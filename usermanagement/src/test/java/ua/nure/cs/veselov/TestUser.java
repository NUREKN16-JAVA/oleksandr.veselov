package ua.nure.cs.veselov;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TestUser {
    
    // Тест, проверяющий метод User.GetFullName
    
    private static final String USER_FIRST_NAME = "Alexander";
    private static final String USER_LAST_NAME = "Veselov";
    private static final String ETALON_USER_FULL_NAME = "Veselov, Alexander";
    
    // Тесты, проверяющие метод User.GetAge актуальны для даты 14 ноября 2018 года
    
    // Тест #1
    // Заданный день является днем рождения
    private static final int YEAR_OF_BIRTH_1 = 1998;
    private static final int MONTH_OF_BIRTH_1 = Calendar.NOVEMBER;
    private static final int DAY_OF_BIRTH_1 = 14;
    private static final int ETALON_AGE_1 = 20;
    
    // Тест #2
    // День рождения будет в данном месяце (но ещё не наступил)
    // Данный тест демонстрирует ошибочность предположения, что
    // Возраст = Текущий год - Год рождения
    private static final int YEAR_OF_BIRTH_2 = 1998;
    private static final int MONTH_OF_BIRTH_2 = Calendar.NOVEMBER;
    private static final int DAY_OF_BIRTH_2 = 20;
    private static final int ETALON_AGE_2 = 19;
    
    // Тест #3
    // День рождения был в данном месяце (уже прошел)
    private static final int YEAR_OF_BIRTH_3 = 1998;
    private static final int MONTH_OF_BIRTH_3 = Calendar.NOVEMBER;
    private static final int DAY_OF_BIRTH_3 = 2;
    private static final int ETALON_AGE_3 = 20;
    
    // Тест #4
    // День рождения будет больше, чем через месяц
    private static final int YEAR_OF_BIRTH_4 = 1998;
    private static final int MONTH_OF_BIRTH_4 = Calendar.DECEMBER;
    private static final int DAY_OF_BIRTH_4 = 16;
    private static final int ETALON_AGE_4 = 19;
    
    // Тест #5
    // После дня рождения прошло больше месяца
    private static final int YEAR_OF_BIRTH_5 = 1998;
    private static final int MONTH_OF_BIRTH_5 = Calendar.JULY;
    private static final int DAY_OF_BIRTH_5 = 10;
    private static final int ETALON_AGE_5 = 20;
    
    private User user;
    private Date dateOfBirth;
    
    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void testGetFullName() {
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        assertEquals(ETALON_USER_FULL_NAME, user.getFullName());
    }
    
    @Test
    public void testGetAge1() {
       Calendar calendar = Calendar.getInstance();
       calendar.set(YEAR_OF_BIRTH_1, MONTH_OF_BIRTH_1, DAY_OF_BIRTH_1);
       dateOfBirth = calendar.getTime();
       user.setDateOfBirth(dateOfBirth);
       assertEquals(ETALON_AGE_1, user.getAge());
    }
    
    @Test
    public void testGetAge2() {
       Calendar calendar = Calendar.getInstance();
       calendar.set(YEAR_OF_BIRTH_2, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_2);
       dateOfBirth = calendar.getTime();
       user.setDateOfBirth(dateOfBirth);
       assertEquals(ETALON_AGE_2, user.getAge());
    }
    
    @Test
    public void testGetAge3() {
       Calendar calendar = Calendar.getInstance();
       calendar.set(YEAR_OF_BIRTH_3, MONTH_OF_BIRTH_3, DAY_OF_BIRTH_3);
       dateOfBirth = calendar.getTime();
       user.setDateOfBirth(dateOfBirth);
       assertEquals(ETALON_AGE_3, user.getAge());
    }
    
    @Test
    public void testGetAge4() {
       Calendar calendar = Calendar.getInstance();
       calendar.set(YEAR_OF_BIRTH_4, MONTH_OF_BIRTH_4, DAY_OF_BIRTH_4);
       dateOfBirth = calendar.getTime();
       user.setDateOfBirth(dateOfBirth);
       assertEquals(ETALON_AGE_4, user.getAge());
    }
    
    @Test
    public void testGetAge5() {
       Calendar calendar = Calendar.getInstance();
       calendar.set(YEAR_OF_BIRTH_5, MONTH_OF_BIRTH_5, DAY_OF_BIRTH_5);
       dateOfBirth = calendar.getTime();
       user.setDateOfBirth(dateOfBirth);
       assertEquals(ETALON_AGE_5, user.getAge());
    }
}
