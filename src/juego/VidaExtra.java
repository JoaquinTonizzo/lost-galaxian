package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class VidaExtra {
	private double x;
	private double y;
	private double factorDesplazamiento;
	private int radio;
	private Image imagenVidaExtra;
	Color transparente = new Color(0, 0, 255, 0);

	public VidaExtra(double x, double y, double factorDesplazamiento, int radio) {
		this.x = x;
		this.y = y;
		this.factorDesplazamiento = factorDesplazamiento;
		this.radio = radio;
		this.imagenVidaExtra = Herramientas.cargarImagen("multimedia/vidaExtra.png");
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, 3 * radio, transparente);
		entorno.dibujarImagen(this.imagenVidaExtra, this.x, this.y, 0, 0.1);
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

	public double getFactorDesplazamiento() {
		return this.factorDesplazamiento;
	}

	public boolean seFue(Entorno entorno) {
		if (this.y > entorno.alto()) {
			return true;
		}
		return false;
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

	public void mover() {
		y += this.factorDesplazamiento;
	}

}
