package io.github.imecuadorian.threads.thread;

import io.github.imecuadorian.library.*;
import io.github.imecuadorian.threads.model.*;

import java.io.*;
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
            Files resultFile = new Files("resultados/" + name + "_resultados.txt");
            resultFile.createFile(FileType.FILE);

            for (Sample s : samples) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2000));
                boolean ok = true;
                StringBuilder result = new StringBuilder();
                result.append(String.format("[%s] Analizando muestra %s:\n", name, s.getId()));

                if (s.getGlucose() < 70 || s.getGlucose() > 110) {
                    result.append(String.format(" - Glucosa fuera de rango: %d\n", s.getGlucose()));
                    ok = false;
                } else {
                    result.append(String.format(" - Glucosa OK: %d\n", s.getGlucose()));
                }

                if (s.getCholesterol() > 200) {
                    result.append(String.format(" - Colesterol fuera de rango: %d\n", s.getCholesterol()));
                    ok = false;
                } else {
                    result.append(String.format(" - Colesterol OK: %d\n", s.getCholesterol()));
                }

                if (s.getTriglycerides() > 150) {
                    result.append(String.format(" - Triglicéridos fuera de rango: %d\n", s.getTriglycerides()));
                    ok = false;
                } else {
                    result.append(String.format(" - Triglicéridos OK: %d\n", s.getTriglycerides()));
                }

                if (s.getHemoglobin() < 12 || s.getHemoglobin() > 18) {
                    result.append(String.format(" - Hemoglobina fuera de rango: %.1f\n", s.getHemoglobin()));
                    ok = false;
                } else {
                    result.append(String.format(" - Hemoglobina OK: %.1f\n", s.getHemoglobin()));
                }

                if (s.getUrea() < 10 || s.getUrea() > 50) {
                    result.append(String.format(" - Urea fuera de rango: %d\n", s.getUrea()));
                    ok = false;
                } else {
                    result.append(String.format(" - Urea OK: %d\n", s.getUrea()));
                }

                result.append(String.format("[%s] Resultado de muestra %s: %s\n\n", name, s.getId(), ok ? "Aprobada" : "Fuera de rango"));

                resultFile.writeFile(result.toString(), true);

                System.out.print(result);

                if (!ok) outOfRange++;
            }
            double percent = (outOfRange * 100.0) / samples.size();
            String summary = String.format("[%s] Porcentaje de muestras fuera de rango: %.2f%%\n", name, percent);
            resultFile.writeFile(summary, true);
            System.out.print(summary);
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
        }
    }
}
