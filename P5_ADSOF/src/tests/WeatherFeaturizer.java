package tests;

import java.util.List;

import dataset.Featurizer;

/**
 * Esta clase representa un featurizer de Weather
 */
public class WeatherFeaturizer implements Featurizer<Weather>{
	
	/**
	 * Cosntructor del featurizer de Weather
	 */
	public WeatherFeaturizer() {
		
	}

	@Override
	public List<String> getFeatureNames() {
		return List.of("weather condition", "temperature");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V extends Comparable<? super V>> V getFeatureValue(Weather w, String featureName) {
		return switch (featureName) {
        case "weather condition" -> (V) w.getCondition();
        case "temperature" -> (V) w.getTemperature();
        default -> throw new IllegalArgumentException("Unknown feature: " + featureName);
    };
	}

}
