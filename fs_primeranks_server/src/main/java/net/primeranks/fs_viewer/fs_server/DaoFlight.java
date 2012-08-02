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

import com.googlecode.objectify.Query;
import net.primeranks.fs_viewer.fs_data.Flight;

import java.util.ArrayList;
import java.util.List;

public class DaoFlight extends Dao {
    public Flight findUserById(Long id) {
        return ofy().get(Flight.class, id);
    }

    public Flight createFlight(Flight u) {
        ofy().put(u);
        return u;
    }

    public List<Flight> getAllFlights() {
        List<Flight> r = new ArrayList<Flight>();

        Query<Flight> q = ofy().query(Flight.class);
        Iterable<Flight> tbl = q.fetch();

        for (Flight p : tbl) {
            r.add(p);
        }
        return r;
    }

    public List<Flight> findFlightsForUser(Long userId) {

        List<Flight> r = new ArrayList<Flight>();
        Query<Flight> q = ofy().query(Flight.class);
        if (userId != null)
            q = q.filter("userId", userId.toString());
        Iterable<Flight> tbl = q.fetch();

        for (Flight p : tbl) {
            r.add(p);
        }
        return r;
    }
}
