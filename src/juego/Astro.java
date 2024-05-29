package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Astro {
	private double x;
	private double y;
	private double factorDesplazamiento;
	private int radio;
	private Proyectil proyectil;
	private int vidas;
	private Image imagenAstro;
	Color transparente = new Color(0, 0, 255, 0);

	public Astro(double x, double y, double factorDesplazamiento, int radio) {
		this.x = x;
		this.y = y;
		this.factorDesplazamiento = factorDesplazamiento;
		this.radio = radio;
		this.vidas = 3;
		this.imagenAstro = Herramientas.cargarImagen("multimedia/astro.png");
	}

	public int getVidas() {
		return this.vidas;
	}

	public void perderVida() {
		if (this.vidas > 0) {
			this.vidas--;
		}
	}

	public void ganarVida() {
		this.vidas++;
	}

	public void crearProyectil() {
		this.proyectil = new Proyectil(this.x, this.y, 6, 3);
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, 1.5 * this.radio, transparente);
		entorno.dibujarImagen(this.imagenAstro, this.x, this.y, 0, 0.16);
	}

	public void moverIzquierda() {
		this.x -= factorDesplazamiento;
	}

	public void moverDerecha() {
		this.x += factorDesplazamiento;
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

	public void lanzarProyectil(Entorno entorno) {
		this.proyectil.dibujarDisparo(entorno);
		this.proyectil.mover();
		this.proyectil.setLanzado(true);
	}

	public Proyectil getProyectil() {
		return this.proyectil;
	}

	public void setProyectil(Proyectil proyectil) {
		this.proyectil = proyectil;
	}

}
