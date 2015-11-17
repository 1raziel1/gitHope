package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

public class Jugador {

	private double posicionX;
	private double posicionY;

	private double velocidad = 1;

	private int salud = 10;

	private int salto = 2;
	private int decrementoSalto = 0;

	private boolean enemigoGolpeado = false;
	private int indiceEnemigoGolpeado = 0;
	private int golpeandoEnemigo = 0;

	private int recorridoSalto = 0;

	private int estadoDano = 0;
	private int estadoDanoDireccion = 0;

	private boolean invulnerable = false;
	private boolean saltando = false;
	private boolean callendo = false;
	private boolean agachado = false;
	private boolean bajoAgua = false;
	private boolean moviendoBloque = false;

	private int capacidadSalto = (int) ((48)/0.6);

	private int direccion;

	private boolean enMovimiento;
	private boolean enPlataforma = false;
	private boolean pulsando = false;

	private HojaSprites hs;

	private BufferedImage imagenActual;

	private Mapa mapa;

	private final int ANCHO_JUGADOR = 11;
	private final int ALTO_JUGADOR = 28;

	private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - 5,
			Constantes.CENTRO_VENTANA_Y - 12, ANCHO_JUGADOR, 2);
	private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - 5,
			Constantes.CENTRO_VENTANA_Y + 14, ANCHO_JUGADOR, 2);

	private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - 5,
			Constantes.CENTRO_VENTANA_Y - 12, 1, ALTO_JUGADOR);

	private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + 5,
			Constantes.CENTRO_VENTANA_Y - 12, 1, ALTO_JUGADOR);

	private final Rectangle[] limitesJugador = { LIMITE_ARRIBA, LIMITE_ABAJO, LIMITE_IZQUIERDA, LIMITE_DERECHA };
	
	
	
	
	//Agachado
	private final Rectangle LIMITE_ARRIBA_AGACHADO = new Rectangle(Constantes.CENTRO_VENTANA_X-15,
			Constantes.CENTRO_VENTANA_Y , ANCHO_JUGADOR+20, 2);
	private final Rectangle LIMITE_ABAJO_AGACHADO = new Rectangle(Constantes.CENTRO_VENTANA_X-15,
			Constantes.CENTRO_VENTANA_Y + 14, ANCHO_JUGADOR+20, 2);

	private final Rectangle LIMITE_IZQUIERDA_AGACHADO = new Rectangle(Constantes.CENTRO_VENTANA_X - 15,
			Constantes.CENTRO_VENTANA_Y, 1, ALTO_JUGADOR-15);

	private final Rectangle LIMITE_DERECHA_AGACHADO = new Rectangle(Constantes.CENTRO_VENTANA_X + 15,
			Constantes.CENTRO_VENTANA_Y , 1, ALTO_JUGADOR-15);

	private final Rectangle[] limitesJugadorAgachado = { LIMITE_ARRIBA_AGACHADO, LIMITE_ABAJO_AGACHADO, LIMITE_IZQUIERDA_AGACHADO, LIMITE_DERECHA_AGACHADO };
	

	private int animacion;
	private int estado;
	int velocidadX = 0;
	int velocidadY = 0;
	private int velocidadPlataforma = 0;
	private Enemigo enemigo;
	private ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();
	private ArrayList<Plataforma> plataformas = new ArrayList<Plataforma>();
	private ArrayList<Bloque> bloques = new ArrayList<Bloque>();

	private ArrayList<Rectangle> colisionHabilidades = new ArrayList<Rectangle>();

	// public Jugador(Mapa mapa, ArrayList<Enemigo> enemigos) {
	// posicionX = mapa.obtenerPosicionInicial().x;
	// posicionY = mapa.obtenerPosicionInicial().y;
	//
	// direccion = 2;
	//
	// enMovimiento = false;
	//
	// hs = new HojaSprites("/imagenes/hojasPersonajes/1.png",
	// Constantes.LADO_SPRITE, false);
	// imagenActual = hs.obtenerSprite(0).obtenerImagen();
	//
	// animacion = 0;
	// estado = 0;
	//
	// this.mapa = mapa;
	// this.enemigos = enemigos;
	// }

	// public Jugador(Mapa mapa, ArrayList<Enemigo> enemigos,
	// ArrayList<Plataforma> plataformas) {
	// posicionX = mapa.obtenerPosicionInicial().x;
	// posicionY = mapa.obtenerPosicionInicial().y;
	//
	// direccion = 2;
	//
	// enMovimiento = false;
	//
	// hs = new HojaSprites("/imagenes/hojasPersonajes/1.png",
	// Constantes.LADO_SPRITE, false);
	// imagenActual = hs.obtenerSprite(0).obtenerImagen();
	//
	// animacion = 0;
	// estado = 0;
	//
	// this.mapa = mapa;
	// this.enemigos = enemigos;
	// this.plataformas = plataformas;
	// }

	public Jugador(Mapa mapa, ArrayList<Enemigo> enemigos, ArrayList<Plataforma> plataformas, ArrayList<Bloque> bloques) {
		posicionX = mapa.obtenerPosicionInicial().x;
		posicionY = mapa.obtenerPosicionInicial().y;

		direccion = 2;

		enMovimiento = false;

		hs = new HojaSprites("/imagenes/hojasPersonajes/1.png", Constantes.LADO_SPRITE, false);
		imagenActual = hs.obtenerSprite(0).obtenerImagen();

		animacion = 0;
		estado = 0;

		this.mapa = mapa;
		this.enemigos = enemigos;
		this.plataformas = plataformas;
		this.bloques = bloques;
	}

	// public Jugador(Mapa mapa) {
	// posicionX = mapa.obtenerPosicionInicial().x;
	// posicionY = mapa.obtenerPosicionInicial().y;
	//
	// direccion = 2;
	//
	// enMovimiento = false;
	//
	// hs = new HojaSprites("/imagenes/hojasPersonajes/1.png",
	// Constantes.LADO_SPRITE, false);
	// imagenActual = hs.obtenerSprite(0).obtenerImagen();
	//
	// animacion = 0;
	// estado = 0;
	//
	// this.mapa = mapa;
	// }

	public void actualizar() {
		cambiarAnimacionEstado();
		enMovimiento = false;
		determinarDireccion();
		animar();

		actualizarPlataforma();

		atacar();

		if (!enemigos.isEmpty()) {
			recibirDano();
		}

	}

	private void actualizarPlataforma() {
		for (int i = 0; i < plataformas.size(); i++) {
			if (enPlataforma) {
				plataformas.get(i).colision(true);
			} else {
				plataformas.get(i).colision(false);
			}
		}

		if (GestorControles.teclado.izquierda.estaPulsada() || GestorControles.teclado.derecha.estaPulsada()) {
			pulsando = true;

		} else {
			pulsando = false;
		}
	}

	private void atacar() {
		int frecuenciaAnimacion = 6;
		int limite = 6;

		if (GestorPrincipal.obtenerAps() % frecuenciaAnimacion == 0) {
			colisionHabilidades.clear();

		}

		if (GestorControles.teclado.j.estaPulsada() && direccion == 2) {
			colisionHabilidades.clear();
			Rectangle golpeDerecha = new Rectangle(Constantes.CENTRO_VENTANA_X + 10, Constantes.CENTRO_VENTANA_Y, 11,
					11);

			colisionHabilidades.add(golpeDerecha);
		}
		if (GestorControles.teclado.j.estaPulsada() && direccion == 3) {
			colisionHabilidades.clear();
			Rectangle golpeIzquierda = new Rectangle(Constantes.CENTRO_VENTANA_X - 20, Constantes.CENTRO_VENTANA_Y, 11,
					11);

			colisionHabilidades.add(golpeIzquierda);
		}
		if (!colisionHabilidades.isEmpty()) {
			if (golpearHabilidad()) {
				enemigos.get(indiceEnemigoGolpeado).recivirDano(200);
			}
		}

	}

	private boolean golpearHabilidad() {

		for (int arrayene = 0; arrayene < enemigos.size(); arrayene++) {
			for (int ene = 0; ene < enemigos.get(arrayene).obtenerColisiones().size(); ene++) {

				final Rectangle e = enemigos.get(arrayene).obtenerColisiones().get(ene);

				int origenX = e.x + velocidadX * (int) velocidad - 3 * (int) velocidad;

				int origenY = e.y;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, e.width, e.height);

				if (colisionHabilidades.get(0).intersects(areaFutura)) {
					indiceEnemigoGolpeado = arrayene;

					return true;
				}
			}

		}

		return false;
	}

	private void cambiarAnimacionEstado() {
		int frecuenciaAnimacion = 6;
		int limite = 6;

		if (GestorPrincipal.obtenerAps() % frecuenciaAnimacion == 0) {
			animacion++;
			if (animacion >= limite) {
				animacion = 0;
			}
			if (invulnerable) {
				if (estadoDano <= 4) {
					estado = 1;
				} else {
					// revolcandose en el suelo
					switch (animacion) {
					case 0:
						estado = 2;
						break;
					case 1:
						estado = 2;
						break;
					case 2:
						estado = 3;
						break;
					case 3:
						estado = 3;
						break;
					case 4:
						estado = 2;
						break;
					case 5:
						estado = 3;
						break;
					}
				}
				// saltando
			} else if (saltando) {
				if (callendo) {
					switch (animacion) {
					case 0:
						estado = 3;
						break;
					case 1:
						estado = 4;
						break;
					case 2:
						estado = 3;
						break;
					case 3:
						estado = 4;
						break;
					case 4:
						estado = 3;
						break;
					case 5:
						estado = 4;
						break;
					}
					if (GestorControles.teclado.abajo.estaPulsada()) {
						estado = 5;// atacar
					}
				} else {
					estado = 8;
				}

			} else if (enColisionAgua(2) || moviendoBloque) {
				if (!enMovimiento) {
					switch (animacion) {
					case 0:
						estado = 0;
						break;
					case 1:
						estado = 2;
						break;
					case 2:
						estado = 1;
						break;
					case 3:
						estado = 2;
						break;
					case 4:
						estado = 0;
						break;
					case 5:
						estado = 2;
						break;
					}
				} else {// en el agua sin moverse
					switch (animacion) {
					case 0:
						estado = 3;
						break;
					case 1:
						estado = 3;
						break;
					case 2:
						estado = 4;
						break;
					case 3:
						estado = 4;
						break;
					case 4:
						estado = 5;
						break;
					case 5:
						estado = 5;
						break;

					}
				}
			} else {

				if (agachado) {// agachado
					switch (animacion) {
					case 0:
						estado = 6;
						break;
					case 1:
						estado = 6;
						break;
					case 2:
						estado = 6;
						break;
					case 3:
						estado = 7;
						break;
					case 4:
						estado = 7;
						break;
					case 5:
						estado = 7;
						break;
					}
				} else if (pulsando) {// normal
					switch (animacion) {
					case 0:
						estado = 1;
						break;
					case 1:
						estado = 1;
						break;
					case 2:
						estado = 0;
						break;
					case 3:
						estado = 2;
						break;
					case 4:
						estado = 2;
						break;
					case 5:
						estado = 0;
						break;
					}
				}

			}

		}

	}

	private void determinarDireccion() {

		if (GestorControles.teclado.abajo.estaPulsada() && !bajoAgua) {
			agachado = true;

		} else if (GestorControles.teclado.arriba.estaPulsada() || GestorControles.teclado.espacio.estaPulsada()
				|| invulnerable) {
			agachado = false;
		}
		// cambiar modo sumergido modo normal
		if (enColisionAgua(2) && GestorControles.teclado.abajo.estaPulsada() && !GestorControles.teclado.espacio.estaPulsada() ) {
			bajoAgua = true;
			agachado = false;

		} else if (enColisionAgua(2)) {
			bajoAgua = false;
		}

		final int velocidadX = evaluarVelocidadX();
		final int velocidadY = evaluarVelocidadY();

		if (velocidadX == 0 && velocidadY == 0) {
			return;
		}
		if ((velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0)) {
			mover(velocidadX, velocidadY);

		} else {
			mover(velocidadX, velocidadY);
		}
	}

	private int evaluarVelocidadX() {
		velocidadX = 0;
		if (!invulnerable) {
			if (GestorControles.teclado.izquierda.estaPulsada() && !GestorControles.teclado.derecha.estaPulsada()) {
				velocidadX = -1;
			} else if (!GestorControles.teclado.izquierda.estaPulsada()
					&& GestorControles.teclado.derecha.estaPulsada()) {
				velocidadX = 1;
			}
		}
		if (enPlataforma) {

			velocidadX = velocidadX + plataformas.get(0).obtenerVelocidadX();

		}

		return velocidadX;
	}

	private int evaluarVelocidadY() {

		if (bajoAgua) {

			if (enColisionAbajo(2) || enColisionAgua(2)) {
				velocidadY = 0;
				decrementoSalto = 0;
				saltando = false;
				salto = (int) posicionY;
				recorridoSalto = (int) posicionY;

			} else {
				// velocidad de caida
				saltando = true;
				velocidadY = 1;
				agachado = false;
			}
			if (!invulnerable) {

				if (GestorControles.teclado.espacio.estaPulsada()) {
					velocidadY = -1;
				} else {
					decrementoSalto = 0;
					salto = -1;
					velocidadY = 1;
				}
			}

		} else {
			if (enColisionPlataformaAbajo(2)) {
				enPlataforma = true;
			} else {
				enPlataforma = false;
			}

			if (enColisionAbajo(2) || enColisionAgua(2) || enPlataforma || enColisionAbajoBloque(2)) {
				velocidadY = 0;
				decrementoSalto = 0;
				saltando = false;
				salto = (int) posicionY;
				recorridoSalto = (int) posicionY;

			} else {
				// velocidad de caida
				saltando = true;
				velocidadY = 2;
				agachado = false;
			}
			if (!invulnerable) {

				if (GestorControles.teclado.espacio.estaPulsada() && recorridoSalto >= posicionY) {
					recorridoSalto = (int) posicionY;

					if (salto - capacidadSalto <= (int) posicionY && salto >= 0) {
						switch (decrementoSalto) {
						case 0:
							velocidadY = -4;
							break;
						case 1:
							velocidadY = -3;
							break;
						case 2:
							velocidadY = -2;
							break;
						}

						if (!(salto - capacidadSalto / 2 <= (int) posicionY)) {
							decrementoSalto = 1;
						}
						if (!(salto - (capacidadSalto / 2) - (capacidadSalto / 4) <= (int) posicionY)) {
							decrementoSalto = 2;
						}

						// velocidad de salto
						if (enColisionArriba(velocidadY)) {
							salto = -1;
						}
					} else {
						decrementoSalto = 0;
						salto = -1;
						velocidadY = 2;
					}
				}
			}
			if (velocidadY > 0) {
				callendo = true;
			} else {
				callendo = false;
			}

		}

		return velocidadY;
	}

	private void mover(final int velocidadX, int velocidadY) {
		enMovimiento = true;

		cambiarDireccionEstado(velocidadX, velocidadY);
		if (agachado && !saltando) {
			velocidad = 0.5;
		} else {
			velocidad = 1;
		}

		if (!fueraMapa(velocidadX, velocidadY)) {
			if (velocidadX <= -1 && !enColisionIzquierda(velocidadX) && !enColisionIzquierdaBloque(velocidadX)) {
				posicionX += velocidadX * velocidad;
			}
			if (velocidadX >= 1 && !enColisionDerecha(velocidadX) && !enColisionDerechaBloque(velocidadX)) {
				posicionX += velocidadX * velocidad;
			}
			if (velocidadY <= -1 && !enColisionArriba(velocidadY)) {
				posicionY += velocidadY * velocidad;
			}
			if (velocidadY >= 1 && !enColisionAbajo(velocidadY) && !enColisionAbajoBloque(velocidadY)) {
				posicionY += velocidadY * velocidad;
			}

		} else {
			enMovimiento = false;
		}

	}

	private boolean enColisionAgua(final int velocidadY) {
		for (int r = 0; r < mapa.areasAgua.size(); r++) {
			final Rectangle area = mapa.areasAgua.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad - 4 * (int) velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (LIMITE_ARRIBA.intersects(areaFutura)) {
				return true;
			}
		}

		return false;
	}

	private boolean enColisionArriba(final int velocidadY) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad + 4 * (int) velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (LIMITE_ARRIBA.intersects(areaFutura)) {
				return true;
			}
		}

		return false;
	}

	private boolean enColisionAbajo(final int velocidadY) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x;
			int origenY = area.y + 2 * (int) 2 - 3 * (int) 2;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (LIMITE_ABAJO.intersects(areaFutura)) {
				return true;
			}

		}

		return false;
	}

	private boolean enColisionIzquierda(final int velocidadX) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			if(!agachado){
				if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
					return true;
				}
			}else{
				if (LIMITE_IZQUIERDA_AGACHADO.intersects(areaFutura)) {
					return true;
				}
			}
		
		}

		return false;
	}

	private boolean enColisionDerecha(final int velocidadX) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x + velocidadX * (int) velocidad - 3 * (int) velocidad;

			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			if(!agachado){
				if (LIMITE_DERECHA.intersects(areaFutura)) {
					return true;
				}
			}else{
				if (LIMITE_DERECHA_AGACHADO.intersects(areaFutura)) {
					return true;
				}
			}
			
		}

		return false;
	}

	private boolean enColisionAbajoBloque(int velocidadY) {

		for (int arrayene = 0; arrayene < bloques.size(); arrayene++) {
			for (int ene = 0; ene < bloques.get(arrayene).obtenerColisiones().size(); ene++) {

				final Rectangle e = bloques.get(arrayene).obtenerColisiones().get(ene);

				int origenX = e.x;

				int origenY = e.y + velocidadY * (int) velocidad - 5 * (int) velocidad;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, e.width, e.height);

				if (LIMITE_ABAJO.intersects(areaFutura)) {
					return true;
				}
			}

		}

		return false;
	}

	private boolean enColisionIzquierdaBloque(int velocidadX) {
		for (int arrayene = 0; arrayene < bloques.size(); arrayene++) {
			for (int ene = 0; ene < bloques.get(arrayene).obtenerColisiones().size(); ene++) {

				final Rectangle e = bloques.get(arrayene).obtenerColisiones().get(ene);

				int origenX = e.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
				int origenY = e.y;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, e.width, e.height);

				if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
					bloques.get(arrayene).colisionJugadorIzquierda(true);
					return true;
				}

			}
		}

		return false;
	}

	private boolean enColisionDerechaBloque(int velocidadX) {
		for (int arrayene = 0; arrayene < bloques.size(); arrayene++) {
			for (int ene = 0; ene < bloques.get(arrayene).obtenerColisiones().size(); ene++) {

				final Rectangle e = bloques.get(arrayene).obtenerColisiones().get(ene);

				int origenX = e.x + velocidadX * (int) velocidad - 3 * (int) velocidad;
				int origenY = e.y;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, e.width, e.height);

				if (LIMITE_DERECHA.intersects(areaFutura)) {
					bloques.get(arrayene).colisionJugadorDerecha(true);
					return true;
				}
			}
		}

		return false;
	}

	private boolean enColisionEnemigoAbajo(int velocidadY) {
		for (int arrayene = 0; arrayene < enemigos.size(); arrayene++) {
			for (int ene = 0; ene < enemigos.get(arrayene).obtenerColisiones().size(); ene++) {

				final Rectangle e = enemigos.get(arrayene).obtenerColisiones().get(ene);

				int origenX = e.x;

				int origenY = e.y + velocidadY * (int) velocidad - 3 * (int) velocidad;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, e.width, e.height);
				if (LIMITE_ABAJO.intersects(areaFutura)) {
					indiceEnemigoGolpeado = arrayene;
					return true;
				}
			}

		}

		return false;
	}

	private boolean enColisionPlataformaAbajo(int velocidadY) {
		for (int arrayene = 0; arrayene < plataformas.size(); arrayene++) {
			for (int ene = 0; ene < plataformas.get(arrayene).obtenerColisiones().size(); ene++) {

				final Rectangle e = plataformas.get(arrayene).obtenerColisiones().get(ene);

				int origenX = e.x;

				int origenY = e.y + velocidadY * (int) velocidad - 5 * (int) velocidad;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, e.width, e.height);
				if (LIMITE_ABAJO.intersects(areaFutura)) {
					return true;
				}
			}

		}

		return false;
	}

	private boolean enColisionEnemigoDerecha(int velocidadX) {
		for (int arrayene = 0; arrayene < enemigos.size(); arrayene++) {
			for (int ene = 0; ene < enemigos.get(arrayene).obtenerColisiones().size(); ene++) {

				final Rectangle e = enemigos.get(arrayene).obtenerColisiones().get(ene);

				int origenX = e.x + velocidadX * (int) velocidad - 3 * (int) velocidad;

				int origenY = e.y;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, e.width, e.height);
				if (LIMITE_DERECHA.intersects(areaFutura)) {
					return true;
				}
			}

		}

		return false;
	}

	private boolean enColisionEnemigoIzquierda(int velocidadX) {
		for (int arrayene = 0; arrayene < enemigos.size(); arrayene++) {
			for (int ene = 0; ene < enemigos.get(arrayene).obtenerColisiones().size(); ene++) {

				final Rectangle e = enemigos.get(arrayene).obtenerColisiones().get(ene);

				int origenX = e.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
				int origenY = e.y;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, e.width, e.height);
				if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
					return true;
				}
			}
		}

		return false;
	}

	private void golpearEnemigo(int i) {
		if (enemigoGolpeado) {

			if (golpeandoEnemigo == 0) {
				enemigos.get(i).recivirDano(1);
			}

		}
	}

	public void recibirDano() {
		int frecuencia = 10;
		int limite = 3;
		if (enemigoGolpeado) {
			if (golpeandoEnemigo <= 3) {
				velocidadY = -4;

				mover(velocidadX, velocidadY);

			}

			if (GestorPrincipal.obtenerAps() % frecuencia == 0) {
				golpeandoEnemigo++;

				if (golpeandoEnemigo >= limite) {
					golpeandoEnemigo = 0;
					enemigoGolpeado = false;
					velocidadY = 0;
				}
			}
		}
		if (!invulnerable && !enemigoGolpeado) {
			if (enColisionEnemigoIzquierda(velocidadX)) {

				invulnerable = true;
				salud -= 2;
				estadoDanoDireccion = 2;

			}
			if (enColisionEnemigoDerecha(velocidadX)) {

				invulnerable = true;
				estadoDanoDireccion = -2;
				salud -= 2;

			}
			if (enColisionEnemigoAbajo(velocidadY)) {
				if (GestorControles.teclado.abajo.estaPulsada()) {
					enemigoGolpeado = true;
					invulnerable = false;
					golpearEnemigo(indiceEnemigoGolpeado);

				} else {
					invulnerable = true;
					estadoDanoDireccion = -2;
					salud -= 2;
				}

			}
		}
		if (invulnerable) {
			agachado = false;

			if (estadoDano <= 3) {
				velocidadX = estadoDanoDireccion;
				velocidadY = -3;
				mover(velocidadX, velocidadY);
			}

			if (GestorPrincipal.obtenerAps() % frecuencia == 0) {
				estadoDano++;

				if (estadoDano >= limite + 8) {
					estadoDano = 0;
					invulnerable = false;
					velocidadX = 0;
					velocidadY = 0;
					// direccion=estadoDanoDireccion;//123
				}
			}

		}

	}

	private boolean fueraMapa(final int velocidadX, final int velocidadY) {
		int posicionFuturaX = (int) posicionX + velocidadX * (int) velocidad;
		int posicionFuturaY = (int) posicionY + velocidadY * (int) velocidad;

		final Rectangle bordesMapa = mapa.obtenerBordes(posicionFuturaX, posicionFuturaY, ALTO_JUGADOR, ANCHO_JUGADOR);

		final boolean fuera;

		if (LIMITE_ARRIBA.intersects(bordesMapa) || LIMITE_ABAJO.intersects(bordesMapa)
				|| LIMITE_IZQUIERDA.intersects(bordesMapa) || LIMITE_DERECHA.intersects(bordesMapa)) {
			fuera = false;
		} else {
			fuera = true;
		}

		return fuera;
	}

	private void cambiarDireccionEstado(final int velocidadX, final int velocidadY) {

		if (velocidadX == -1) {
			direccion = 3;
		} else if (velocidadX == 1) {
			direccion = 2;
		}
		if (enColisionAgua(2) && direccion <= 3) {
			direccion += 4;
		}

		if (velocidadY == -1) {
			direccion = 1;
		} else if (velocidadY == 1) {
			direccion = 0;
		}
		if (invulnerable) {
			if (estadoDanoDireccion == -2) {
				direccion = 4;
			}
			if (estadoDanoDireccion == 2) {
				direccion = 5;
			}
		}

	}

	private void animar() {
		if (!enColisionAgua(2)) {
			if (direccion >= 6) {
				direccion -= 4;
			}
			if (!enMovimiento && !invulnerable) {
				animacion = 0;
				if (!agachado) {
					estado = 0;
				}
			}
		} else if (direccion <= 3) {
			direccion += 4;
		}

		imagenActual = hs.obtenerSprite(direccion, estado).obtenerImagen();

	}

	public void dibujar(Graphics g) {
		final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;

		final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;

		g.setColor(Color.RED);
		g.drawString("coor: " + posicionX+"-"+posicionY, 20, 60);

		g.drawImage(imagenActual, centroX, centroY, null);
		if (!colisionHabilidades.isEmpty())
			g.drawRect(colisionHabilidades.get(0).x, colisionHabilidades.get(0).y, colisionHabilidades.get(0).width,
					colisionHabilidades.get(0).height);

//		 g.drawRect(LIMITE_ARRIBA_AGACHADO.x, LIMITE_ARRIBA_AGACHADO.y, LIMITE_ARRIBA_AGACHADO.width,
//		 LIMITE_ARRIBA_AGACHADO.height);
//		 g.drawRect(LIMITE_ABAJO_AGACHADO.x, LIMITE_ABAJO_AGACHADO.y, LIMITE_ABAJO_AGACHADO.width,
//		 LIMITE_ABAJO_AGACHADO.height);
//		 g.drawRect(LIMITE_IZQUIERDA_AGACHADO.x, LIMITE_IZQUIERDA_AGACHADO.y,
//		 LIMITE_IZQUIERDA_AGACHADO.width, LIMITE_IZQUIERDA_AGACHADO.height);
//		 g.drawRect(LIMITE_DERECHA_AGACHADO.x, LIMITE_DERECHA_AGACHADO.y, LIMITE_DERECHA_AGACHADO.width,
//		 LIMITE_DERECHA_AGACHADO.height);

	}

	public void establecerPosicionX(double posicionX) {
		this.posicionX = posicionX;
	}

	public void establecerPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}

	public double obtenerPosicionX() {
		return posicionX;
	}

	public double obtenerPosicionY() {
		return posicionY;
	}

	public Rectangle obtenerLIMITE_DERECHA() {
		return LIMITE_DERECHA;
	}

	public Rectangle obtenerLIMITE_IZQUIERDA() {
		return LIMITE_IZQUIERDA;
	}

	public Rectangle[] obtenerLimitesJugador() {
		return limitesJugador;
	}

}
