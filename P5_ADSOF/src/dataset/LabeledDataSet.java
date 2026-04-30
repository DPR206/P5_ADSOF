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
		if (objects.isEmpty()) return true;
		S first = labelOf(objects.get(0));
		return objects.stream().allMatch(o -> labelOf(o).equals(first));
	}

	public Map<Object, LabeledDataSet<T, S>> split(String feature) {
		int featureIndex = featurizer.getFeatureNames().indexOf(feature);
		Map<Object, LabeledDataSet<T, S>> subsets = new LinkedHashMap<>();
		for (T obj : objects) {
			Object val = featurizer.getFeatureValues(obj).get(featureIndex);
			subsets.computeIfAbsent(val, v -> new LabeledDataSet<>(featurizer, etiquetador)).objects.add(obj);
		}
		return subsets;
	}

}
