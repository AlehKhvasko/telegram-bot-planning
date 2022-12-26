package com.khvasko.telegrambotplanning.DBLocal;

import java.time.LocalDate;
import java.util.List;

public interface DBStore {
        void save(LocalDate date, String task);
        List<String> selectAll(LocalDate date);
}
