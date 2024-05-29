package juego;

import java.awt.Color;
import java.util.Random;

import entorno.*;
import entorno.InterfaceJuego;
import entorno.Herramientas;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	private Astro astro;
	private Enemigo[] enemigos;
	private Asteroide[] asteroides;
	private Laser[] lasers;
	private Fondo fondo;
	private VidaExtra vidaExtra;
	private boolean juegoIniciado;

	Juego() {

		// INICIAMOS EL ENTORNO
		this.entorno = new Entorno(this, "Lost Galaxian - G1", 800, 600);
		this.fondo = new Fondo(entorno.ancho() / 2, entorno.alto() / 2, 0.5);

		// INICIAMOS los enemigos, sus lasers, asteroides y la nave astro y LO QUE HACE
		// FALTA
		enemigos = new Enemigo[4];
		asteroides = new Asteroide[4];
		lasers = new Laser[4];
		this.astro = new Astro(entorno.ancho() / 2, entorno.alto() - 35, 6, 25);
		this.crearEnemigos();
		this.crearAsteroides();

		// INICIA EL JUEGO
		this.entorno.iniciar();
		Herramientas.play("multimedia/sonidoFondo.wav");
	}

	public void tick() {
		// FONDO
		fondo.dibujarse(entorno);
		if (!juegoIniciado) { // se usa para la pantalla de inicio
			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				juegoIniciado = true;
			} else {
				entorno.cambiarFont("Arial", 50, Color.white);
				entorno.escribirTexto("BIENVENIDO", 240, 250);
				entorno.escribirTexto("LOST GALAXIAN - G1", 150, 350);
				entorno.cambiarFont("Arial", 20, Color.white);
				entorno.escribirTexto("Presiona enter para iniciar el juego", 230, 520);
				return; // sale y vuelve a entrar para cortar el tick
			}
		}

		if (!gano() && astro.getVidas() > 0) { // si no gano y la astro tiene mas de tres vidas
			tick++;

			this.comportamientoVidaExtra(); // se inician todos los metodos en juego
			this.comportamientoAstro();
			this.comportamientoAsteroides();
			this.comportamientoEnemigos();

			inicioElJuego = true;

			// CARTEL ELIMINADOS Y VIDAS
			entorno.dibujarRectangulo(400, 10, 800, 30, 0, Color.black);
			entorno.cambiarFont("Arial", 18, Color.white);
			entorno.escribirTexto("Eliminados: " + eliminados, 10, 20);
			entorno.escribirTexto("Cantidad de vidas:" + astro.getVidas(), 630, 20);
		}

		else {
			if (astro.getVidas() == 0) {
				entorno.cambiarFont("Arial", 18, Color.white);
				entorno.escribirTexto("Eliminados: " + eliminados, 10, 20);
				entorno.escribirTexto("Cantidad de vidas:" + astro.getVidas(), 630, 20);
				entorno.cambiarFont("Arial", 50, Color.white);
				entorno.escribirTexto("GAME OVER", 240, 300);
			} else {
				entorno.cambiarFont("Arial", 18, Color.white);
				entorno.escribirTexto("Eliminados: " + eliminados, 10, 20);
				entorno.escribirTexto("Cantidad de vidas:" + astro.getVidas(), 630, 20);
				entorno.cambiarFont("Arial", 50, Color.white);
				entorno.escribirTexto("YOU WIN", 280, 300);
			}
		}
	}

	private boolean gano() {
		for (int i = 0; i < enemigos.length; i++) {
			if (enemigos[i] != null) {
				return false;
			}
		}
		return true;
	}

	private void comportamientoEnemigos() {
		// RECORREMOS A LOS ENEMIGOS
		for (Enemigo enemigo : enemigos) {

			this.destruirNaveEnemiga();
			this.crearEnemigos();

			if (enemigo != null) { // colisiones, etc.
				// LOS DIBUJAMOS
				enemigo.dibujar(entorno);

				// SI EL ENEMIGO COLISIONA CON EL PROYECTIL DE LA ASTRO MUERE
				if (astro.getProyectil() != null && astro.getProyectil().colisionaCon(enemigo)) {
					astro.setProyectil(null);
				}

				// VERIFICAMOS COLISION ENTRE ENEMIGOS
				for (Enemigo otro : enemigos) {
					if (otro != null) {
						if (otro.colisionaCon(enemigo) && enemigo != otro) {
							if (enemigo.getX() < otro.getX()) {
								otro.setX(otro.getX() + 1); // para evitar que las colisiones hagan movim esxtraños
								enemigo.setX(enemigo.getX() - 1);
							} else {
								otro.setX(otro.getX() - 1);
								enemigo.setX(enemigo.getX() + 1);
							}
							otro.cambiarTrayectoria();
							enemigo.cambiarTrayectoria();
						}
					}
				}

				// VERIFICAMOS COLISION CON EL ENTORNO
				if (enemigo.chocasteCon(entorno)) {
					if (enemigo.getX() > 100)
						enemigo.setX(enemigo.getX() - 1);
					else {
						enemigo.setX(enemigo.getX() + 1);
					}
					enemigo.cambiarTrayectoria();
				}
			}
		}

		// MOVIMIENTO DE ENEMIGOS
		DesplazamientosEnemigos();

		// RECOREMOS EL ARREGLO DE LASERS
		for (int i = 0; i < lasers.length; i++) {
			if ((lasers[i] == null || lasers[i].seFue(entorno)) && enemigos[i] != null) {
				lasers[i] = null;
				lasers[i] = enemigos[i].crearLaser();
			} else {
				if (lasers[i] != null) {
					lasers[i].dibujarDisparoEnemigo(entorno);
					lasers[i].mover();
					lasers[i].setLanzado(true);
				}
			}

			if (lasers[i] != null && lasers[i].colisionaCon(astro)) {
				lasers[i] = null;
				Herramientas.play("multimedia/Explocion.wav");
				astro.perderVida();
			}
		}

	}

	private void comportamientoAsteroides() {
		// RECORREMOS LOS ASTEROIDES
		for (Asteroide asteroide : asteroides) {
			this.destruirAsteroides();
			this.crearAsteroides();

			if (asteroide != null) {

				// LOS DIBUJAMOS
				asteroide.dibujar(entorno);

				// VERIFICAMOS COLISION ENTRE ASTEROIDES PARA QUE NO SE SUPERPONGAN
				for (Asteroide otro : asteroides) {
					if (otro.colisionaCon(asteroide) && asteroide != otro) {
						if (otro.getX() < asteroide.getX()) {
							asteroide.setX(asteroide.getX() + 1);
							otro.setX(otro.getX() - 1);
						} else {
							otro.setX(otro.getX() + 1);
							asteroide.setX(asteroide.getX() - 1);
						}
						asteroide.cambiarTrayectoria();
						otro.cambiarTrayectoria();
					}
				}

				// VERIFICAMOS COLISION ENTRE ASTEROIDES Y ENEMIGOS PARA QUE NO SE SUPERPONGAN
				for (Enemigo enemigo : enemigos) {
					if (enemigo != null) {
						if (enemigo.colisionaCon(asteroide)) {
							if (enemigo.getX() < asteroide.getX()) {
								asteroide.setX(asteroide.getX() + 1);
								enemigo.setX(enemigo.getX() - 1);
							} else {
								asteroide.setX(asteroide.getX() - 1);
								enemigo.setX(enemigo.getX() + 1);
							}
							enemigo.cambiarTrayectoria();
							asteroide.cambiarTrayectoria();
						}
					}
				}

				// VERIFICAMOS COLISION CON EL PROYECTIL DE LA ASTRO
				if (astro.getProyectil() != null && asteroide.colisionaCon(astro.getProyectil())) {
					astro.setProyectil(null);
				}

				// VERIFICAMOS COLISION CON EL ENTORNO
				if (asteroide.chocasteCon(entorno)) {
					if (asteroide.getX() > 100) // para saber que està a la derecha
						asteroide.setX(asteroide.getX() - 1);
					else {
						asteroide.setX(asteroide.getX() + 1);
					}
					asteroide.cambiarTrayectoria();
				}
			}
		}

		// MOVIMIENTO
		DesplazamientosAsteroides();

	}

	int tick = 0;
	int acum = 1; // para vida extra
	int acum1 = 1; // para enemigos
	int eliminados = 0;
	boolean inicioElJuego = false;
	Random random = new Random();
	int aleatorio = 300; // random.nextInt(40) + 380; //cada cuanto se crean los enemigos

	private void DesplazamientosAsteroides() {
		for (int i = 0; i < asteroides.length; i++) {
			if (asteroides[i] != null) {
				if (i % 2 == 0) {
					asteroides[i].moverDerecha();
				} else {
					asteroides[i].moverIzquierda();
				}
			}
		}
	}

	private void DesplazamientosEnemigos() {
		for (int i = 0; i < enemigos.length; i++) {
			if (enemigos[i] != null) {
				if (i % 2 == 0) {
					enemigos[i].moverDerecha();
				} else {
					enemigos[i].moverIzquierda();
				}
			}
		}
	}

	private void comportamientoAstro() {
		// LA DIBUJAMOS
		astro.dibujar(entorno);

		// MOVIMEINTOS DE LA ASTRO
		if (this.astro.getX() - this.astro.getRadio() > 0
				&& (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a'))) {
			astro.moverIzquierda();
		}

		if (this.astro.getX() + this.astro.getRadio() < entorno.ancho()
				&& (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d'))) {
			astro.moverDerecha();
		}

		// DISPAROS DE LA ASTRO
		if (astro.getProyectil() == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			astro.crearProyectil();
			astro.getProyectil().setLanzado(true);
			Herramientas.play("multimedia/Proyectil.wav");
		}

		if (astro.getProyectil() != null && astro.getProyectil().getLanzado() == true) {
			astro.lanzarProyectil(entorno);
		}

		if (astro.getProyectil() != null && astro.getProyectil().seFue(entorno)) {
			astro.setProyectil(null);
		}
	}

	private void crearEnemigos() {
		if (tick == aleatorio * acum1 || !inicioElJuego) {
			for (int i = 0; i < this.enemigos.length; i++) {
				if (enemigos[i] == null) {
					Random random = new Random();
					int x = random.nextInt(entorno.ancho() - 100) + 50;
					int y = random.nextInt(200) - 200;
					enemigos[i] = new Enemigo(x, y, 1.3, 25, Math.PI / 4);
					enemigos[i].crearLaser();
				}
			}
			if (tick == aleatorio * acum1)
				acum1++;
		}
	}

	private void comportamientoVidaExtra() {

		if (vidaExtra == null && tick == 1500 * acum && astro.getVidas() < 3) {
			Random random = new Random();
			int x = random.nextInt(entorno.ancho() - 100) + 50;
			int y = random.nextInt(200) - 200;
			vidaExtra = new VidaExtra(x, y, 1, 15);
			acum++;
		}

		if (vidaExtra != null) {
			vidaExtra.dibujar(entorno);
			vidaExtra.mover();

			if (vidaExtra.seFue(entorno) || vidaExtra.colisionaCon(astro)) {
				if (vidaExtra.colisionaCon(astro)) {
					astro.ganarVida();
					Herramientas.play("multimedia/vidaExtra.wav");
				}
				vidaExtra = null;
			}
		}
	}

	private void crearAsteroides() {
		int pos = 0;
		if (inicioElJuego) {
			pos = 200;
		}
		for (int i = 0; i < this.asteroides.length; i++) {
			if (asteroides[i] == null) {
				Random random = new Random();
				int x = random.nextInt(entorno.ancho() - 100) + 50;
				int y = random.nextInt(200) - pos;
				asteroides[i] = new Asteroide(x, y, 1, 15, Math.PI / 4);
			}
		}
	}

	private void destruirAsteroides() {
		for (int i = 0; i < asteroides.length; i++) {
			if (asteroides[i] != null) {
				if (asteroides[i].seFue(entorno)) {
					asteroides[i] = null;
				} else {
					if (asteroides[i].colisionaCon(astro)) {
						asteroides[i] = null;
						Herramientas.play("multimedia/Explocion.wav");
						astro.perderVida();
					}
				}
			}
		}
	}

	private void destruirNaveEnemiga() {
		for (int i = 0; i < enemigos.length; i++) {
			if (enemigos[i] != null) {
				if (astro.getProyectil() != null && astro.getProyectil().colisionaCon(enemigos[i])) {
					enemigos[i] = null;
					astro.setProyectil(null);
					eliminados++;
				} else {
					if (enemigos[i].seFue(entorno)) {
						enemigos[i] = null;
						Random random = new Random();
						int x = random.nextInt(entorno.ancho() - 100) + 50;
						int y = random.nextInt(200) - 200;
						enemigos[i] = new Enemigo(x, y, 1.3, 25, Math.PI / 4);
						enemigos[i].crearLaser();
					} else {
						if (enemigos[i].colisionaCon(astro)) {
							enemigos[i] = null;
							Herramientas.play("multimedia/Explocion.wav");
							astro.perderVida();
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
