package com.accenture.examples.rest;

import org.platformlambda.core.exception.AppException;
import org.platformlambda.core.models.EventEnvelope;
import org.platformlambda.core.models.Kv;
import org.platformlambda.core.system.PostOffice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Path("/add")
public class DemoMathRest {

    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
    public Map<String, Object> adding2numbers(@QueryParam("a") String a, @QueryParam("b") String b) throws IOException, TimeoutException, AppException {

        PostOffice po = PostOffice.getInstance();
        EventEnvelope response = po.request("math.addition", 5000, new Kv("a", a), new Kv("b", b));
        if (response.getBody() instanceof Integer) {
            Map<String, Object> result = new HashMap<>();
            result.put("result", response.getBody());
            result.put("time", new Date());
            result.put("execution_time", response.getExecutionTime());
            result.put("round_trip", response.getRoundTrip());
            return result;
        } else {
            throw new IllegalArgumentException("Sorry math function failed");
        }

    }
}
