package edu;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.DatabaseProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CruiseDBTest {
    @Test
    public void addCruiseTest(){
        LocalDate rightNow = LocalDate.now();
        Cruise newCruise = new Cruise("testCruise",rightNow,5,null,null);
        Cruise checkCruise;
        CruiseDatabase.addCruise(newCruise);
        Assertions.assertNotNull(CruiseDatabase.getCruise("testCruise"));
        checkCruise = CruiseDatabase.getCruise("testCruise");
        assertEquals(checkCruise.getName(),"testCruise");
    }

    @Test
    public void cruisePathTest(){
        LocalDate date = LocalDate.now();
        Country u = new Country("Kingston, Jamaica", date, date);

        List<Country>uList = new ArrayList<>();
        uList.add(u);
        uList.add(u);
        uList.add(u);
        Cruise newCruise = new Cruise("testCruise",date,5,uList,null);

        assertEquals(CruiseDatabase.getTravelPathForCruise("testCruise").size(),3);
    }

    @Test
    public void cruiseGetAllCruisesTest(){
        LocalDate rightNow = LocalDate.now();
        Cruise newCruise = new Cruise("testCruise",rightNow,5,null,null);
        Cruise checkCruise;
        CruiseDatabase.addCruise(newCruise);
        assertEquals(CruiseDatabase.getAllCruises().size(),4);

    }

    @Test
    public void getCruiseNamesTest(){
        LocalDate rightNow = LocalDate.now();
        Cruise newCruise = new Cruise("testCruise",rightNow,5,null,null);
        Cruise checkCruise;
        CruiseDatabase.addCruise(newCruise);
        assertEquals(CruiseDatabase.getAllCruiseNames().size(),4);
        assertEquals(CruiseDatabase.getAllCruiseNames().get(3),"testCruise");
    }



    @AfterEach
    public void tearDown() {
       String url = DatabaseProperties.url;
        try (Connection connection = DriverManager.getConnection(url)) {
            //clear the table
            String deleteQuery = "DELETE FROM Cruises WHERE id = 4";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                int deleted;
                deleted = statement.executeUpdate();

                if (deleted <= 0) {
                    System.out.println("Failed to delete data");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
