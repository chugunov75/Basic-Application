package basic_app;

import basic_app.entity.City;
import basic_app.entity.Country;
import basic_app.entity.FlatUser;
import basic_app.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void readEntitiesTest()
    {
        int expectedUserCount=3;
        User expectedFirstUser=new User("1234","Vasya","2004-10-18","23");
        User expectedLastUser=new User("1236","Olya","1999-05-12","23");

        File fUsers=new File("files/users.csv");
        List<User> users=App.readEntities(new User(), fUsers);

        Assert.assertEquals(expectedUserCount, users.size());
        Assert.assertEquals(expectedFirstUser, users.get(0));
        Assert.assertEquals(expectedLastUser, users.get(2));
    }
    @Test
    public void getEntityMapTest()
    {
        File fCities=new File("files/cities.csv");
        List<City> cities=App.readEntities(new City(),fCities);

        int expectedCount=3;
        String key="4";
        City expectedCity=new City("4","Moscow","2");

        Map<String, City> map=App.getEntityMap(cities);

        Assert.assertEquals(expectedCount, map.size());

        City actual=map.get(key);

        Assert.assertEquals(expectedCity, actual);
    }
    @Test
    public void createFlatUserTest()
    {
        File fUsers=new File("files/users.csv");
        File fCities=new File("files/cities.csv");
        File fCountries=new File("files/countries.csv");

        List<User> users=App.readEntities(new User(), fUsers);

        Map<String, Country> countryMap=App.getEntityMap(App.readEntities(new Country(), fCountries));
        Map<String, City> cityMap=App.getEntityMap(App.readEntities(new City(), fCities));

        FlatUser expected=new FlatUser();
        expected.setId("1234");
        expected.setName("Vasya");
        expected.setDateOfBirth("2004-10-18");
        expected.setCountryName("Ukraine");
        expected.setCityName("Kharkov");

        FlatUser actual=App.createFlatUser(users.get(0), cityMap, countryMap);

        Assert.assertEquals(expected, actual);
    }
}
