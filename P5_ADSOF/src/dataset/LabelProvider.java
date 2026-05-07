package dataset;

/**
 * Esta interfaz representa un etiquetador de objetos
 * @param <T> Objeto que se etiqueta
 * @param <S> Tipo con el que se etiqueta a los objetos
 */
public interface LabelProvider<T, S> {
	
	/**
	 * Asigna una etiqueta a un objeto
	 * @param object Objeto que se etiqueta
	 * @return Valor con el que se etiqueta
	 */
	S asignarEtiqueta(T object);

}
