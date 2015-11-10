package principal.entes;

import java.awt.Point;

import principal.mapas.Mapa;

public class Bloque {
//bloque que se pueda empujar
	private Mapa mapa;
	private Point posicion;
	private Jugador jugador;
	
	public Bloque(Jugador jugador,Point posicion,Mapa mapa){
		this.jugador=jugador;
		this.posicion=posicion;
		this.mapa=mapa;
	}
	
	
	public void actualizar(int posicionX, int posicionY) {

		actualizarColisiones(posicionX, posicionY);

		movimiento(posicion);

		animar();

	}
	private void movimiento(Point posicion) {
		
	}
	private void animar() {
		// TODO Auto-generated method stub
		
	}
	
	private void actualizarColisiones(int posicionX, int posicionY) {
		// TODO Auto-generated method stub
		
	}

}


