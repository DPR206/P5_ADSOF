package exceptions;

/**
 * Excepción padre de las excepciones de la práctica
 */
public abstract class DecisionTreeDatasetException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Cosntructor de una excepción
	 * @param message Mensaje de la excepción
	 */
	public DecisionTreeDatasetException(String message) {
		super(message);
	}
}
