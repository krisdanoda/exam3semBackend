package facades;

import dtos.ShowDto;
import entities.Guest;
import entities.Show;
import utils.EMF_Creator;
import entities.RenameMe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class ShowFacadeTest {

    private static EntityManagerFactory emf;
    private static ShowFacade facade;

    public static Show testShow1, testShow2;
    public static Guest testGuest;

    public ShowFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ShowFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();


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
        testShow1 = new Show("TestShow1", 300, "tomorrow", "denmark");
        testShow2 = new Show("TestShow2", 5000, "DayAfterTomorrow", "japan");
        testGuest = new Guest("TestDummy", 11223344L, "test@tmail.com", "null", null);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Show.deleteAllRows").executeUpdate();
            em.createNamedQuery("Guest.deleteAllRows").executeUpdate();
            em.persist(testGuest);
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

    @Test
    void getAllShows() {
        assertEquals(2, facade.getShows().size());
    }

    @Test
    void updateShow() {


        testShow2.setName("differentName");
        testShow2.setDuration(testShow1.getDuration() + 50000);
        testShow2.setLocation("not in denmark");

        ShowDto actualDTO = facade.updateShow(new ShowDto(testShow2));

        assertEquals(testShow2, actualDTO.creatEntity());


    }


    @Test
    void updateShowWithGuest() {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            testShow2.getGuestList().add(testGuest);
            testGuest.getShows().add(testShow2);
            em.merge(testShow2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        testShow2.setName("differentName");
        testShow2.setDuration(testShow1.getDuration() + 50000);
        testShow2.setLocation("not in denmark");

        ShowDto actualDTO = facade.updateShow(new ShowDto(testShow2));

        assertEquals(testShow2, actualDTO.creatEntity());
        assert(testShow2.getGuestList().contains(testGuest));

    }

    @Test
    void deleteShow() throws Exception {
        int numberOfRows = facade.getShows().size();

        ShowDto showDto = facade.deleteShowDTO(Math.toIntExact(testShow2.getId()));
        Show actual = showDto.creatEntity();

        assertEquals(testShow2, actual);

        assertEquals(numberOfRows, facade.getShows().size() + 1);


    }

    @Test
    void attendShow(){
        facade.attendShow(Math.toIntExact(testShow1.getId()), Math.toIntExact(testGuest.getId()));
        EntityManager em = emf.createEntityManager();

        TypedQuery<Show> queryListShow = (TypedQuery<Show>) em.createNativeQuery("SELECT * FROM shows where id = ?", Show.class);
        queryListShow.setParameter(1, testShow1.getId());
        Show show = queryListShow.getSingleResult();
        System.out.println(show);
        assert (show.getGuestList().contains(testGuest));

    }


}
