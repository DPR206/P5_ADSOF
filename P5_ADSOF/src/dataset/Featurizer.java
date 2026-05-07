package dataset;

import java.util.List;

/**
 * Esta clase representa un featurizer
 * @param <T> Tipo de la clase para la que se usa el featurizer
 */
public interface Featurizer<T> {

	/**
	 * Devuelve la lista de nombres de los features
	 * @return Lista de los nombres
	 */
	public List<String> getFeatureNames();

	/**
	 * Devuelve el valor de un objeto para un feature en concreto
	 * @param <V> Tipo de los valores que devuelve el feature del objeto
	 * @param object Objeto del que se quiere extraer el valor
	 * @param featureName Nombre del feature que se quiere extraer
	 * @return El valor de ese feature del objeto
	 */
	public <V extends Comparable<? super V>> V getFeatureValue(T object, String featureName);
}