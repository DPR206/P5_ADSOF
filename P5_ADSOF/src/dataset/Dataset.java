package dataset;

import java.util.*;

import exceptions.NotExistingFeature;

public class Dataset<T> {

    private final Map<String, Feature<?>> features;
    protected final Featurizer<T> featurizer;
    protected List<T> objects = new ArrayList<>();

    public Dataset(Featurizer<T> featurizer) {
        this.featurizer = featurizer;
        this.features = new LinkedHashMap<>();
        for (String name : featurizer.getFeatureNames()) {
            features.put(name, new Feature<>(name));
        }
    }

    public Featurizer<T> getFeaturizer() {
        return featurizer;
    }

    @SuppressWarnings("unchecked")
    public <V extends Comparable<V>> Feature<V> getFeature(String name) throws NotExistingFeature {
        Feature<?> f = features.get(name);
        if (f == null)
            throw new NotExistingFeature(name);
        return (Feature<V>) f;
    }

    public void removeDuplicates() {
        Set<T> possiblyDuplicated = new HashSet<>(objects);

        for (String feat : featurizer.getFeatureNames()) {
            possiblyDuplicated = findDuplicatesForFeature(possiblyDuplicated, feat);
            if (possiblyDuplicated.isEmpty()) break;
        }

        if (!possiblyDuplicated.isEmpty()) {
            List<T> toRemove = getDefiniteDuplicates(possiblyDuplicated);

            List<Integer> indicesToRemove = new ArrayList<>();
            for (T obj : toRemove) {
                int idx = objects.indexOf(obj);
                if (idx != -1) indicesToRemove.add(idx);
            }
            indicesToRemove.sort(Collections.reverseOrder());

            for (String feat : featurizer.getFeatureNames()) {
                Feature<?> feature = this.features.get(feat);
                for (int idx : indicesToRemove) {
                    feature.remove(idx);
                }
            }
            objects.removeAll(toRemove);
        }
    }

    private <V> Set<T> findDuplicatesForFeature(Set<T> candidates, String feat) {
        Map<V, T> seenValueToObj = new HashMap<>();
        Set<T> confirmed = new HashSet<>();

        for (T obj : candidates) {
            V value = featurizer.getFeatureValue(obj, feat);
            if (!seenValueToObj.containsKey(value)) {
                seenValueToObj.put(value, obj);
            } else {
                confirmed.add(seenValueToObj.get(value));
                confirmed.add(obj);
            }
        }
        return confirmed;
    }

    private List<T> getDefiniteDuplicates(Set<T> possiblyDuplicated) {
        List<T> sorted = new ArrayList<>(possiblyDuplicated);
        sorted.sort(Comparator.comparingInt(obj -> objects.indexOf(obj)));

        Map<String, T> groupRepresentative = new HashMap<>();
        List<T> toRemove = new ArrayList<>();

        for (T obj : sorted) {
            StringBuilder keyBuilder = new StringBuilder();
            for (String feat : featurizer.getFeatureNames()) {
                keyBuilder.append(featurizer.getFeatureValue(obj, feat).toString());
                keyBuilder.append("|");
            }
            String compositeKey = keyBuilder.toString();

            if (!groupRepresentative.containsKey(compositeKey)) {
                groupRepresentative.put(compositeKey, obj);
            } else {
                toRemove.add(obj);
            }
        }
        return toRemove;
    }

    public List<String> getFeatureNames() {
        return featurizer.getFeatureNames();
    }

    public int size() {
        return objects.size();
    }

    public List<T> getObjects() {
        return objects;
    }

    @SuppressWarnings("unchecked")
    private <V extends Comparable<? super V>> void addToFeature(Feature<V> feature, Object value) {
        feature.add((V) value);
    }

    @SuppressWarnings("unchecked")
    public void addAll(T... items) {
        for (T item : items) {
            this.objects.add(item);
            for (String f : features.keySet()) {
                addToFeature(features.get(f), featurizer.getFeatureValue(item, f));
            }
        }
    }

    protected void removeFeature(String feature) {
        features.remove(feature);
    }

    public Map<String, Feature<?>> getFeatures() {
        return features;
    }

    public List<Feature<?>> getListFeatures() {
        return this.features.values().stream().toList();
    }

    @Override
    public String toString() {
        return features.toString();
    }
}