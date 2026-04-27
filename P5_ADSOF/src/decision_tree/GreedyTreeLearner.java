package decision_tree;

import java.util.*;

import dataset.*;

public class GreedyTreeLearner<T, S> {


	public GreedyTreeLearner() {}

	
	public DecisionTree<T> learn(LabeledDataSet<T, S> dataset){
		return buildTree(dataset.getObjects(), dataset.getListFeatures());
				
	}
	
	/*public DecisionTree<T> learn(List<T> data){
		return buildTree(data, data);
				
	}*/
	
	private DecisionTree<T> buildTree(LabeledDataSet<T, S> objects, List<Feature<?>> availableFeatures) {
		
		List<T> elements = objects.getObjects();
		int total = 0;
		LabelProvider<T, S> etiquetador = objects.getEtiquetador();
		S etiqueta = etiquetador.asignarEtiqueta(elements.getFirst());
		
		for(T element : elements) {
			if(!etiqueta.equals(etiquetador.asignarEtiqueta(element)))
				break;
			total++;
		}
		
		if(total == elements.size()) {
			DecisionTree<T> tree = new DecisionTree<>();
			tree.setNodo(new Nodo<T>(etiquetador.asignarEtiqueta(elements.getFirst()).toString()));
			return tree;
		}
		
		
		
		return null;
	}


	private DecisionTree<T> buildTree(List<T> objects, List<Feature<?>> availableFeatures) {
		
	
		
		return null;
	}
	
}
