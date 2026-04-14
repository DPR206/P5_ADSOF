package dataset;

import java.util.List;

public interface Featurizer<T> {

	List<String> getFeatureNames();

	List<Object> getFeatureValues(T object);
}