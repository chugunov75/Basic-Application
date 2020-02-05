package basic_app.entity;

import java.util.Objects;

public class FlatUser
{
  private String id;
  private String name;
  private String dateOfBirth;
  private String cityName;
  private String countryName;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDateOfBirth()
  {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth)
  {
    this.dateOfBirth = dateOfBirth;
  }

  public String getCityName()
  {
    return cityName;
  }

  public void setCityName(String cityName)
  {
    this.cityName = cityName;
  }

  public String getCountryName()
  {
    return countryName;
  }

  public void setCountryName(String countryName)
  {
    this.countryName = countryName;
  }

  @Override
  public String toString()
  {
    return "FlatUser{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", dateOfBirth='" + dateOfBirth + '\'' +
        ", cityName='" + cityName + '\'' +
        ", countryName='" + countryName + '\'' +
        '}';
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
    FlatUser flatUser = (FlatUser) other;

    return Objects.equals(id, flatUser.id) &&
        Objects.equals(name, flatUser.name) &&
        Objects.equals(dateOfBirth, flatUser.dateOfBirth) &&
        Objects.equals(cityName, flatUser.cityName) &&
        Objects.equals(countryName, flatUser.countryName);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, name, dateOfBirth, cityName, countryName);
  }
}
