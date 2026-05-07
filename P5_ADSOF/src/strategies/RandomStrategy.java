package strategies;

import java.util.Random;
import java.util.Set;
import dataset.LabeledDataSet;

/**
 * Esta clase representa una estrategia aleatoria para seleccionar la mejor feature
 * @param <T> Tipo del objeto que se guarda en el dataset
 * @param <S> Tipo del valor de la etiqueta del labeledDataset
 */
public class RandomStrategy<T, S> implements Strategy<T, S> {
	
	/**
	 * Constructor de la estrategia aleatoria
	 */
	public RandomStrategy() {
		
	}

	@Override
	public String chooseBestFeature(LabeledDataSet<T, S> dataset, Set<String> availableFeatures) {
		int idx = new Random().nextInt(availableFeatures.size());
		return availableFeatures.stream().skip(idx).findFirst().orElseThrow();
	}
}