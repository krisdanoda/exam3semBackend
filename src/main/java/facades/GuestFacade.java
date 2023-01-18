package facades;

import dtos.GuestDto;
import dtos.ShowDto;
import entities.Guest;
import entities.Show;
import entities.User;
import entities.UserDto;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GuestFacade {

    private static EntityManagerFactory emf;
    private static GuestFacade instance;

    private GuestFacade() {
    }

    public UserDto createGuest(UserDto userDto) {
        User user = new User(userDto);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }finally {
            em.close();
        }

        return new UserDto(user);
    }

    //Finds all the shows a guest is attending
    public  List<ShowDto> getShows(int guestID){
        List<ShowDto> resultList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        Query query = em.createNativeQuery("SELECT * from shows \n" +
                "Left outer join shows_guest\n" +
                "on shows.id = shows_guest.show_id\n" +
                "Left outer join guests\n" +
                "ON shows_guest.guest_id = guests.id\n" +
                "where guests.id = " + guestID, Show.class);
        List<Show> shows = query.getResultList();
        for (Show show : shows) {
            resultList.add(new ShowDto(show));
        }

        return resultList;
    }

    public static GuestFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuestFacade();
        }
        return instance;
    }



    public static void main(String[] args) {


         EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
        //GuestFacade FACADE = GuestFacade.getFacade(EMF);

        User admin = new User("admin", "password");
        EntityManager entityManager = EMF.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(admin);

        entityManager.getTransaction().commit();
        entityManager.close();

    }

}
