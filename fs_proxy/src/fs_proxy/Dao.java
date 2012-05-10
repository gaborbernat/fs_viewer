/*
 * Dao.java ->
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

package fs_proxy;

import fs_data.Flight;
import fs_data.FlightSnapshot;
import fs_data.User;

public interface Dao {
    public Long getUserID(User u);

    public Long createUser(User u);

    public Flight addFlight(Flight f);

    public boolean addSnapshotToFlight(FlightSnapshot f);

    public int getSnapshotCount(Flight x);

}
