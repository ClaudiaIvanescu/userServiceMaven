package com.itfactory;

import com.itfactory.project.user_service.dto.User;
import com.itfactory.project.user_service.service.DatabaseInitialService;
import com.itfactory.project.user_service.service.DbManager;
import com.itfactory.project.user_service.service.UserDao;
import org.junit.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    private static final String TEST_DB_FILE = "project_test.db";
    private static final List<User> sampleUser = Arrays.asList(new User(-1,"Marian","Dumitras","marian_dumitras@yahoo.com",20),
            new User(-1,"Ciprian","Andrei","andrei.ciprian@yahoo.com",27),
            new User(-1,"Maria","Popescu","maria.posecu@yahoo.com",19));

    private final UserDao user = new UserDao();

    @BeforeClass
    public static void initDbBeforeAnyTest(){
        DbManager.setDbFile(TEST_DB_FILE);
        DatabaseInitialService.createMissingTable();
    }
    @AfterClass
    public static void deleteDbAfterAllTest(){
        new File(TEST_DB_FILE).delete();
    }
    @Before
    public void insertRowsBeforeTest(){
        assertTrue(user.getAllUsers().isEmpty());
        for(User item : sampleUser){
            user.insert(item);
        }
    }
    @After
    public void deleteRowsAfterTest(){
        user.getAllUsers().forEach((dto-> user.delete(dto.getId())));
        assertTrue(user.getAllUsers().isEmpty());
    }
    @Test
    public void getAll(){
        checkOnlyTheSampleItemArePresentInDb(3);
    }

    @Test
    public void get(){
        User user1 = user.getAllUsers().get(0);
        assertEqualsItemExpectId(sampleUser.get(0),user1);
    }

    @Test
    public void getForInvalidId(){
        assertFalse(user.getById(-99).isPresent());
    }

    @Test
    public void insert(){
        assertEquals(3,user.getAllUsers().size());
        User newUser = new User(-1,"Petru","Mane","mane_petru@yahoo.com",33);
        user.insert(newUser);
        checkOnlyTheSampleItemArePresentInDb(4);
    }

    @Test
    public void updateForInvalidId(){
        user.update(new User(-98,"test","update","test_update@yahoo.com",33));
        checkOnlyTheSampleItemArePresentInDb(3);
    }

    @Test
    public void delete(){
        List<User> itemsFromDb = user.getAllUsers();
        user.delete(itemsFromDb.get(0).getId());
        List<User> itemsFromDbAfterDelete = user.getAllUsers();
        assertEquals(2,itemsFromDbAfterDelete.size());
        assertEqualsItemExpectId(itemsFromDb.get(1),itemsFromDbAfterDelete.get(0));
        assertEqualsItemExpectId(itemsFromDb.get(2),itemsFromDbAfterDelete.get(1));
    }

    @Test
    public void deleteFromInvalidId(){
        checkOnlyTheSampleItemArePresentInDb(3);
        user.delete(-55);
        checkOnlyTheSampleItemArePresentInDb(3);
    }

    private void assertEqualsItemExpectId(User user1,User user2){
        assertTrue("Items should be equal (exceptid):"+user1+","+user2,
                user1.getName().equals(user2.getName())&&
                        user1.getSurname().equals(user2.getSurname())&&
                        user1.getEmail().equals(user2.getEmail())&&
                        user1.getAge() == user2.getAge());
    }
    private void checkOnlyTheSampleItemArePresentInDb(int expected){
        List<User> itemsFromDb = user.getAllUsers();
        assertEquals(expected,itemsFromDb.size());
        assertEqualsItemExpectId(sampleUser.get(0),itemsFromDb.get(0));
        assertEqualsItemExpectId(sampleUser.get(1),itemsFromDb.get(1));
        assertEqualsItemExpectId(sampleUser.get(2),itemsFromDb.get(2));
    }
}
