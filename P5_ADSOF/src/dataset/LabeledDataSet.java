package dataset;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LabeledDataSet<T, S> extends Dataset<T> {

	private LabelProvider<T, S> etiquetador;

	public LabeledDataSet(Featurizer<T> featurizer, LabelProvider<T, S> etiquetador) {
		super(featurizer);
		this.etiquetador = etiquetador;
	}

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

	public Map<?, LabeledDataSet<T, S>> split(String feature, List<String> removedFeatures) {
		return splitByFeature(feature, removedFeatures);
	}

	@SuppressWarnings("unchecked")
	private <V> Map<V, LabeledDataSet<T, S>> splitByFeature(String feature, List<String> removedFeatures) {
		Map<V, LabeledDataSet<T, S>> subsets = new LinkedHashMap<>();
		for (T obj : objects) {
			V val = featurizer.getFeatureValue(obj, feature);
			subsets.putIfAbsent(val, createSubset(feature, removedFeatures));
			subsets.get(val).addAll(obj);
		}
		return subsets;
	}

	private LabeledDataSet<T, S> createSubset(String feature, List<String> removedFeatures) {
		LabeledDataSet<T, S> newDataset = new LabeledDataSet<>(featurizer, etiquetador);
		newDataset.removeFeature(feature);
		for (String s : removedFeatures) {
			newDataset.removeFeature(s);
		}
		return newDataset;
	}

	public S getLabel(T obj) {
		return etiquetador.asignarEtiqueta(obj);
	}
}
