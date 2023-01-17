package facades;

import dtos.FestivalDto;
import dtos.GuestDto;
import dtos.ShowDto;
import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class FestivalFacade {

    private static EntityManagerFactory emf;
    private static FestivalFacade instance;

    private FestivalFacade() {
    }

    public ArrayList<FestivalDto> getAllFestivals(){

            EntityManager em = emf.createEntityManager();
            TypedQuery<Festival> query = em.createQuery("SELECT f FROM Festival f",Festival.class);
            List<Festival> rms = query.getResultList();
            ArrayList<FestivalDto> festivalDtos = new ArrayList<>();

            for (Festival festival: rms){
                festivalDtos.add(new FestivalDto(festival));
            }
            return festivalDtos;
    }

    public FestivalDto createFestival(FestivalDto festivalDto){
        Festival festival = festivalDto.createEntity();
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(festival);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new FestivalDto(festival);
    }

    public FestivalDto update(FestivalDto festivalDto){
        Festival festival = festivalDto.createEntity();
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(festival);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return new FestivalDto(festival);
    }

    //Finds all the shows a guest is attending
    public static FestivalFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FestivalFacade();
        }
        return instance;
    }
}
