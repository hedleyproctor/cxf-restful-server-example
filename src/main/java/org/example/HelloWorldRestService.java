package org.example;

import com.ice.fraud.Claim;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.jws.WebService;

@Path("/hello")
@WebService
public interface HelloWorldRestService
{
    @GET
    @Path("/greet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response greet();

    @POST
    @Path("/sayhello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello(String input);

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submit(Claim claim);
}
