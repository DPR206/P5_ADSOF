package dataset;

import java.util.*;

/**
 * Esta clase representa un feature
 * @param <V> Tipo genérico de los valores que hay en el feature
 */
public class Feature<V extends Comparable<? super V>> extends ArrayList<V> {
	private static final long serialVersionUID = 1L;
	/** Nombre de la feature */
	private final String name;

	/**
	 * Constructor del feature
	 * @param name Nombre del feature
	 */
	public Feature(String name) {
		this.name = name;
	}

	/**
	 * Devuelve el nombre del feature
	 * @return Nombre del feature
	 */
	public String getName() {
		return name;
	}

	/**
	 * Devuelve el mínimo valor del feature
	 * @return Valor mínimo
	 */
	public V getMin() {
		if (this.isEmpty())
			throw new NoSuchElementException();
		V min = this.get(0);
		for (V v : this) {
			if (v.compareTo(min) < 0)
				min = v;
		}
		return min;
	}

	/**
	 * Devuelve el máximo valor del feature
	 * @return Valor máximo
	 */
	public V getMax() {
		if (this.isEmpty())
			throw new NoSuchElementException();
		V max = this.get(0);
		for (V v : this) {
			if (v.compareTo(max) > 0)
				max = v;
		}
		return max;
	}

	/**
	 * Devuelve la distribución de valores, el número de cada valor
	 * @return La distribución de cada valor
	 */
	public Map<V, Long> getDistribution() {
		Map<V, Long> distribution = new HashMap<>();
		for (V v : this) {
			if (distribution.containsKey(v)) {
				distribution.put(v, distribution.get(v) + 1);
			} else {
				distribution.put(v, 1L);
			}
		}
		return distribution;
	}
}