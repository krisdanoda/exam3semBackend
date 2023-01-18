package facades;

import dtos.GuestDto;
import dtos.ShowDto;
import entities.Guest;
import entities.Show;
import entities.User;
import entities.UserDto;
import utils.EMF_Creator;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class ShowFacade {

    private static EntityManagerFactory emf;
    private static ShowFacade instance;

    private ShowFacade() {
    }

    public ArrayList<ShowDto> getShows(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Show> query = em.createQuery("SELECT r FROM Show r",Show.class);
        List<Show> rms = query.getResultList();

        ArrayList<ShowDto> showDtos = new ArrayList<>();

        for (Show show: rms){
            showDtos.add(new ShowDto(show));
        }

        return showDtos;

    }

    public ShowDto createShow(ShowDto showDto) {
        Show show = showDto.creatEntity();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(show);
            em.getTransaction().commit();
        }finally {
            em.close();
        }

        return new ShowDto(show);

    }

    public ShowDto updateShow(ShowDto showDto){
        System.out.println(showDto);
        Show show = showDto.creatEntity();
        System.out.println("update show: " + show);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(show);
            em.getTransaction().commit();
        }finally {
            em.close();
        }

        return new ShowDto(show);
    }

    public ShowDto deleteShowDTO(int id) throws Exception {

        EntityManager em = emf.createEntityManager();
        Show show = em.find(Show.class, (long)id);
        try {
            em.getTransaction().begin();
            em.remove(show);
            em.getTransaction().commit();
        }finally {
            em.close();
        }

        return new ShowDto(show);
    }

    public static ShowFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ShowFacade();
        }
        return instance;
    }



    public void test(EntityManagerFactory EMF){
        ShowFacade showFacade =ShowFacade.getFacade(EMF);
        Show show = new Show("Best show", 3, "Today", "Here");
        Show show1 = new Show("Even better show", 1 , "yesterday", "over there");
        showFacade.createShow(new ShowDto(show));
        showFacade.createShow(new ShowDto(show1));
    }

    public void attendShow(int idShow, int idPerson){
        EntityManager em = emf.createEntityManager();

        Show show = em.find(Show.class, (long) idShow);
        Guest guest = em.find(Guest.class,(long) idPerson);

        guest.getShows().add(show);
        show.getGuestList().add(guest);

        em.getTransaction().begin();
        em.merge(guest);
        em.getTransaction().commit();
        em.close();

    }

    public static void main(String[] args) throws Exception {

        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

        GuestFacade guestFacade = GuestFacade.getFacade(EMF);
        ShowFacade showFacade = ShowFacade.getFacade(EMF);

        UserDto Userdto = new UserDto("Buddha", "fat4lyfe");
        GuestDto guestDto = new GuestDto(51, "buddha", new ArrayList<>(), 11223344L, null, "single", "g@godmail.com", Userdto);

        showFacade.attendShow(1,1);


       EntityManager em = EMF.createEntityManager();

       /*
       Guest budda = guestDto.createEntity();

        Show show = new Show("Best show", 3, "Today", "Here");
        show.setId(1L);
        budda.getShows().add(show);
        show.getGuestList().add(budda);

       em.getTransaction().begin();
       em.merge(budda);
        System.out.println(budda);
       em.getTransaction().commit();
       em.close();

*/


    }

}

