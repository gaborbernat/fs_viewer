/*
 * Dao.java ->
 * Copyright (C) 2012-05-01 Gábor Bernát
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

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import net.primeranks.fs_data.User;

public class Dao extends DAOBase {
    // Objectify is the simplest convenient interface to the Google App Engine datastore.
    // At startup register the DAO objects at Objectifies service.
    static {
        ObjectifyService.register(User.class);
//        ObjectifyService.register(FlightSnapshot.class);
//        ObjectifyService.register(Flight.class);
    }
}