package com.github.jtam2000.stockquotes;

import java.util.List;

public interface DataAccessObject<T> {

    List<T> create();
    List<T> read();
    List<T> update();
    void delete();
}
