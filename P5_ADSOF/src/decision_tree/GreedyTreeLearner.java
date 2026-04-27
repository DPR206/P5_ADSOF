package decision_tree;

import java.util.*;

import dataset.*;

public class GreedyTreeLearner<T, S> {


	public GreedyTreeLearner() {}

	
	public DecisionTree<T> learn(LabeledDataSet<T, S> dataset){
		return buildTree(dataset, dataset.getListFeatures());
				
	}
	
	/*public DecisionTree<T> learn(Collection<T> data){
		return buildTree(data.stream().toList(), data);
				
	}*/
	
	//a lo mejor en vez de una lista de features debería ser una lista de strings de features
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
		
		int indexFeature = (int) ((Math.random() * ((availableFeatures.size()-1) - 0 + 1)) + 0);
		//(Math.random() * (max - min + 1)) + min
		
		Feature<?> featureSplit = availableFeatures.get(indexFeature);
		availableFeatures.remove(featureSplit);
		
		//split data into subsets based on featureSplit, the number of subsets is equal to the number of different values
		//of featureSplit in the data
		
		DecisionTree<T> resultTree = new DecisionTree<>();
		
		//for-each subset de data {x in data | x.feat == value}
		// 	añadir la condicion featureSplit == value 
		//	resultTree.addArbolHijo(nodo, this.buildTree(subset, availableFeatures));
		
		
		return resultTree;
	}


	private DecisionTree<T> buildTree(List<T> objects, List<Feature<?>> availableFeatures) {
		
	
		
		return null;
	}
	
}
