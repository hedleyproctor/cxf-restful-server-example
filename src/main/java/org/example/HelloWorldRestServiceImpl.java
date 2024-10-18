package org.example;

import com.ice.fraud.Claim;
import com.ice.fraud.ClaimResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class HelloWorldRestServiceImpl implements HelloWorldRestService
{
    public Response greet() {

        return Response.status(Status.OK).
                entity("Hi There!!").
                build();
    }

    public Response sayHello(String input) {
        Hello hello = new Hello();
        hello.setHello("Hello");
        hello.setName("Default User");

        if(input != null)
            hello.setName(input);

        return Response.
                status(Status.OK).
                entity(hello).
                build();
    }

    @Override
    public Response submit(Claim claim) {
        if (claim.getId() == null) {
            throw new RuntimeException("Claim id is mandatory!");
        }

        ClaimResponse claimResponse = new ClaimResponse();
        claimResponse.setFraudScore(200L);
        claimResponse.setDetails("This claim looks dodgy");

        return Response
                .status(Status.OK)
                .entity(claimResponse)
                .build();
    }

}
