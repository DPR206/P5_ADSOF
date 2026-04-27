package dataset;

import java.util.*;

public class Dataset<T> {

	private final Map<String, Feature<?>> features;
	private final Featurizer<T> featurizer;
	private int size;
	private List<T> objects = new ArrayList<>();

	public Dataset(Featurizer<T> featurizer) {
		this.featurizer = featurizer;
		this.size = 0;
		this.features = new LinkedHashMap<>();
		for (String name : featurizer.getFeatureNames()) {
			features.put(name, new Feature<>(name));
		}
	}

	@SuppressWarnings("unchecked")
    public <V extends Comparable<V>> Feature<V> getFeature(String name) {
        Feature<?> f = features.get(name);
        if (f == null) throw new IllegalArgumentException("Feature no encontrada: " + name);
        return (Feature<V>) f;
    }

	public void removeDuplicates() {
        Set<String> seen = new HashSet<>();
        List<String> names = featurizer.getFeatureNames();
        int i = 0;
        while (i < size) {
            StringBuilder key = new StringBuilder();
            for (String name : names) {
                key.append(features.get(name).get(i)).append("|");
            }
            if (!seen.add(key.toString())) {
                for (String name : names) {
                    features.get(name).remove(i);
                }
                size--;
            } else {
                i++;
            }
        }
    }

	public List<String> getFeatureNames() {
		return featurizer.getFeatureNames();
	}

	public int size() {
		return size;
	}

	/**
	 * @return the objects
	 */
	public List<T> getObjects() {
		return objects;
	}

	@SuppressWarnings("unchecked")
    private <V extends Comparable<V>> void addToFeature(Feature<?> feature, Object value) {
        ((Feature<V>) feature).add((V) value);
    }

    @SuppressWarnings("unchecked")
	public void addAll(T... items) {
        for (T item : items) {
        	this.objects.add(item);
            List<Object> values = featurizer.getFeatureValues(item);
            List<String> names = featurizer.getFeatureNames();
            for (int i = 0; i < names.size(); i++) {
                addToFeature(features.get(names.get(i)), values.get(i));
            }
            size++;
        }
    }

	/**
	 * @return the features
	 */
	public Map<String, Feature<?>> getFeatures() {
		return features;
	}

	@Override
	public String toString() {
		return features.toString();
	}
}