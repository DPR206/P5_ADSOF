package strategies;

import java.util.Set;
import dataset.LabeledDataSet;

public interface Strategy<T, S> {
	String chooseBestFeature(LabeledDataSet<T, S> dataset, Set<String> availableFeatures);
}
