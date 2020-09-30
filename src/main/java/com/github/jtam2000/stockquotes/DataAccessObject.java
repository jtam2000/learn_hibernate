package com.github.jtam2000.stockquotes;

import java.util.List;

public interface DataAccessObject<T> {

    void create(List<T> items);

    List<T> read();
    List<T> readByPrimaryKey(List<T> pks);

    void update(List<T> items);

    void delete();
    void delete(List<T> item);
    String primaryKeyName();
}
