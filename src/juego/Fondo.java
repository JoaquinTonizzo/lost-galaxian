package juego;

//import javax.sound.sampled.*;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Fondo {
	private double x;
	private double y;
	private Image imgFondo;
	private double velocidad;

	public Fondo(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.imgFondo = Herramientas.cargarImagen("multimedia/galaxian.jpg");
	}

	// Para colocar la imagen de fondo de las estrellas de forma continua:
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imgFondo, x, y, 0, 2); // se llama al método dibujarImagen en el objeto entorno: se está
														// dibujando una imagen en el entorno gráfico. La imagen que se
														// dibuja es imgFondo y se coloca en la posición (x, y).
		this.y += velocidad; // Después de dibujar, se actualiza el valor de y incrementándolo por
								// velocidad.Entonces la imagen se mueve hacia abajo en cada llamada al método
								// dibujarse
		int comienzoY = 500; // comienzoY con un valor de 500. Esta variable se utiliza para controlar el
								// dibujo repetitivo de la imagen de fondo.
		while (y - comienzoY >= -400) { // se va a dibujar una nueva imagen y va a ir bajando como la anterior, mientras
										// la diferencia entre y y comienzoY sea mayor o igual a -400, se ejecutará el
										// cuerpo del bucle.(nunca llega a 400)
			entorno.dibujarImagen(imgFondo, this.x, this.y - comienzoY, 0, 2);
			comienzoY += 500;
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
