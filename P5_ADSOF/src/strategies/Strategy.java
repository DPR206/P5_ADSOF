package strategies;

import java.util.Set;
import dataset.LabeledDataSet;

/**
 * Esta interfaz representa una estrategia de seleccion de la mejor feature
 * @param <T> Tipo del objeto que se guarda en el dataset
 * @param <S> Tipo del valor de la etiqueta del labeledDataset
 */
public interface Strategy<T, S> {
	/**
	 * Elige la mejor estrategia para un algoritmo greedy de construccion de árboles
	 * @param dataset LabeledDataset del que se cogen los valores
	 * @param availableFeatures Valores posibles de features
	 * @return La mejor estrategia elegida
	 */
	String chooseBestFeature(LabeledDataSet<T, S> dataset, Set<String> availableFeatures);
}
