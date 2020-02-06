package basic_app.dao;

import basic_app.entity.FlatUser;
import basic_app.util.DbUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FlatUserDaoTest
{
  @Test
  public void createReadTest()
  {
    DbUtil.cleanTables();

    FlatUser flatUser = new FlatUser();
    flatUser.setId("1234");
    flatUser.setName("Vasya");
    flatUser.setDateOfBirth("2004-10-18");
    flatUser.setCountryName("Ukraine");
    flatUser.setCityName("Kharkov");

    FlatUserDao fDao = new FlatUserDao();

    boolean actualResult = fDao.create(flatUser);

    Assert.assertTrue(actualResult);

    FlatUser writtenUser = fDao.read("1234");

    Assert.assertEquals(flatUser, writtenUser);

    actualResult = fDao.create(flatUser);

    Assert.assertFalse(actualResult);

    DbUtil.cleanTables();
  }

  @Test
  public void deleteTest()
  {
    DbUtil.cleanTables();

    FlatUser flatUser = new FlatUser();
    flatUser.setId("1234");
    flatUser.setName("Vasya");
    flatUser.setDateOfBirth("2004-10-18");
    flatUser.setCountryName("Ukraine");
    flatUser.setCityName("Kharkov");

    FlatUserDao fDao = new FlatUserDao();

    boolean actualResult = fDao.create(flatUser);

    Assert.assertTrue(actualResult);

    actualResult = fDao.delete(flatUser);

    Assert.assertTrue(actualResult);

    flatUser = fDao.read("1234");

    Assert.assertNull(flatUser);

    DbUtil.cleanTables();
  }

  @Test
  public void updateReadAllTest()
  {
    DbUtil.cleanTables();

    FlatUser flatUser1 = new FlatUser();
    flatUser1.setId("1234");
    flatUser1.setName("Vasya");
    flatUser1.setDateOfBirth("2004-10-18");
    flatUser1.setCountryName("Ukraine");
    flatUser1.setCityName("Kharkov");

    FlatUser flatUser2 = new FlatUser();
    flatUser2.setId("1236");
    flatUser2.setName("Olya");
    flatUser2.setDateOfBirth("1999-05-12");
    flatUser2.setCountryName("Ukraine");
    flatUser2.setCityName("Kharkov");

    FlatUserDao fDao = new FlatUserDao();

    fDao.create(flatUser1);
    fDao.create(flatUser2);

    FlatUser flatUser3 = new FlatUser();
    flatUser3.setId("1234");
    flatUser3.setName("Olya");
    flatUser3.setDateOfBirth("1999-05-12");
    flatUser3.setCountryName("Russia");
    flatUser3.setCityName("Moscow");


    boolean res = fDao.update(flatUser3);

    Assert.assertTrue(res);

    FlatUser actualUser1 = fDao.read("1234");
    FlatUser actualUser2 = fDao.read("1236");

    Assert.assertEquals(flatUser2, actualUser2);

    Assert.assertEquals(flatUser3, actualUser1);

    Assert.assertNotEquals(flatUser1, actualUser1);

    Assert.assertNotEquals(flatUser2, actualUser1);

    List<FlatUser> userList = fDao.readAll();

    int expectedSize = 2;
    int actualSize = userList.size();

    Assert.assertEquals(expectedSize, actualSize);

    DbUtil.cleanTables();
  }
}
