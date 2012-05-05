/*
 * SendData.java ->
 * Copyright (C) 2012-05-06 Gábor Bernát
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

import net.primeranks.fs_data.Flight;
import net.primeranks.fs_data.FlightSnapshot;
import net.primeranks.fs_data.User;

public interface SendData {
    public Long getUserID(User u);

    public Long createUser(User u);

    public Flight createFlight(User u, Flight f);

    public void addFlightSnapshot(Flight f, FlightSnapshot s);
}
