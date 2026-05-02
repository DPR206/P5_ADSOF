package dataset;

import java.util.LinkedHashMap;
import java.util.Map;

public class LabeledDataSet<T, S> extends Dataset<T> {

	private LabelProvider<T, S> etiquetador;

	public LabeledDataSet(Featurizer<T> featurizer, LabelProvider<T, S> etiquetador) {
		super(featurizer);
		this.etiquetador = etiquetador;
	}

	/**
	 * @return the etiquetador
	 */
	public LabelProvider<T, S> getEtiquetador() {
		return etiquetador;
	}

	public S labelOf(T object) {
		return etiquetador.asignarEtiqueta(object);
	}

	public boolean allSameLabel() {
		if (objects.isEmpty())
			return true;
		S first = labelOf(objects.get(0));
		return objects.stream().allMatch(o -> labelOf(o).equals(first));
	}
	
	@SuppressWarnings("unchecked")
	public Map<Object, LabeledDataSet<T, S>> split(String feature) {
		Map<Object, LabeledDataSet<T, S>> subsets = new LinkedHashMap<>();
		for (T obj : objects) {
			Object val = featurizer.getFeatureValue(obj, feature);
			LabeledDataSet<T, S> newDataset = new LabeledDataSet<>(featurizer, etiquetador);
			newDataset.removeFeature(feature);

			subsets.putIfAbsent(val, newDataset);
			subsets.get(val).addAll(obj);
		}
		return subsets;
	}

	public Object getLabel(T obj) {
		return etiquetador.asignarEtiqueta(obj);
	}
}
