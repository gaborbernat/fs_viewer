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
import net.primeranks.fs_viewer.fs_data.FlightSnapshot;
import net.primeranks.fs_viewer.fs_server.Dao;

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
