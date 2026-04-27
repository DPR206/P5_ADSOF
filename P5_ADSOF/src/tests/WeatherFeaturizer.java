package tests;

import java.util.List;

import dataset.Featurizer;

public class WeatherFeaturizer implements Featurizer<Weather>{

	@Override
	public List<String> getFeatureNames() {
		return List.of("weather condition", "temperature");
	}

	@Override
	public List<Object> getFeatureValues(Weather w) {
		return List.of(w.getCondition(), w.getTemperature());
	}

}
