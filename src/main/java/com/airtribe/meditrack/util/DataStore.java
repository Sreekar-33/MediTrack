package com.airtribe.meditrack.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataStore <T> {

    private Map<Integer, T> store = new HashMap<>();

    public void add(int id, T obj) {
        store.put(id, obj);
    }

    public T get(int id) {
        return store.get(id);
    }

    public Collection<T> getAll(){
        return store.values();
    }

    public void remove(int id) {
        store.remove(id);
    }
}
