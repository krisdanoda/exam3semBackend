package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.GuestDto;
import dtos.ShowDto;
import entities.UserDto;
import facades.FacadeExample;
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

@Path("guest")
public class GuestResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final GuestFacade Facade =  GuestFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();



    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("show/{id}")
    public Response getShows(@PathParam("id") int id ) throws Exception {
        List<ShowDto> showDto = Facade.getShows(id);
        return Response.ok().entity(GSON.toJson(showDto)).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createGuest(String content){
        UserDto userDto= GSON.fromJson(content, UserDto.class);
        userDto = Facade.createGuest(userDto);

        return Response.ok().entity(GSON.toJson(userDto)).build();
    }

}

