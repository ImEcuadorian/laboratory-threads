package io.github.imecuadorian.threads.model;

import io.github.imecuadorian.library.*;

public class Sample extends Patient {

    private final Generic<String, Double> data;
    private final Generic<Integer, Integer> results;

    public Sample(String id, String dni, int glucose, int cholesterol, int triglycerides, double hemoglobin, int urea) {
        super(dni);
        this.data = new Generic<>(id, hemoglobin);
        this.results = new Generic<>(glucose, cholesterol, triglycerides, urea);
    }

    public String getId() {
        return data.getT1();
    }

    public double getHemoglobin() {
        return data.getS1();
    }

    public int getGlucose() {
        return results.getT1();
    }
    public int getCholesterol() {
        return results.getT2();
    }
    public int getTriglycerides() {
        return results.getS1();
    }
    public int getUrea() {
        return results.getS2();
    }

    public String toCSV() {
        return getId() + ";" + getDni() + ";" + getGlucose() + ";" + getCholesterol() + ";" +
                getTriglycerides() + ";" + getHemoglobin() + ";" + getUrea();
    }
}
