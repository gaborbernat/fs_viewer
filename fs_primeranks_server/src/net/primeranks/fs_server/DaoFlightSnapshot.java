/*
 * DaoFlightSnapshot.java ->
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
import net.primeranks.fs_data.FlightSnapshot;

import java.util.List;

public class DaoFlightSnapshot extends Dao {
    public FlightSnapshot addFlightSnapshot(FlightSnapshot u) {
        if (u == null || u.getFlightId() == null) return null;
        ofy().put(u);
        return u;
    }

    public List<FlightSnapshot> getSnapshots(Long flightId, int from, int nr) {

        Query<FlightSnapshot> q = ofy().query(FlightSnapshot.class);
        if (flightId != null)
            q = q.filter("flightId", flightId);

        // Limit in chunks
        q = q.offset(from < 0 ? 0 : from).limit(nr < 0 ? 0 : nr);

        return q.list();
    }

    public int getSnapshotCount(Long flightId) {

        Query<FlightSnapshot> q = ofy().query(FlightSnapshot.class);
        if (flightId != null)
            q = q.filter("flightId", flightId);
        return q.count();
    }
}
