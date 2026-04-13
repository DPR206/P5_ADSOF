package dataset;

import java.util.*;

public class Dataset<T> extends ArrayList<T>{

	private static final long serialVersionUID = 1L;
	private Featurizer featurizer;
	
	public Dataset(Featurizer featurizer) {
		this.featurizer = featurizer;
	}
}
