package strategies;

import java.util.Random;
import java.util.Set;
import dataset.LabeledDataSet;

public class RandomStrategy<T, S> implements Strategy<T, S> {

    @Override
    public String chooseBestFeature(LabeledDataSet<T, S> dataset, Set<String> availableFeatures) {
        int idx = new Random().nextInt(availableFeatures.size());
        return availableFeatures.stream().skip(idx).findFirst().orElseThrow();
    }
}