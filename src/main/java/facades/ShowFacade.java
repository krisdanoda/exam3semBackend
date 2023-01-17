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
            System.out.println(show);
        }

        return new ShowDto(show);

    }

    public ShowDto updateShow(ShowDto showDto){
        Show show = showDto.creatEntity();
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
        System.out.println("delete " + id);
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



    public static void main(String[] args) throws Exception {


        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
        ShowFacade showFacade =ShowFacade.getFacade(EMF);


        showFacade.deleteShowDTO(1);

    }

}

