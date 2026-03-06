package com.airtribe.meditrack.interfaces;

import java.util.Collection;

public interface Searchable<T> {
    T searchById(int id);
    Collection<T> searchByName(String name);
    Collection<T> searchByAge(int age);
}
