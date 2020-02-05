package basic_app.entity;

import java.util.Objects;

public class User implements IEntity
{
  private String id;
  private String name;
  private String dateOfBirth;
  private String cityId;

  public User()
  {
    this("", "", "", "");
  }

  public User(String id, String name, String dateOfBirth, String cityId)
  {
    this.id = id;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.cityId = cityId;
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

  public String getDateOfBirth()
  {
    return dateOfBirth;
  }

  public String getCityId()
  {
    return cityId;
  }

  @Override
  public void setAttributes(String[] attr) throws IllegalAccessException
  {
    if (attr.length < 4)
    {
      throw new IllegalAccessException();
    }
    else
    {
      this.id = attr[0];
      this.name = attr[1];
      this.dateOfBirth = attr[2];
      this.cityId = attr[3];
    }
  }

  @Override
  public IEntity copy()
  {
    return new User(this.id, this.name, this.dateOfBirth, this.cityId);
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
    User user = (User) other;

    return Objects.equals(id, user.id) &&
        Objects.equals(name, user.name) &&
        Objects.equals(dateOfBirth, user.dateOfBirth) &&
        Objects.equals(cityId, user.cityId);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, name, dateOfBirth, cityId);
  }
}
