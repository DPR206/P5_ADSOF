package strategies;

import java.util.*;
import dataset.LabeledDataSet;

public class MisclassificationStrategy<T, S> implements Strategy<T, S> {

	@Override
	public String chooseBestFeature(LabeledDataSet<T, S> dataset, Set<String> availableFeatures) {
		String bestFeature = null;
		int bestScore = Integer.MAX_VALUE;

		for (String feature : availableFeatures) {
			Map<?, LabeledDataSet<T, S>> groups = dataset.split(feature, new LinkedList<>());
			int score = 0;

			for (LabeledDataSet<T, S> group : groups.values()) {
				Map<S, Long> labelCounts = new HashMap<>();
				for (T obj : group.getObjects()) {
					S label = group.getLabel(obj);
					labelCounts.merge(label, 1L, Long::sum);
				}
				long majorityCount = Collections.max(labelCounts.values());
				score += group.size() - majorityCount;
			}

			if (score < bestScore) {
				bestScore = score;
				bestFeature = feature;
			}
		}
		return bestFeature;
	}
}