package basic_app.dao;

import basic_app.entity.FlatUser;
import basic_app.util.DbUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlatUserDao implements DAO<FlatUser>
{
  private static final Logger log = LogManager.getLogger(FlatUserDao.class);

  private static final String CREATE_QUERY = "INSERT INTO flat_users (id, name, date_of_birth, city_name, country_name)"
      + " values(?,?,?,?,?)";

  private static final String READ_QUERY = "SELECT id, name, date_of_birth, city_name, country_name FROM flat_users WHERE id=?";

  private static final String READ_ALL_QUERY = "SELECT id, name, date_of_birth, city_name, country_name FROM flat_users";

  private static final String DELETE_QUERY = "DELETE FROM flat_users WHERE id=?";

  private static final String UPDATE_QUERY = "UPDATE flat_users SET name=?, date_of_birth=?, city_name=?, country_name=? WHERE id=?";

  @Override
  public boolean create(FlatUser entity)
  {
    boolean result = false;
    try (Connection conn = DbUtil.getConnection())
    {
      PreparedStatement statement = conn.prepareStatement(CREATE_QUERY);

      statement.setString(1, entity.getId());
      statement.setString(2, entity.getName());
      statement.setString(3, entity.getDateOfBirth());
      statement.setString(4, entity.getCityName());
      statement.setString(5, entity.getCountryName());

      int rowsCount = statement.executeUpdate();
      if (rowsCount > 0)
      {
        result = true;
      }
    }
    catch (SQLException ex)
    {
      log.error("Error message: {}", ex.getMessage());
    }
    return result;
  }

  @Override
  public FlatUser read(String key)
  {
    FlatUser result = null;

    try (Connection conn = DbUtil.getConnection())
    {
      PreparedStatement statement = conn.prepareStatement(READ_QUERY);

      statement.setString(1, key);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next())
      {
        result = readUser(resultSet);
      }
    }
    catch (SQLException ex)
    {
      log.error("Error message: {}", ex.getMessage());
    }
    return result;
  }

  @Override
  public List<FlatUser> readAll()
  {
    List<FlatUser> result = new ArrayList<>();

    try (Connection conn = DbUtil.getConnection())
    {
      Statement statement = conn.createStatement();

      ResultSet resultSet = statement.executeQuery(READ_ALL_QUERY);

      while (resultSet.next())
      {
        FlatUser flatUser = readUser(resultSet);
        result.add(flatUser);
      }
    }
    catch (SQLException ex)
    {
      log.error("Error message: {}", ex.getMessage());
    }

    return result;
  }

  @Override
  public boolean update(FlatUser entity)
  {
    boolean result = false;

    try (Connection conn = DbUtil.getConnection())
    {
      PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);

      statement.setString(1, entity.getName());
      statement.setString(2, entity.getDateOfBirth());
      statement.setString(3, entity.getCityName());
      statement.setString(4, entity.getCountryName());
      statement.setString(5, entity.getId());

      int rowsCount = statement.executeUpdate();
      if (rowsCount > 0)
      {
        result = true;
      }
    }
    catch (SQLException ex)
    {
      log.error("Error message: {}", ex.getMessage());
    }
    return result;
  }

  @Override
  public boolean delete(FlatUser entity)
  {
    boolean result = false;
    String id = entity.getId();

    try (Connection conn = DbUtil.getConnection())
    {
      PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
      statement.setString(1, entity.getId());

      int rowsCount = statement.executeUpdate();
      if (rowsCount > 0)
      {
        result = true;
      }
    }
    catch (SQLException ex)
    {
      log.error("Error message: {}", ex.getMessage());
    }
    return result;
  }

  private FlatUser readUser(ResultSet resultSet) throws SQLException
  {
    FlatUser flatUser = new FlatUser();
    flatUser.setId(resultSet.getString("id"));
    flatUser.setName(resultSet.getString("name"));
    flatUser.setDateOfBirth(resultSet.getString("date_of_birth"));
    flatUser.setCityName(resultSet.getString("city_name"));
    flatUser.setCountryName(resultSet.getString("country_name"));

    return flatUser;
  }
}
