package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.control.Raton;
import principal.maquinaDeEstado.GestorEstados;

public class SuperficieDibujo extends Canvas {

	private static final long serialVersionUID = -6227038142688953660L;
	private int ancho;
	private int alto;

	private Raton raton;

	public SuperficieDibujo(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		this.raton = new Raton(this);

		setIgnoreRepaint(true);
		setCursor(raton.ObtenerCursor());

		setPreferredSize(new Dimension(ancho, alto));
		addKeyListener(GestorControles.teclado);
		setFocusable(true);
		requestFocus();

	}
	public void actualizar(){
		raton.actualizar(this);
	}

	public void dibujar(final GestorEstados ge) {
		BufferStrategy buffer = getBufferStrategy();

		if (buffer == null) {
			createBufferStrategy(4);
			return;
		}

		Graphics2D g = (Graphics2D)buffer.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);
		
		if(Constantes.FACTOR_ESCALADO_X!=1.0 || Constantes.FACTOR_ESCALADO_Y!=1.0){
			g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);

		}
		
		ge.dibujar(g);
		g.drawString("FPS:"+GestorPrincipal.obtenerFps(), 20, 40);
		g.drawString("APS:"+GestorPrincipal.obtenerAps(), 20, 50);
		raton.dibujar(g);
		
		Toolkit.getDefaultToolkit().sync();

		g.dispose();
		buffer.show();
	}

	public int obtenerAncho() {
		return ancho;
	}

	public int obtenerAlto() {
		return alto;
	}

}
