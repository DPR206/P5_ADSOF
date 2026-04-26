package exceptions;

public class ObjetoSinSalida extends Exception{

	private static final long serialVersionUID = 1L;
	public String nodo;
	public Object objeto;
	
	/**
	 * @param nodo
	 * @param objeto
	 */
	public ObjetoSinSalida(String nodo, Object objeto) {
		this.nodo = nodo;
		this.objeto = objeto;
	}

	@Override
	public String toString() {
		return "El objeto " + objeto.toString() + " no cumple ninguna condición de salida del nodo " + nodo;
	}
	
	
}
