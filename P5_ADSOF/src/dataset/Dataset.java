package dataset;

import java.util.*;

public class Dataset<T> {

	private final Map<String, Feature<?>> features;
	protected final Featurizer<T> featurizer;
	protected List<T> objects = new ArrayList<>();

	public Dataset(Featurizer<T> featurizer) {
		this.featurizer = featurizer;
		this.features = new LinkedHashMap<>();
		for (String name : featurizer.getFeatureNames()) {
			features.put(name, new Feature<>(name));
		}
	}

	public Featurizer<T> getFeaturizer() {
		return featurizer;
	}

	@SuppressWarnings("unchecked")
	public <V extends Comparable<V>> Feature<V> getFeature(String name) {
		Feature<?> f = features.get(name);
		if (f == null)
			throw new IllegalArgumentException("Feature no encontrada: " + name);
		return (Feature<V>) f;
	}

	// Falta arreglar para diferentes tipos de duplicados
	public <V> void removeDuplicates() {
		List<String> features = featurizer.getFeatureNames();
		Set<T> possiblyDuplicated = new HashSet<>(objects);

		// Encontrar los duplicados reduciendo el número de posibles duplicados en cada iteración
		for (String feat : features) {
			Map<V, T> seenValueToObj = new HashMap<>();
			Set<T> confirmedDuplicates = new HashSet<>();

			for (T obj : possiblyDuplicated) {
				V value = featurizer.getFeatureValue(obj, feat);

				//Si no está duplicado, se pone en la lista de posibles,
				//si está duplicado, se busca el otro objeto que duplica y se añaden ambos a posibles duplicados
				if (!seenValueToObj.containsKey(value)) {
					seenValueToObj.put(value, obj);
				} else {
					T firstObj = seenValueToObj.get(value);
					if (!confirmedDuplicates.contains(firstObj)) {
						confirmedDuplicates.add(firstObj);
					}
					confirmedDuplicates.add(obj);
				}
			}
			possiblyDuplicated = confirmedDuplicates;

			if (possiblyDuplicated.isEmpty())
				break;
		}

		// Eliminar duplicados de objects y features
		if (!possiblyDuplicated.isEmpty()) {
			//Mantenemos solo uno de los duplicados
			List<T> toRemove = new ArrayList<>(possiblyDuplicated);
			toRemove.remove(0);

			List<Integer> indicesToRemove = new ArrayList<>();
			for (T obj : toRemove) {
				int idx = objects.indexOf(obj);
				if (idx != -1) {
					indicesToRemove.add(idx);
				}
			}
			indicesToRemove.sort(Collections.reverseOrder());
			for (String feat : featurizer.getFeatureNames()) {
				Feature<?> feature = this.features.get(feat);
				for (int idx : indicesToRemove) {
					feature.remove(idx);
				}
			}
			
			objects.removeAll(toRemove);
		}
	}

	public List<String> getFeatureNames() {
		return featurizer.getFeatureNames();
	}

	public int size() {
		return objects.size();
	}

	/**
	 * @return the objects
	 */
	public List<T> getObjects() {
		return objects;
	}

	@SuppressWarnings("unchecked")
	private <V extends Comparable<? super V>> void addToFeature(Feature<?> feature, Object value) {
		((Feature<? super V>) feature).add((V) value);
	}

	@SuppressWarnings("unchecked")
	public void addAll(T... items) {
		for (T item : items) {
			this.objects.add(item);
			for(String f : features.keySet()) {
				addToFeature(features.get(f), featurizer.getFeatureValue(item, f));
			}
		}
	}
	
	protected void removeFeature(String feature) {
		features.remove(feature);
	}

	/**
	 * @return the features
	 */
	public Map<String, Feature<?>> getFeatures() {
		return features;
	}

	public List<Feature<?>> getListFeatures() {
		return this.features.values().stream().toList();
	}

	@Override
	public String toString() {
		return features.toString();
	}
}