package dataset;

import java.util.*;

public class Dataset<T> {

	private final List<T> objects;
	private final Featurizer<T> featurizer;

	public Dataset(Featurizer<T> featurizer) {
		this.featurizer = featurizer;
		this.objects = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public <V extends Comparable<V>> Feature<V> getFeature(String featureName) {
		List<String> names = featurizer.getFeatureNames();
		int index = names.indexOf(featureName);
		if (index == -1)
			throw new IllegalArgumentException("Feature no encontrada: " + featureName);

		Feature<V> feature = new Feature<>(featureName);
		for (T obj : objects) {
			feature.add((V) featurizer.getFeatureValues(obj).get(index));
		}
		return feature;
	}

	public void removeDuplicates() {
		List<List<Object>> seen = new ArrayList<>();
		Iterator<T> it = objects.iterator();
		while (it.hasNext()) {
			List<Object> values = featurizer.getFeatureValues(it.next());
			if (seen.contains(values)) {
				it.remove();
			} else {
				seen.add(values);
			}
		}
	}

	public List<String> getFeatureNames() {
		return featurizer.getFeatureNames();
	}

	public int size() {
		return objects.size();
	}

	@SuppressWarnings("unchecked")
	public void addAll(T... items) {
		Collections.addAll(objects, items);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		List<String> names = featurizer.getFeatureNames();
		for(String n : names) {
			sb.append(n + ": ");
			sb.append(getFeature(n) + " ");
		}
		sb.append("}");

		return sb.toString();
	}
}