package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.FestivalDto;
import dtos.GuestDto;
import dtos.ShowDto;
import entities.Festival;
import entities.UserDto;
import facades.FacadeExample;
import facades.FestivalFacade;
import facades.GuestFacade;
import facades.ShowFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("festival")
public class FestivalResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final FestivalFacade festivalFacade =  FestivalFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();





    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createFestival(String content){
        FestivalDto festivalDto = GSON.fromJson(content, FestivalDto.class);
        festivalDto = festivalFacade.createFestival(festivalDto);

        return Response.ok().entity(GSON.toJson(festivalDto)).build();
    }
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateFestival(String content){
        FestivalDto festivalDto = GSON.fromJson(content, FestivalDto.class);
        festivalDto = festivalFacade.update(festivalDto);

        return Response.ok().entity(GSON.toJson(festivalDto)).build();
    }

}

