package principal.maquinaDeEstado.estados.menuinicial;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.control.Raton;
import principal.mapas.Mapa;
import principal.maquinaDeEstado.EstadoJuego;
import principal.maquinaDeEstado.GestorEstados;

public class GestorMenuOpciones implements EstadoJuego{

	GestorEstados ge;
	Mapa mapaFondo=new Mapa("/mapas/nivel1");
	private int posicionX=32*10, posicionY=32*10;
	
	private int posVentanaX=Constantes.ANCHO_PANTALLA_COMPLETA/7;
	private int posVentanaY=Constantes.ALTO_PANTALLA_COMPLETA/8;
	private int ventanaAlto=Constantes.ALTO_PANTALLA_COMPLETA/4;
	private int ventanaAncho=Constantes.ANCHO_PANTALLA_COMPLETA/5;
	
	private Raton raton;
	private Rectangle ratonRec;
	private Rectangle configurarAudio;
	private Rectangle configurarTeclado;
	private Rectangle atras;
	
	private BufferedImage img;
	
	private boolean i,o,s;
	
	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dibujar(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void continuar() {
		// TODO Auto-generated method stub
		
	}

}
