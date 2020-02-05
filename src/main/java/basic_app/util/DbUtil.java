package basic_app.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbUtil
{

  private static final Logger log = LogManager.getLogger(DbUtil.class);

  private static HikariDataSource dataSource;

  private static String driverName;
  private static String url;
  private static String username;
  private static String password;
  private static int maxPoolSize;

  private static void init()
  {
    setDbProperties();

    HikariConfig config = new HikariConfig();
    config.setPoolName("MySqlPool");
    config.setDriverClassName(driverName);
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.setConnectionTestQuery("SELECT 1");
    config.setMaxLifetime(60000); // 60 Sec
    config.setIdleTimeout(45000); // 45 Sec
    config.setMaximumPoolSize(maxPoolSize); // 50 Connections (including idle connections)
    dataSource = new HikariDataSource(config);

    if (dataSource != null)
    {
      log.info("The Hikari Data Source has been created successfully!", url);
    }

  }

  private static void setDbProperties()
  {
    Properties props = new Properties();
    String propsFilePath = DbUtil.class
        .getClassLoader()
        .getResource("database.properties")
        .getFile();

    try (InputStream in = Files.newInputStream(Paths.get(propsFilePath)))
    {
      props.load(in);
      driverName = props.getProperty("driver");
      url = props.getProperty("url");
      username = props.getProperty("username");
      password = props.getProperty("password");
      maxPoolSize = Integer.parseInt(props.getProperty("max_pool_size"));
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
