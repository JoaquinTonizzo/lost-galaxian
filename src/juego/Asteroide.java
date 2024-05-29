package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Asteroide {
	private double x;
	private double y;
	private double factorDesplazamiento;
	private int radio;
	private double angulo;
	private Image imagenAsteroide;
	Color transparente = new Color(0, 0, 255, 0);

	public Asteroide(int x, int y, double factorDesplazamiento, int radio, double angulo) {
		this.x = x;
		this.y = y;
		this.factorDesplazamiento = factorDesplazamiento;
		this.radio = radio;
		this.angulo = angulo;
		this.imagenAsteroide = Herramientas.cargarImagen("multimedia/asteroide.png");
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, 2 * this.radio, transparente);
		entorno.dibujarImagen(this.imagenAsteroide, this.x, this.y, 0, 0.121);
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

	public boolean seFue(Entorno entorno) {
		if (this.y > entorno.alto()) {
			return true;
		}
		return false;
	}

	public boolean colisionaCon(Proyectil otroCirculo) {
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

	public boolean colisionaCon(Astro otroCirculo) {
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
