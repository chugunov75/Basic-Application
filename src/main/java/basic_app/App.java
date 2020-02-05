package basic_app;

import basic_app.dao.FlatUserDao;
import basic_app.entity.*;
import basic_app.util.DbUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 *
 */

public class App
{
  private static final Logger log = LogManager.getLogger(App.class);

  public static void main(String[] args)
  {
    DbUtil.cleanTables();

    String str = "";
    File fUsers = null;
    File fCities = null;
    File fCountries = null;

    Scanner in = new Scanner(System.in);

    exit:
    while (!str.equals("q"))
    {
      do
      {
        System.out.println("Укажите путь к файлу данных о пользователях (пример - files/users.csv; выход - нажмите q)");
        str = in.nextLine();
        if (str.equals("q"))
        {
          System.out.println("By!");
          break exit;
        }
        else if (!str.isEmpty())
        {
          fUsers = new File(str);
        }

        if (fUsers == null || !fUsers.exists())
        {
          System.out.println("Путь указан неверно");
        }
        else
        {
          break;
        }
      }
      while (true);

      do
      {
        System.out.println("Укажите путь к файлу данных о городах (пример - files/cities.csv; выход - нажмите q)");
        str = in.nextLine();
        if (str.equals("q"))
        {
          System.out.println("By!");
          break exit;
        }
        else if (!str.isEmpty())
        {
          fCities = new File(str);
        }

        if (fCities == null || !fCities.exists())
        {
          System.out.println("Путь указан неверно");
        }
        else
        {
          break;
        }
      }
      while (true);

      do
      {
        System.out.println("Укажите путь к файлу данных о странах (пример - files/countries.csv; выход - нажмите q)");
        str = in.nextLine();
        if (str.equals("q"))
        {
          System.out.println("By!");
          break exit;
        }
        else if (!str.isEmpty())
        {
          fCountries = new File(str);
        }

        if (fCountries == null || !fCountries.exists())
        {
          System.out.println("Путь указан неверно");
        }
        else
        {
          break;
        }
      }
      while (true);

      str = "q";

      boolean result = writeFlatUsers(fUsers, fCities, fCountries);

      if (result)
      {
        System.out.println("Зделано!");
      }
      else
      {
        System.out.println("Что-то пошло не так...");
      }

    }
    in.close();
  }


  public static <T extends IEntity> List<T> readEntities(T entity, File file)
  {
    List<T> list = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(file)))
    {
      String row = null;
      while ((row = reader.readLine()) != null)
      {
        String[] attr = row.split(",");

        if (attr[0].toLowerCase().indexOf("id") == -1)
        {
          for (int i = 0; i < attr.length; i++)
          {
            attr[i] = attr[i].trim();
          }
          T newEntity = (T) entity.copy();
          newEntity.setAttributes(attr);
          list.add(newEntity);
        }
      }
    }
    catch (IOException | IllegalAccessException ex)
    {
      log.error("Error message: {}", ex.getMessage());
    }
    return list;
  }

  public static <T extends IEntity> Map<String, T> getEntityMap(List<T> list)
  {
    Map<String, T> entytyMap = new HashMap<>();
    for (T item : list)
    {
      entytyMap.put(item.getId(), item);
    }
    return entytyMap;
  }

  public static FlatUser createFlatUser(User u, Map<String, City> cityMap, Map<String, Country> countryMap)
  {
    FlatUser flatUser = new FlatUser();
    flatUser.setId(u.getId());
    flatUser.setName(u.getName());
    flatUser.setDateOfBirth(u.getDateOfBirth());


    City flatUserCity = cityMap.get(u.getCityId());
    Country flatUserCountry = null;
    if (flatUserCity != null)
    {
      flatUser.setCityName(flatUserCity.getName());
      flatUserCountry = countryMap.get(flatUserCity.getCountryId());
    }

    if (flatUserCountry != null)
    {
      flatUser.setCountryName(flatUserCountry.getName());
    }

    return flatUser;
  }

  public static boolean writeFlatUsers(File fUsers, File fCities, File fCountries)
  {
    boolean result = false;

    List<User> users = readEntities(new User(), fUsers);

    Map<String, Country> countryMap = getEntityMap(readEntities(new Country(), fCountries));
    Map<String, City> cityMap = getEntityMap(readEntities(new City(), fCities));

    List<FlatUser> flatUserList = new ArrayList<>();

    for (User u : users)
    {
      FlatUser flatUser = createFlatUser(u, cityMap, countryMap);

      flatUserList.add(flatUser);
    }

    FlatUserDao fDao = new FlatUserDao();

    for (FlatUser item : flatUserList)
    {
      result = fDao.create(item);
    }

    return result;
  }
}


