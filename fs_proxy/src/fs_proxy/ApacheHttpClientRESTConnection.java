/*
 * ApacheHttpClientRESTConnection.java ->
 * Copyright (C) 2012-05-09 Gábor Bernát
 * Created at: [Budapest University of Technology and Economics - Deparment of Automation and Applied Informatics]
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package fs_proxy;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.primeranks.fs_data.Flight;
import net.primeranks.fs_data.FlightSnapshot;
import net.primeranks.fs_data.User;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.http.client.utils.URIUtils.createURI;

public class ApacheHttpClientRESTConnection implements Dao {
    static Logger log = Logger.getLogger(ApacheHttpClientRESTConnection.class.getName());

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
        userResource = c.resource(getUserURI());
        flightResource = c.resource(getFlightURI());
        flightSnapshotResource = c.resource(getFlightSnapshotURI());
    }

    private static URI getFlightSnapshotURI() {
        URI x = null;
        try {
            x = createURI(Config.PROTOCOL, Config.BASE_ADDRESS, Config.BASE_PORT,
                    Config.REST_PATH + Config.FLIGHTSNAPSHOT_PATH, null, null);
        } catch (URISyntaxException e) {
            log.log(Level.SEVERE, "Failure to form URI: " + e.getMessage());
        }
        return x;
    }

    private static URI getFlightURI() {
        URI x = null;
        try {
            x = createURI(Config.PROTOCOL, Config.BASE_ADDRESS, Config.BASE_PORT,
                    Config.REST_PATH + Config.FLIGHT_PATH, null, null);
        } catch (URISyntaxException e) {
            log.log(Level.SEVERE, "Failure to form URI: " + e.getMessage());
        }
        return x;
    }

    // Interface to access the application wide HTTP client
    public static Client getHttpClient() {
        return c;
    }

    private static URI getUserURI() {
        URI x = null;
        try {
            x = createURI(Config.PROTOCOL, Config.BASE_ADDRESS, Config.BASE_PORT,
                    Config.REST_PATH + Config.USER_PATH, null, null);
        } catch (URISyntaxException e) {
            log.log(Level.SEVERE, "Failure to form URI: " + e.getMessage());
        }
        return x;
    }

    @Override
    public Long getUserID(User u) {
        Long r = User.INVALID_ID;
        MultivaluedMap<String, String> p = new MultivaluedMapImpl();
        p.add("name", u.getName());
        p.add("domain", u.getDomain());
        List<User> resp = null;
        try {
            resp = userResource.queryParams(p).accept(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<User>>() {
                    });
        } catch (ClientHandlerException c) {
            log.log(Level.SEVERE, "Failed to connect to the URI. GET: " + userResource.getURI().toString());
        }

        if (resp != null && resp.size() != 0) {
            r = resp.get(0).getId();
        }
        return r;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long createUser(User u) {
        Long r = User.INVALID_ID;
        ClientResponse x = null;
        try {
            x = userResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).entity(u).put(ClientResponse.class);
        } catch (ClientHandlerException c) {
            log.log(Level.SEVERE, "Failed to connect to the URI. PUT: " + userResource.getURI().toString());
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
            resp = flightSnapshotResource.entity(f, MediaType.APPLICATION_JSON).put(ClientResponse.class);
        } catch (ClientHandlerException c) {
            log.log(Level.SEVERE, "Failed to connect to the URI. GET: " + userResource.getURI().toString() + " As count.");
        }
        if (resp.getStatus() != Response.Status.OK.getStatusCode()) {
            log.log(Level.SEVERE, "Could not add flightSnapshot: " + f.toString());
            return false;
        }
        return true;
    }

    @Override
    public int getSnapshotCount(Flight x) {
        String resp = null;
        try {
            resp = flightSnapshotResource.path("count").entity(x, MediaType.APPLICATION_JSON)
                    .accept(MediaType.TEXT_PLAIN).get(String.class);
        } catch (ClientHandlerException c) {
            log.log(Level.SEVERE, "Failed to connect to the URI. GET: " + userResource.getURI().toString() + " As count.");
        }

        return Integer.parseInt(resp);
    }

    @Override
    public Flight addFlight(Flight f) {

        String resp = null;
        try {
            resp = flightResource.entity(f, MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN)
                    .put(String.class);
        } catch (ClientHandlerException c) {
            log.log(Level.SEVERE, "Failed to connect to the URI. GET: " + userResource.getURI().toString());
        }

        f.setId(resp != null && resp.length() > 0 ? Long.parseLong(resp) : null);
        return f;
    }
}
