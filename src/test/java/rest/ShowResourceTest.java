package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ShowDto;
import entities.*;
import facades.ShowFacade;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasEntry;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class ShowResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    private static ShowFacade facade;

    public static Show testShow1, testShow2;
    public static Guest testGuest;


    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ShowFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();

        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

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
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.persist(testGuest);
            em.persist(testShow1);
            em.persist(testShow2);

            Role userRole = new Role("guest");
            Role adminRole = new Role("admin");
            User user = new User("guest", "test");
            user.addRole(userRole);
            User admin = new User("admin", "t");
            admin.addRole(adminRole);
            User both = new User("admin_guest", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);


            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    

    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
               // .when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }
    @Test
    public void getAllShows() throws Exception {
        List<ShowDto> ShowDTO;

        ShowDTO = given()
                .contentType("application/json")
                .when()
                .get("/show")
                .then()
                .extract().body().jsonPath().getList("", ShowDto.class);


        ShowDto showDto1 = new ShowDto(testShow1);
        ShowDto showDto2 = new ShowDto(testShow2);
        assertThat(ShowDTO, containsInAnyOrder(showDto1, showDto2));

    }

    @Test
    public void testDeleteShow() {
        login("admin", "t");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
              //  .header("x-access-token", securityToken)
                .pathParam("id", testShow1.getId())
                .delete("/show/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(Math.toIntExact(testShow1.getId())));
    }

    @Test
    public void createTest() {
        Show show = new Show("Duckwrth Concert", 23, "23 august", "California, LA");
        ShowDto showDto = new ShowDto(show);
        String requestBody = GSON.toJson(showDto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/show")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo(show.getName()));
    }

    @Test
    public void updateTest() {
        testShow1.setName("Updated name");
        testShow1.setLocation("tomorrow");
        String requestBody = GSON.toJson(new ShowDto(testShow1));
        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("/show")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(Math.toIntExact(testShow1.getId())))
                .body("name", equalTo(testShow1.getName()))
                .body("location", equalTo(testShow1.getLocation()));
    }

}
