package tests;

import dataset.LabelProvider;

public class IsOldMale implements LabelProvider<Person, Boolean>{

	@Override
	public Boolean asignarEtiqueta(Person p) {
		return (p.isMale() && p.getAge() > 65 && p.getHeight() < 67);
	}

}
