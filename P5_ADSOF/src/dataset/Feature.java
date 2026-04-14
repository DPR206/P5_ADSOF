package dataset;

import java.util.*;

public class Feature<V extends Comparable<V>> extends ArrayList<V> {

	private static final long serialVersionUID = 1L;
	private final String name;

	public Feature(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

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