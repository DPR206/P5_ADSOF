package decision_tree;

import java.util.*;
import java.util.function.Predicate;

public class Nodo<T> {

	private String etiqueta;
	private List<Predicate<? super T>> conditions = new LinkedList<>();
	
	/**
	 * @param etiqueta
	 */
	public Nodo(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the conditions
	 */
	public List<Predicate<? super T>> getConditions() {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<Predicate<? super T>> conditions) {
		this.conditions = conditions;
	}
	
	public void addCondition(Predicate<? super T> condition) {
		this.conditions.add(condition);
	}
	
	@Override
	public String toString() {
		return etiqueta;
	}
}

