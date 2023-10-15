package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface Storage {

    Meal save(Meal meal);
    void delete(int id);
    Meal get(int id);
    Collection<Meal> getAll();
}
