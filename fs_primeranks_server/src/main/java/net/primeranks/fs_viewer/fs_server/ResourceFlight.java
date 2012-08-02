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
package net.primeranks.fs_viewer.fs_server;

import com.google.inject.Key;
import com.google.inject.name.Names;
import net.primeranks.fs_viewer.fs_data.Flight;
import net.primeranks.fs_viewer.fs_data.FlightSnapshot;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import java.util.List;

@Path("flight/")
public class ResourceFlight extends Resource_RESTInjectorProvider {

    public void throwIfPreconditionFailedIfNull(Object i, String x, MediaType y) {
        if (i == null) throw new ExceptionPreconditionFailed(y, x);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Flight> getFlightsForUser(@QueryParam("userId") Long userId) {
        List<Flight> u = daoFlight().findFlightsForUser(userId);
        return u;
    }

    /*
       A Flight is sent as input.
       Return a Flight ID and a Flight Meta ID as FlightIds object.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
    public String createFlight(JAXBElement<Flight> user) {
        Flight u = user.getValue();
        u = daoFlight().createFlight(u);
        String i = (u == null) ? null : u.getId().toString();
        throwIfPreconditionFailedIfNull(i, "Null value found for the user", MediaType.TEXT_PLAIN_TYPE);
        return i;
    }

    /*
        Add a flight snapshot object.
     */
    @PUT
    @Path("snapshot/")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void addSnapshotToFlight(JAXBElement<FlightSnapshot> s) {
        FlightSnapshot x = daoFlightSnapshot().addFlightSnapshot(s.getValue());
        if (x == null)
            throw new WebApplicationException(500);
    }

    @GET
    @Path("snapshot/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<FlightSnapshot> getSnapShot(@QueryParam("from") int from, @QueryParam("nr") int nr,
                                            @QueryParam("flightId") Long flightId) {
        List<FlightSnapshot> x = daoFlightSnapshot().getSnapshots(flightId, from, nr);
        return x;
    }

    @GET
    @Path("snapshot/count/")
    @Produces({MediaType.TEXT_PLAIN})
    public String getSnapShot(@QueryParam("flightId") Long flightId) {
        return "" + daoFlightSnapshot().getSnapshotCount(flightId);
    }

    private DaoFlight daoFlight() {
        return (DaoFlight) getInjectorInstance().getInstance(Key.get(Dao.class, Names.named("objectify.dao.Flight")));
    }

    private DaoFlightSnapshot daoFlightSnapshot() {
        return (DaoFlightSnapshot) getInjectorInstance().getInstance(Key.get(Dao.class, Names.named("objectify.dao.FlightSnapshot")));
    }
}
