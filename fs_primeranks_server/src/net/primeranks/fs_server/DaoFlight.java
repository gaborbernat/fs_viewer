/*
 * DaoFlight.java ->
 * Copyright (C) 2012-05-10 Gábor Bernát
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

package net.primeranks.fs_server;

import com.googlecode.objectify.Query;
import net.primeranks.fs_data.Flight;

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
            q = q.filter("userId", userId);
        Iterable<Flight> tbl = q.fetch();

        for (Flight p : tbl) {
            r.add(p);
        }
        return r;
    }
}
