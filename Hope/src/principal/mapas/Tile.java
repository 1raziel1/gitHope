package principal.mapas;

import java.awt.Rectangle;

import principal.sprites.Sprite;

public class Tile {

	private final Sprite sprite;
	private final int id;
	private boolean solido;

	public Tile(final int id, final Sprite sprite) {
		this.id = id;
		this.sprite = sprite;
		solido = false;
	}

	public Tile(final int id, final Sprite sprite, final boolean solido) {
		this.id = id;
		this.sprite = sprite;
		this.solido = solido;
	}

	public Sprite obtenerSprite() {
		return sprite;
	}

	public int obtenerId() {
		return id;
	}

	public void establecerSolido(final boolean solido) {
		this.solido = solido;
	}

	public Rectangle obtenerLimites(final int x, final int y) {
		return new Rectangle(x, y, sprite.obtenerAncho(), sprite.obtenerAlto());
	}

}
