package DAO;
import java.util.List;


public interface GenericDAO<T> {
    T findById(int id);
    List<T> findAll();
    boolean insert(T obj);
    boolean update(T obj);
    boolean delete(int id);
}