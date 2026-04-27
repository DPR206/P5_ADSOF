package decision_tree;

import java.util.*;

import dataset.*;

public class GreedyTreeLearner<T, S> {


	public GreedyTreeLearner() {}

	
	public DecisionTree<T> learn(LabeledDataSet<T, S> dataset){
		return buildTree(dataset.getObjects(), dataset.getFeatures());
				
	}
	
	/*public DecisionTree<T> learn(List<T> data){
		return buildTree(data, data);
				
	}*/
	
	private DecisionTree<T> buildTree(LabeledDataSet<T, S> objects, Map<String, Feature<?>> features) {
		
		if(features.keySet().size() == 1)
		
		return null;
	}


	private DecisionTree<T> buildTree(List<T> objects, Map<String, Feature<?>> features) {
		
	
		
		return null;
	}
	
}
