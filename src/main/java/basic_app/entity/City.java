package basic_app.entity;

import java.util.Objects;

public class City implements IEntity
{
  private String id;
  private String name;
  private String countryId;

  public City()
  {
    this("", "", "");
  }

  public City(String id, String name, String countryId)
  {
    this.id = id;
    this.name = name;
    this.countryId = countryId;
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

  public String getCountryId()
  {
    return countryId;
  }

  @Override
  public void setAttributes(String[] attr) throws IllegalAccessException
  {
    if (attr.length < 3)
    {
      throw new IllegalAccessException();
    }
    else
    {
      this.id = attr[0];
      this.name = attr[1];
      this.countryId = attr[2];
    }
  }

  @Override
  public IEntity copy()
  {
    return new City(this.id, this.name, this.countryId);
  }

  @Override
  public boolean equals(Object other)
  {
    if (other == this)
    {
      return true;
    }
    if (other == null)
    {
      return false;
    }
    if (this.getClass() != other.getClass())
    {
      return false;
    }
    City city = (City) other;

    return Objects.equals(id, city.id) &&
        Objects.equals(name, city.name) &&
        Objects.equals(countryId, city.countryId);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, name, countryId);
  }
}
