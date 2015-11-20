package principal;

public class Constantes {

	public static final int LADO_SPRITE = 32;
	public static final int LADO_TILE = 32;

	public static int ANCHO_JUEGO = 640;
	public static int ALTO_JUEGO = 360;

	public static int ANCHO_PANTALLA_COMPLETA =640;//1366
	public static int ALTO_PANTALLA_COMPLETA  =360;//768

	public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA
			/ (double) ANCHO_JUEGO;
	public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA
			/ (double) ALTO_JUEGO;

	public static int CENTRO_VENTANA_X = ANCHO_JUEGO / 2;
	public static int CENTRO_VENTANA_Y = ALTO_JUEGO / 2;

}
