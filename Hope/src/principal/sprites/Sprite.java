package principal.sprites;

import java.awt.image.BufferedImage;

public class Sprite {

	private final BufferedImage imagen;

	private final int alto;
	private final int ancho;

	public Sprite(final BufferedImage imagen) {
		this.imagen = imagen;

		ancho = imagen.getWidth();
		alto = imagen.getHeight();
	}

	public BufferedImage obtenerImagen() {
		return imagen;
	}

	public int obtenerAncho() {
		return ancho;
	}

	public int obtenerAlto() {
		return alto;
	}

}
