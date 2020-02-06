package basic_app.util;

import basic_app.App;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbUtil
{

  private static final Logger log = LogManager.getLogger(DbUtil.class);

  private static HikariDataSource dataSource;

  private static Map<String, String> dbProps;

  private static final String[] dbPropNames =
      {"driver", "url", "username", "password", "max_pool_size", "max_life_time", "idle_timeout"};

  private static void init()
  {
    setDbProperties();

    HikariConfig config = new HikariConfig();
    config.setPoolName("MySqlPool");
    config.setDriverClassName(dbProps.get("driver"));
    config.setJdbcUrl(dbProps.get("url"));
    config.setUsername(dbProps.get("username"));
    config.setPassword(dbProps.get("password"));
    config.setConnectionTestQuery("SELECT 1");
    config.setMaxLifetime(Integer.parseInt(dbProps.get("max_life_time")));
    config.setIdleTimeout(Integer.parseInt(dbProps.get("idle_timeout")));
    config.setMaximumPoolSize(Integer.parseInt(dbProps.get("max_pool_size")));
    dataSource = new HikariDataSource(config);

    if (dataSource != null)
    {
      log.info("The Hikari Data Source has been created successfully!");
    }

  }

  public static Map<String, String> getProps(String[] keys, String propertiesFileName, Class currentClass) throws IOException
  {
    Map<String, String> propsMap = new HashMap<>();
    Properties props = new Properties();
    String propsFilePath = currentClass
        .getClassLoader()
        .getResource(propertiesFileName)
        .getFile();

    try (InputStream in = Files.newInputStream(Paths.get(propsFilePath)))
    {
      props.load(in);
      for (String key : keys)
      {
        propsMap.put(key, props.getProperty(key));
      }
    }
    catch (Exception ex)
    {
      throw ex;
    }
    return propsMap;
  }

  private static void setDbProperties()
  {
    try
    {
      dbProps = getProps(dbPropNames, "database.properties", DbUtil.class);
    }
    catch (Exception ex)
    {
      log.error("Error message : {}", ex.getMessage());
    }
  }

  public static Connection getConnection()
  {
    Connection connection = null;
    if (dataSource == null)
    {
      init();
    }

    try
    {
      connection = dataSource.getConnection();
      log.info("Connection to base_app_db has been created successfully!");
    }
    catch (SQLException ex)
    {
      log.error("Error message : {}", ex.getMessage());
    }

    return connection;
  }

  public static void cleanTable(String tableName)
  {
    String query = "DELETE FROM " + tableName;
    try (Connection conn = getConnection())
    {
      Statement st = conn.createStatement();
      st.executeUpdate(query);
    }
    catch (SQLException ex)
    {
      log.error("Error message : {}", ex.getMessage());
    }
  }

  public static void cleanTables()
  {
    List<String> tableNames = new ArrayList<>();
    try (Connection conn = getConnection())
    {
      DatabaseMetaData meta = conn.getMetaData();
      ResultSet rs = meta.getTables(null, null, null, new String[]{"TABLE"});
      while (rs.next())
      {
        tableNames.add(rs.getString(3));
      }
      rs.close();
      for (String tableName : tableNames)
      {
        String query = "DELETE FROM " + tableName;
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        st.close();
      }
    }
    catch (SQLException ex)
    {
      log.error("Error message : {}", ex.getMessage());
    }
  }
}
