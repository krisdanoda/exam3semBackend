package facades;

import dtos.GuestDto;
import entities.Guest;
import entities.User;
import entities.UserDto;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;

public class GuestFacade {

    private static EntityManagerFactory emf;
    private static GuestFacade instance;

    private GuestFacade() {
    }

    public User createGuest(UserDto userDto) {
        User user = new User(userDto);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }finally {
            em.close();
        }

        return user;
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
        GuestFacade FACADE = GuestFacade.getFacade(EMF);


        UserDto Userdto = new UserDto("God", "Jesus");
        GuestDto guestDto = new GuestDto(0, "god", new ArrayList<>(), 11223344L, null, "single", "g@godmail.com", Userdto);
        Userdto.setGuestAccount(guestDto);



        FACADE.createGuest(Userdto);

    }

}
