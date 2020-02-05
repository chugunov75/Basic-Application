package basic_app.entity;

public interface IEntity
{
  void setAttributes(String[] attr) throws IllegalAccessException;

  IEntity copy();

  String getId();
}
