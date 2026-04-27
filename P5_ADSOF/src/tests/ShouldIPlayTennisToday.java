package tests;

import dataset.*;

public class ShouldIPlayTennisToday implements LabelProvider<Weather, Boolean>{

	@Override
	public Boolean asignarEtiqueta(Weather w) {
		return (w.getCondition() == WeatherCondition.SUNNY || w.getCondition() == WeatherCondition.CLOUDY 
				|| w.getCondition() == WeatherCondition.CLOUDY) && 
				(w.getTemperature() == Temperature.COOL || w.getTemperature() == Temperature.MILD ||
				 w.getTemperature() == Temperature.WARM);
	}

}
