package basic_app.dao;

import java.util.List;

public interface DAO<T>
{
  boolean create(T entity);

  T read(String key);

  List<T> readAll();

  boolean update(T entity);

  boolean delete(T entity);
}
