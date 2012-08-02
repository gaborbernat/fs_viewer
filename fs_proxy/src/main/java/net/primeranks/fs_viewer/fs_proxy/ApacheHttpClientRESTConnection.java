/**
 * Copyright (C) 2012 Gabor Bernat <bernat@primeranks.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.primeranks.fs_viewer.fs_proxy;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.primeranks.fs_viewer.fs_data.Flight;
import net.primeranks.fs_viewer.fs_data.FlightSnapshot;
import net.primeranks.fs_viewer.fs_data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class ApacheHttpClientRESTConnection implements Dao {
    static Logger log = LoggerFactory.getLogger(ApacheHttpClientRESTConnection.class.getName());

    // Maintain a single HTTP client for the entire application
    private final static Client c;
    private final static WebResource userResource;
    private final static WebResource flightResource;
    private final static WebResource flightSnapshotResource;

    // Configure the HTTP client to handle multiple threaded access
    static { // A static initializer will create the object
        ClientConfig cc = new DefaultClientConfig();
        cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
        cc.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 5000);
        cc.getProperties().put(ClientConfig.PROPERTY_THREADPOOL_SIZE, 5);
        cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);

        c = Client.create(cc);
        flightResource = getHttpClient().resource(getFlightURI());
        flightSnapshotResource = getHttpClient().resource(getFlightSnapshotURI());
        userResource = getHttpClient().resource(getUserURI());
    }

    private static URI getURI(String f) {
        try {
            URL a = new URL(Config.PROTOCOL, Config.BASE_ADDRESS, Config.BASE_PORT, Config.REST_PATH + f);
            return a.toURI();
        } catch (MalformedURLException e) {
            log.error( "Failure to form URL: " + e.getMessage());
        } catch (URISyntaxException e) {
            log.error("Failure to form URI: " + e.getMessage());
        }
        return null;
    }

    private static URI getFlightSnapshotURI() {
        return getURI(Config.FLIGHTSNAPSHOT_PATH);
    }
    private static URI getFlightURI() {
        return getURI(Config.FLIGHT_PATH);
    }

    // Interface to access the application wide HTTP client
    public static Client getHttpClient() {
        return c;
    }

    private static URI getUserURI() {
       return getURI(Config.USER_PATH);
    }

    @Override
    public Long getUserID(User u) {
        Long r = User.INVALID_ID;
        MultivaluedMap<String, String> p = new MultivaluedMapImpl();
        p.add("name", u.getName());
        p.add("domain", u.getDomain());
        List<User> resp = null;
        try {
            resp = userResource.queryParams(p).accept(Config.TRANSPORT_FORMAT)
                    .get(new GenericType<List<User>>() {
                    });
        } catch (ClientHandlerException c) {
            log.error( "Failed to connect to the URI. GET: " + userResource.getURI().toString() + " " + c.getMessage());
        }

        if (resp != null && resp.size() != 0) {
            r = resp.get(0).getId();
        }
        return r;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long createUser(User u) {
        ClientResponse x = null;
        try {
            x = userResource.accept(MediaType.TEXT_PLAIN).type(Config.TRANSPORT_FORMAT).entity(u).put(ClientResponse.class);
        } catch (ClientHandlerException c) {
            log.error( "Failed to connect to the URI. PUT: " + userResource.getURI().toString() + " " + c.getMessage());
        }

        if (x != null && x.getClientResponseStatus() == ClientResponse.Status.OK) {
            u.setId(Long.parseLong(x.getEntity(String.class)));
        } else
            return User.INVALID_ID;

        return u.getId();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean addSnapshotToFlight(FlightSnapshot f) {
        ClientResponse resp = null;
        try {
            resp = flightSnapshotResource.entity(f, Config.TRANSPORT_FORMAT).put(ClientResponse.class);
        } catch (ClientHandlerException c) {
            log.error( "Failed to connect to the URI. GET: " + userResource.getURI().toString()
                    + " " + c.getMessage());
        }
        if (Response.Status.fromStatusCode(resp.getStatus()).getFamily() != Response.Status.Family.SUCCESSFUL) {
            log.error( "Could not add flightSnapshot: " + f.toString());
            return false;
        }
        return true;
    }

    @Override
    public int getSnapshotCount(Flight x) {
        String resp = null;
        try {
            resp = flightSnapshotResource.path("count").entity(x, Config.TRANSPORT_FORMAT)
                    .accept(MediaType.TEXT_PLAIN).get(String.class);
        } catch (ClientHandlerException c) {
            log.error("Failed to connect to the URI. GET: " + userResource.getURI().toString() + " " + c.getMessage());
        }

        return Integer.parseInt(resp);
    }

    @Override
    public Flight addFlight(Flight f) {

        String resp = null;
        try {
            resp = flightResource.entity(f, Config.TRANSPORT_FORMAT).accept(MediaType.TEXT_PLAIN)
                    .put(String.class);
        } catch (ClientHandlerException c) {
            log.error( "Failed to connect to the URI. GET: " + userResource.getURI().toString() + " " + c.getMessage());
        }

        f.setId(resp != null && resp.length() > 0 ? Long.parseLong(resp) : null);
        return f;
    }
}
