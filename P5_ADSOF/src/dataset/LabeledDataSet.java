package dataset;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *Esta clase representa un dataset con un etiquetador
 * @param <T> Tipo de los objetos que guarda el dataset
 * @param <S> Tipo de las etiquetas del etiquetador
 */
public class LabeledDataSet<T, S> extends Dataset<T> {
	/** Etiquetador */
	private LabelProvider<T, S> etiquetador;

	/**
	 * Constructor del dataset etiquetado
	 * @param featurizer Featurizer del dataset
	 * @param etiquetador Etiquetador del labeledDataset
	 */
	public LabeledDataSet(Featurizer<T> featurizer, LabelProvider<T, S> etiquetador) {
		super(featurizer);
		this.etiquetador = etiquetador;
	}

	/**
	 * Devuelve el etiquetador del labeledDataset
	 * @return EL etiquetador
	 */
	public LabelProvider<T, S> getEtiquetador() {
		return etiquetador;
	}

	/**
	 * Devuelve la etiqueta de un objeto
	 * @param object Objeto que se evalua
	 * @return La etiqueta asociada al objeto
	 */
	public S labelOf(T object) {
		return etiquetador.asignarEtiqueta(object);
	}

	/**
	 * Devueleve si todos los objetos del dataset tienen la misma etiqueta
	 * @return True si la tienen, false en caso contrario
	 */
	public boolean allSameLabel() {
		if (objects.isEmpty())
			return true;
		S first = labelOf(objects.get(0));
		return objects.stream().allMatch(o -> labelOf(o).equals(first));
	}

	/**
	 * Separa el labeledDataset en función de los valores de una feature
	 * @param feature Feature que separa el dataset
	 * @param removedFeatures Las features que se deben remover al nuevo dataset
	 * @return Mapa de los subsets creados
	 */
	public Map<?, LabeledDataSet<T, S>> split(String feature, List<String> removedFeatures) {
		return splitByFeature(feature, removedFeatures);
	}

	/**
	 * Separa el labeledDataset en función de los valores de una feature
	 * @param <V> Tipo de los valores de la feature concreta
	 * @param feature Feature que separa el dataset
	 * @param removedFeatures Las features que se deben remover al nuevo dataset
	 * @return Mapa de los subsets creados
	 */
	@SuppressWarnings("unchecked")
	private <V> Map<V, LabeledDataSet<T, S>> splitByFeature(String feature, List<String> removedFeatures) {
		Map<V, LabeledDataSet<T, S>> subsets = new LinkedHashMap<>();
		for (T obj : objects) {
			V val = featurizer.getFeatureValue(obj, feature);
			subsets.putIfAbsent(val, createSubset(feature, removedFeatures));
			subsets.get(val).addAll(obj);
		}
		return subsets;
	}

	/**
	 * Crea un subset nuevo
	 * @param feature Feature que se debe quitar
	 * @param removedFeatures Nuevas features que se deben quitar
	 * @return Nuevo labeledDataset
	 */
	private LabeledDataSet<T, S> createSubset(String feature, List<String> removedFeatures) {
		LabeledDataSet<T, S> newDataset = new LabeledDataSet<>(featurizer, etiquetador);
		newDataset.removeFeature(feature);
		for (String s : removedFeatures) {
			newDataset.removeFeature(s);
		}
		return newDataset;
	}
}
