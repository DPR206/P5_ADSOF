package tests;

import java.util.List;

import dataset.Featurizer;

public class PersonFeaturizer implements Featurizer<Person> {

	@Override
	public List<String> getFeatureNames() {
		return List.of("age", "weight", "height", "gender");
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V extends Comparable<? super V>> V getFeatureValue(Person p, String featureName) {
	    return switch (featureName) {
	        case "age" -> (V) Integer.valueOf(p.getAge());
	        case "weight" -> (V) Double.valueOf(p.getWeight());
	        case "height" -> (V) Double.valueOf(p.getHeight());
	        case "gender" -> (V) (p.isMale() ? "MALE" : "FEMALE");
	        default -> throw new IllegalArgumentException("Unknown feature: " + featureName);
	    };
	}
}