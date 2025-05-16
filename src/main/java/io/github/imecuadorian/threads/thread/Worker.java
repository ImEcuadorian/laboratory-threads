package io.github.imecuadorian.threads.thread;

import io.github.imecuadorian.threads.model.*;

import java.util.*;
import java.util.concurrent.*;

public class Worker implements Runnable {
    private final String name;
    private final List<Sample> samples;

    public Worker(String name, List<Sample> samples) {
        this.name = name;
        this.samples = samples;
    }

    @Override
    public void run() {
        int outOfRange = 0;
        try {
            for (Sample s : samples) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2000));
                boolean ok = true;
                System.out.printf("[%s] Analizando muestra %s:\n", name, s.getId());

                if (s.getGlucose() < 70 || s.getGlucose() > 110) {
                    System.out.printf(" - Glucosa fuera de rango: %d\n", s.getGlucose());
                    ok = false;
                } else {
                    System.out.printf(" - Glucosa OK: %d\n", s.getGlucose());
                }

                if (s.getCholesterol() > 200) {
                    System.out.printf(" - Colesterol fuera de rango: %d\n", s.getCholesterol());
                    ok = false;
                } else {
                    System.out.printf(" - Colesterol OK: %d\n", s.getCholesterol());
                }

                if (s.getTriglycerides() > 150) {
                    System.out.printf(" - Triglicéridos fuera de rango: %d\n", s.getTriglycerides());
                    ok = false;
                } else {
                    System.out.printf(" - Triglicéridos OK: %d\n", s.getTriglycerides());
                }

                if (s.getHemoglobin() < 12 || s.getHemoglobin() > 18) {
                    System.out.printf(" - Hemoglobina fuera de rango: %.1f\n", s.getHemoglobin());
                    ok = false;
                } else {
                    System.out.printf(" - Hemoglobina OK: %.1f\n", s.getHemoglobin());
                }

                if (s.getUrea() < 10 || s.getUrea() > 50) {
                    System.out.printf(" - Urea fuera de rango: %d\n", s.getUrea());
                    ok = false;
                } else {
                    System.out.printf(" - Urea OK: %d\n", s.getUrea());
                }

                System.out.printf("[%s] Resultado de muestra %s: %s\n\n", name, s.getId(), ok ? "Aprobada" : "Fuera de rango");
                if (!ok) outOfRange++;
            }
            double percent = (outOfRange * 100.0) / samples.size();
            System.out.printf("[%s] Porcentaje de muestras fuera de rango: %.2f%%\n", name, percent);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
