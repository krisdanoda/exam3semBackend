package facades;

import dtos.ShowDto;
import entities.Show;
import utils.EMF_Creator;
import entities.RenameMe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class ShowFacadeTest {

    private static EntityManagerFactory emf;
    private static ShowFacade facade;

    public static Show testShow1, testShow2;

    public ShowFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ShowFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();

         testShow1 = new Show("TestShow1", 300, "tomorrow", "denmark");
         testShow2 = new Show("TestShow2", 5000, "DayAfterTomorrow", "japan");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Show.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Show.deleteAllRows").executeUpdate();
            em.persist(testShow1);
            em.persist(testShow2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method
    @Test
    public void createShow() throws Exception {

        Show showExpected = new Show("Test show", 2001, "Today at midnight", "not here");
        ShowDto showDto = facade.createShow(new ShowDto(showExpected));
        Show acutal = showDto.creatEntity();

        assertEquals(showExpected, acutal);
        assert (acutal.getId() != 0);

    }

    @Test void getAllShows(){
        assertEquals(2, facade.getShows().size());
    }

    @Test void updateShow(){

        testShow1.setName("differentName");
        testShow1.setDuration(testShow1.getDuration() + 50000);
        testShow1.setLocation("not in denmark");

        ShowDto actualDTO =facade.updateShow(new ShowDto(testShow1));

        assertEquals(testShow1, actualDTO.creatEntity());

    }


}
