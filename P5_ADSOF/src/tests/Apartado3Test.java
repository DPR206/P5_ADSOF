package tests;

import java.util.function.Predicate;

import decision_tree.DecisionTree;
import exceptions.CicloArbol;

public class Apartado3Test {

	public static void main(String[] args) {
		
		DecisionTree<Person> dt = buildDecisionTree();
		
		Predicate<Person> isOldMale = dt.getPredicate("old male");
		
		System.out.println(isOldMale.test(new Person("Miguel", 86, 72, 165, true)));
		System.out.println(isOldMale.test(new Person("Clara", 42, 59, 162, false)));
		
	}
	
	private static DecisionTree<Person> buildDecisionTree(){
		DecisionTree<Person> dt = new DecisionTree<>();
		try {
			dt.node("root")
				.withCondition("male", p -> p.isMale())
				.otherwise("female");
		} catch (CicloArbol e) {
			e.printStackTrace();
		}
		try {
			dt.node("male")
				.withCondition("old male", p -> p.getAge() > 65)
				.withCondition("middle male", p -> p.getAge() <= 65 && p.getAge() > 34)
				.otherwise("young male");
		} catch (CicloArbol e) {
			e.printStackTrace();
		}
		return dt;
	}
}
