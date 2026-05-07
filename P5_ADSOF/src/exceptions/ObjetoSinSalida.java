package exceptions;

public class ObjetoSinSalida extends Exception{

	private static final long serialVersionUID = 1L;
	public Object objeto;
	
	/**
	 * @param nodo
	 * @param objeto
	 */
	public ObjetoSinSalida(Object objeto) {
		super("El objeto " + objeto.toString() + "no tiene ninguna hoja asociada");
	}	
	
}
