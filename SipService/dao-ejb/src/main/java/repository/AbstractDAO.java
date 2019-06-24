package repository;

import java.util.ArrayList;

public interface AbstractDAO<K, T extends Object> {

    boolean addAndUpdate(T entry);

    boolean delete(T entry);

    ArrayList<T> findAll();

    T findById(K key);

}
