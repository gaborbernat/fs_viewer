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

package net.primeranks.fs_server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import net.primeranks.fs_data.Flight;
import net.primeranks.fs_data.FlightSnapshot;
import net.primeranks.fs_data.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Level;

public class Dao extends DAOBase {
    // Objectify is the simplest convenient interface to the Google App Engine datastore.
    // At startup register the DAO objects at Objectifies service.
    static {
        ObjectifyService.register(User.class);
        ObjectifyService.register(Flight.class);
        ObjectifyService.register(FlightSnapshot.class);
    }

    protected Injector injector;

    @Context
    protected UriInfo uriInfo;

    @Context
    protected Request request;

    private Injector getInjectorInstance() {
        if (injector == null) injector = Guice.createInjector(new GuiceModuleFsPrimeranksServer());
        return injector;
    }

    public DaoFlight Flight() {
        DaoFlight D = (DaoFlight)getInjectorInstance().getInstance(Key.get(Dao.class, GuiceModuleFsPrimeranksServer.FlightName));
        if (D == null)
            java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "Could not create Flight DAO.");
        return D;
    }

    protected DaoFlightSnapshot FlightSnapshot() {
       DaoFlightSnapshot D = (DaoFlightSnapshot) getInjectorInstance().getInstance(Key.get(Dao.class, GuiceModuleFsPrimeranksServer.FlightSnapshot));
        if (D == null)
            java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "Could not create FlightSnapshot DAO.");
        return D;
    }

    protected DaoUser User() {
        DaoUser D = (DaoUser) getInjectorInstance().getInstance(Key.get(Dao.class, GuiceModuleFsPrimeranksServer.UserName));
        if (D == null)
            java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "Could not create Flight User DAO.");
        return D;
    }
}