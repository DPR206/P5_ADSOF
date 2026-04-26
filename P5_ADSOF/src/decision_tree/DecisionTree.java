/**
 * 
 */
package decision_tree;

import java.util.*;
import java.util.function.Predicate;

import dataset.*;
import exceptions.*;

/**
 * 
 */
public class DecisionTree <T>{
	
	private Nodo<T> raiz = null;
	private Nodo<T> nodo = null;
	private Map<Nodo<T>, List<DecisionTree<T>>> nodos = new LinkedHashMap<>();
	private Map<String, List<T>> classification = new LinkedHashMap<>();
	
	public DecisionTree () {}
	
	public DecisionTree<T> node(String name) {
		
		if(this.nodos.isEmpty()) {
			Nodo<T> raiz = new Nodo<T>(name);
			this.raiz = raiz;
			this.nodo = raiz;
			this.nodos.put(raiz, new ArrayList<>());
		}
		
		this.nodo = this.encontrarNodo(raiz, name);
		
		return this;
	}
	
	
	public Nodo<T> encontrarNodo(Nodo<T> nodoActual, String etiqueta){
		
		if (nodoActual.getEtiqueta().equals(etiqueta)) 
			return nodoActual;
		
        List<DecisionTree<T>> hijos = nodos.get(nodoActual);
        
        if (hijos != null) {
            for (DecisionTree<T> hijo : hijos) {
                Nodo<T> encontrado = hijo.encontrarNodo(hijo.getNodo(), etiqueta);
                if (encontrado != null) 
                	return encontrado;
            }
        }
        
        return null;
	}
	
	public DecisionTree<T> withCondition(String etiqueta, Predicate<? super T> condition)  throws CicloArbol {
		
		DecisionTree<T> arbolHijo = new DecisionTree<>();
		if(this.encontrarNodo(raiz, etiqueta) != null)
			throw new CicloArbol(etiqueta, this.nodo.getEtiqueta());
		arbolHijo.setNodo(new Nodo<T>(etiqueta));
		
		this.nodos.computeIfAbsent(this.nodo, n -> new ArrayList<>()).add(arbolHijo);
		this.nodo.addCondition(condition);
			
		return this;
	}

	public DecisionTree<T> otherwise(String etiqueta)  throws CicloArbol {
		
		DecisionTree<T> arbolHijo = new DecisionTree<>();	
		if(this.encontrarNodo(raiz, etiqueta) != null)
			throw new CicloArbol(etiqueta, this.nodo.getEtiqueta());
		arbolHijo.setNodo(new Nodo<T>(etiqueta));
		
		this.nodos.computeIfAbsent(this.nodo, n -> new ArrayList<>()).add(arbolHijo);
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<T>> predict(T...elements) throws ObjetoSinSalida{
		this.classification.clear();
		for(T element : elements) {
			String resultadoEtiqueta = predictEach(this.raiz, element);
	        
	        this.classification.computeIfAbsent(resultadoEtiqueta, k -> new ArrayList<>()).add(element);
		}
		
		return classification;
	}
	
	public String predictEach(Nodo<T> actual, T element) throws ObjetoSinSalida{
		
		List<Predicate<? super T>> conditions = actual.getConditions();
		List<DecisionTree<T>> hijos = this.nodos.get(actual);
		
		/*Hemos llegado a un nodo hoja*/
		if(hijos == null || hijos.isEmpty())
			return actual.getEtiqueta();
		
		for (int i = 0; i < conditions.size(); i++) {
            if (conditions.get(i).test(element)) {
            	return predictEach(hijos.get(i).getNodo(), element);
            }
        }
		
		/*Caso otherwise*/
		if(hijos.size() > conditions.size()) {
			DecisionTree<T> otherwise = hijos.get(hijos.size() - 1);
			return otherwise.predictEach(otherwise.getNodo(), element);
		} else
			throw new ObjetoSinSalida(actual.getEtiqueta(), element);
		
		//return null;
		//return this.nodo.getEtiqueta();
	}
	
	public Map<String, List<T>> predict(Dataset<T> data) throws ObjetoSinSalida{
		this.classification.clear();
		for(T element : data.getObjects()) {
			String resultadoEtiqueta = predictEach(this.raiz, element);
	        
	        this.classification.computeIfAbsent(resultadoEtiqueta, k -> new ArrayList<>()).add(element);
		}
		
		return classification;
	}

	/**
	 * @return the nodo
	 */
	public Nodo<T> getNodo() {
		return nodo;
	}


	/**
	 * @param nodo the nodo to set
	 */
	public void setNodo(Nodo<T> nodo) {
		this.nodo = nodo;
	}
	
	
}
