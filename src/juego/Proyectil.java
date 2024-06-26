package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {
	private double x;
	private double y;
	private double velocidad;
	private int radio;
	private boolean lanzado;
	private Image imagenProyectil;
	Color transparente = new Color(0, 0, 255, 0);

	public Proyectil(double x, double y, double velocidad, int radio) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.radio = radio;
		this.lanzado = false;
		this.imagenProyectil = Herramientas.cargarImagen("multimedia/proyectil.png");
	}

	public void dibujarDisparo(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, 1.5 * this.radio, transparente); // SE CREA EN ASTRO el radio es 3
		entorno.dibujarImagen(this.imagenProyectil, this.x, this.y, 0, 0.3);
	}

	public void mover() {
		this.y = this.y - velocidad;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public int getRadio() {
		return this.radio;
	}

	public boolean seFue(Entorno entorno) {
		if (this.y < 0 && this.lanzado) { // se lanzó el proyectil y su ubicacion es menos de cero en y
			return true;
		}
		return false;
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

	public boolean getLanzado() {
		return this.lanzado;
	}

	public void setLanzado(boolean lanzado) {
		this.lanzado = lanzado;
	}
}
