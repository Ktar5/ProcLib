package com.ktar5.proclib;

import java.util.HashMap;

public class ProceduralDataHandler {

    private static ProceduralDataHandler instance;
    private final HashMap<String, ProceduralData> dataStore;

    private ProceduralDataHandler() {
        dataStore = new HashMap<>();
        instance = this;
    }

    public static ProceduralDataHandler getInstance() {
        if (instance == null) {
            instance = new ProceduralDataHandler();
        }
        return instance;
    }

    public ProceduralData get(String name) {
        if (dataStore.containsKey(name.toLowerCase())) {
            return dataStore.get(name.toLowerCase());
        }
        throw new RuntimeException("Attempting to access a procedural data that doesn't exist: '" + name.toLowerCase() + "'");
    }

    public void store(ProceduralData proc, String name) {
        store(proc, name, true);
    }

    public void store(ProceduralData proc, String name, boolean override) {
        if (!override && dataStore.containsKey(name.toLowerCase())) {
            throw new RuntimeException("Attempting to store duplicate data: '" + name.toLowerCase() + "'");
        }
        dataStore.put(name.toLowerCase(), proc);
    }

    public void delete(String name) {
        if (dataStore.containsKey(name.toLowerCase())) {
            dataStore.remove(name.toLowerCase());
        }
        throw new RuntimeException("Attempting to delete data that doesn't exist: '" + name.toLowerCase() + "'");
    }

    public void deleteChildren(String prefix, String... children) {
        for (String child : children) {
            delete(prefix + child);
        }
    }

}
