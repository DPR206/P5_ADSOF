package tests;

import java.util.List;

import dataset.Featurizer;

public class PersonFeaturizer implements Featurizer<Person> {

	@Override
	public List<String> getFeatureNames() {
		return List.of("age", "weight", "height", "gender");
	}

	@Override
	public List<Object> getFeatureValues(Person p) {
		return List.of(p.getAge(), p.getWeight(), p.getWeight(), p.isMale() ? "MALE" : "FEMALE");
	}
}