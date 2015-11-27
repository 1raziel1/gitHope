package principal.maquinaDeEstado.estados.menuinicial;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.mapas.Mapa;
import principal.maquinaDeEstado.EstadoJuego;
import principal.maquinaDeEstado.GestorEstados;

public class GestorMenuInicial implements EstadoJuego{
	GestorEstados ge;
	Mapa mapaFondo=new Mapa("/mapas/nivel1");
	private int posicionX=32*10, posicionY=32*10;
	
	private int posVentanaX=Constantes.ANCHO_PANTALLA_COMPLETA/7;
	private int posVentanaY=Constantes.ALTO_PANTALLA_COMPLETA/8;
	private int ventanaAlto=Constantes.ALTO_PANTALLA_COMPLETA/4;
	private int ventanaAncho=Constantes.ANCHO_PANTALLA_COMPLETA/5;
	
	public GestorMenuInicial(GestorEstados ge){
		this.ge=ge;
	
	}

	@Override
	public void actualizar() {
		animarFondo();
	}
	private void animarFondo(){
		int frecuenciaActualizacion = 2;
	

		if (GestorPrincipal.obtenerAps() % frecuenciaActualizacion == 0) {
			posicionX++;
			if(posicionX>(110*32)){
				posicionX=32*10;
			}
		}
		
	}

	@Override
	public void dibujar(Graphics g) {
		
		mapaFondo.dibujar(g, posicionX, posicionY);
		
		g.setColor(Color.BLACK);
		g.fillRect(posVentanaX,posVentanaY , ventanaAncho, ventanaAlto);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",0,15));
		g.drawString("Iniciar", posVentanaX+ventanaAncho/3, posVentanaY+65);
		g.drawString("Opciones", posVentanaX+ventanaAncho/3, posVentanaY+90);
		g.drawString("Salir", posVentanaX+ventanaAncho/3, posVentanaY+115);
		
		
	}

	@Override
	public void continuar() {
		// TODO Auto-generated method stub
		
	}

}
