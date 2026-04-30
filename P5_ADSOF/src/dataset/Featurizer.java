package dataset;

import java.util.List;

public interface Featurizer<T> {

	public List<String> getFeatureNames();

	public <V extends Comparable<? super V>> V getFeatureValue(T object, String featureName);
}