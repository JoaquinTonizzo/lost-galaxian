package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	private double x;
	private double y;
	private double factorDesplazamiento;
	private int radio;
	private double angulo;
	private Image imagenEnemigo;
	Color transparente = new Color(0, 0, 255, 0);

	public Enemigo(double x, double y, double factorDesplazamiento, int radio, double angulo) {
		this.x = x;
		this.y = y;
		this.factorDesplazamiento = factorDesplazamiento;
		this.radio = radio;
		this.angulo = angulo;
		this.imagenEnemigo = Herramientas.cargarImagen("multimedia/enemigo.png");

	}

	public Laser crearLaser() {
		return new Laser(this.x, this.y, 3, 3);
	}

	public boolean seFue(Entorno entorno) {
		if (this.y > entorno.alto()) {
			return true;
		}
		return false;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, 1.5 * this.radio, transparente);
		entorno.dibujarImagen(this.imagenEnemigo, this.x, this.y, 0, 0.19);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		if (x > 0 && x < 800)
			this.x = x;
	}

	public int getRadio() {
		return this.radio;
	}

	public double getFactorDesplazamiento() {
		return this.factorDesplazamiento;
	}

	public boolean colisionaCon(Astro otroCirculo) {
		double distancia = Math
				.sqrt(Math.pow(otroCirculo.getX() - this.x, 2) + Math.pow(otroCirculo.getY() - this.y, 2));
		if (distancia <= (double) (this.radio + otroCirculo.getRadio())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean colisionaCon(Enemigo otroCirculo) {
		double distancia = Math
				.sqrt(Math.pow(otroCirculo.getX() - this.x, 2) + Math.pow(otroCirculo.getY() - this.y, 2));
		if (distancia <= (double) (this.radio + otroCirculo.getRadio())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean colisionaCon(Asteroide otroCirculo) {
		double distancia = Math
				.sqrt(Math.pow(otroCirculo.getX() - this.x, 2) + Math.pow(otroCirculo.getY() - this.y, 2));
		if (distancia <= (double) (this.radio + otroCirculo.getRadio())) {
			return true;
		} else {
			return false;
		}
	}

	public void moverDerecha() {
		this.y += factorDesplazamiento;
		this.x += factorDesplazamiento * Math.cos(angulo);
	}

	public void moverIzquierda() {
		this.y += factorDesplazamiento;
		this.x -= factorDesplazamiento * Math.cos(angulo);
	}

	public boolean chocasteCon(Entorno entorno) {
		return this.x <= radio || this.x >= entorno.ancho() - radio;
	}

	public void cambiarTrayectoria() {
		this.angulo -= Math.PI / 2;
	}

}
