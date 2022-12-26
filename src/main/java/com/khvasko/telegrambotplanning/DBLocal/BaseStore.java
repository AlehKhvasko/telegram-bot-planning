package com.khvasko.telegrambotplanning.DBLocal;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Component
public class BaseStore implements DBStore {
    private Map<LocalDate, List<String>> localStore = new HashMap<>();

    @Override
    public void save(LocalDate key, String task) {
        if (localStore.containsKey(key)){
            ArrayList<String> alreadyExistTasks = new ArrayList<>(localStore.get(key));
            alreadyExistTasks.add(task);
            localStore.put(key, alreadyExistTasks);
        } else {
            localStore.put(key, asList(task));
        }
    }

    @Override
    public List<String> selectAll(LocalDate key) {
        return localStore.get(key);
    }
}
