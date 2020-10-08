package com.github.jtam2000.stockquotes;

import com.github.jtam2000.jpa.HasPrimaryKey;

import java.util.List;

public interface DataAccessObject<T extends HasPrimaryKey> {

    void create(List<? extends HasPrimaryKey> items);

    List<T>  read();
    List<T>  readByPrimaryKey(List<? extends HasPrimaryKey>  pks);

    void update(List<? extends HasPrimaryKey> items);

    void delete();
    void delete(List<? extends HasPrimaryKey>  items);

    void refresh(List<? extends HasPrimaryKey>  items);

    String primaryKeyName(HasPrimaryKey instance);
}
