package model.data;

import java.util.Collection;

public interface DefaultDao<T> {

    T get(int id);

    void insert(T entity);

    void delete(T entity);

    Collection<T> getAll();
}
