package com.github.jtam2000.stockquotes;

import java.util.List;

public interface DataAccessObject<T> {

    void create(List<T> items);
    List<T> read();
    void update(List<T> items);
    void delete();
    String primaryKeyName();
}
