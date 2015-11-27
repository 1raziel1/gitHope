package principal.maquinaDeEstado;

import java.awt.Graphics;
import java.awt.Point;

import principal.graficos.SuperficieDibujo;
import principal.maquinaDeEstado.estados.juego.GestorJuego;
import principal.maquinaDeEstado.estados.menuinicial.GestorMenuInicial;
import principal.maquinaDeEstado.estados.menujuego.GestorMenu;

public class GestorEstados {

	private EstadoJuego estados[];
	private EstadoJuego estadoActual;
	private GestorMenu gm;

	public GestorEstados(SuperficieDibujo sd) {
		iniciarEstados();
		gm = new GestorMenu(this,sd.obtenerRaton());
		estados[1]=gm;
	
		iniciarEstadoActual();
	
	}

	private void iniciarEstados() {
		estados = new EstadoJuego[3];
		estados[0] = new GestorJuego(this);
		estados[2]=new GestorMenuInicial(this);
		
	}
	public void cargarMapa(String url,Point puntoEntrada){
		estados[0]=new GestorJuego(this,url,puntoEntrada);
	}

	private void iniciarEstadoActual() {
		estadoActual = estados[2];
	}

	public void actualizar() {
		estadoActual.actualizar();
	}

	public void dibujar(final Graphics g) {
		estadoActual.dibujar(g);
	}

	public void cambiarEstadoActual(final int nuevoEstado) {
		estadoActual = estados[nuevoEstado];
	}
	public void reiniciarJuego(){
		estados[0]=new GestorJuego(this);
		estados[1]=gm;
		estadoActual = estados[0];
	}
	public void continuarJuego(){
		estadoActual = estados[0];
		
		estados[0].continuar();
	}

	public EstadoJuego obtenerEstadoActual() {
		return estadoActual;
	}
	
}
