package facades;

import dtos.FestivalDto;
import dtos.GuestDto;
import dtos.ShowDto;
import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class FestivalFacade {

    private static EntityManagerFactory emf;
    private static FestivalFacade instance;

    private FestivalFacade() {
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
