package dataset;

public interface LabelProvider<T, S> {
	
	S asignarEtiqueta(T object);

}
