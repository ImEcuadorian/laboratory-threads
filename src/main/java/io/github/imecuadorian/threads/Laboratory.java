package io.github.imecuadorian.threads;

import io.github.imecuadorian.library.*;
import io.github.imecuadorian.threads.model.*;
import io.github.imecuadorian.threads.thread.*;

import java.io.*;
import java.util.*;

public class Laboratory {
    private static final int TOTAL_SAMPLES = 25;
    private static final int TECHNICIANS = 5;
    private static final String OUTPUT_FILE = "muestras.txt";

    public static void main(String[] args) {
        List<Sample> samples = new ArrayList<>();
        try {
            Files file = new Files(OUTPUT_FILE);
            file.createFile(FileType.FILE);
            file.writeFile("ID_Muestra;Cedula_Paciente;Glucosa;Colesterol;Trigliceridos;Hemoglobina;Urea", false);

            for (int i = 1; i <= TOTAL_SAMPLES; i++) {
                Sample sample = generateRandomSample(i);
                samples.add(sample);
                file.writeFile(sample.toCSV(), false);
            }

            for (int i = 0; i < TECHNICIANS; i++) {
                List<Sample> assigned = samples.subList(i * 5, (i + 1) * 5);
                Thread thread = new Thread(new Worker("Tecnico_" + (i + 1), assigned));
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Sample generateRandomSample(int index) {
        Random r = new Random();
        String id = String.format("M%03d", index);
        String dni = String.valueOf(1000000000 + r.nextInt(900000000));
        int glucose = 65 + r.nextInt(66);
        int cholesterol = 150 + r.nextInt(101);
        int triglycerides = 100 + r.nextInt(101);
        double hemoglobin = 11 + r.nextDouble() * 8;
        int urea = 5 + r.nextInt(56);

        return new Sample(id, dni, glucose, cholesterol, triglycerides, hemoglobin, urea);
    }
}

