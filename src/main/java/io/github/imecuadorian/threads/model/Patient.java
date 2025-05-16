package io.github.imecuadorian.threads.model;

import io.github.imecuadorian.library.*;

public class Patient {

    private final Generic<String, ?> data;

    public Patient(String dni) {
        this.data = new Generic<>(dni);
    }

    public String getDni() {
        return data.getT1();
    }
}
