package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ShowDto;
import facades.FacadeExample;
import facades.ShowFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("show")
public class ShowResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ShowFacade Facade =  ShowFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response shows() {
        ArrayList<ShowDto> showDtos = Facade.getShows();
        return Response.ok().entity(GSON.toJson(showDtos)).build();

    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content){
        ShowDto showDto = GSON.fromJson(content, ShowDto.class);
        showDto = Facade.createShow(showDto);
        return Response.ok().entity(GSON.toJson(showDto)).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(String content){
        ShowDto showDto = GSON.fromJson(content, ShowDto.class);
        showDto = Facade.createShow(showDto);
        return Response.ok().entity(GSON.toJson(showDto)).build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response delete(@PathParam("id") int id ) throws Exception {
        ShowDto showDto = Facade.deleteShowDTO(id);
        return Response.ok().entity(GSON.toJson(showDto)).build();
    }

}
