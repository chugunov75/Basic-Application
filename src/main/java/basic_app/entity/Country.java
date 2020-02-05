package basic_app.entity;

public class Country implements IEntity
{
  private String id;
  private String name;

  public Country()
  {
    this("", "");
  }

  public Country(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  @Override
  public String getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  @Override
  public void setAttributes(String[] attr) throws IllegalAccessException
  {
    if (attr.length < 2)
    {
      throw new IllegalAccessException();
    }
    else
    {
      this.id = attr[0];
      this.name = attr[1];
    }
  }

  @Override
  public IEntity copy()
  {
    return new Country(this.id, this.name);
  }
}
