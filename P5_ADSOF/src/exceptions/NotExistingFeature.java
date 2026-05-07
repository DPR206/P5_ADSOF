package exceptions;

/**
 * Esta excepción se lanza en caso de que la feature que se busca no exista en un dataset
 */
public class NotExistingFeature extends DecisionTreeDatasetException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la excepción
	 * @param feature Feature que no se encuentra
	 */
	public NotExistingFeature(String feature) {
		super("No se encuentra la feature " + feature + " en el dataset");
	}
}
