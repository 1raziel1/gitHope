package principal.maquinaDeEstado;

import java.awt.Graphics;

public interface EstadoJuego {

	void actualizar();

	void dibujar(final Graphics g);
	
	void continuar();
}
