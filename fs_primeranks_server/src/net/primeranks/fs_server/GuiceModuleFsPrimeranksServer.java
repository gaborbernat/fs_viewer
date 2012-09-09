/*
 * GuiceModuleFsPrimeranksServer.java ->
 * Copyright (C) 2012-05-08 Gábor Bernát
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

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class GuiceModuleFsPrimeranksServer extends AbstractModule {

    public static final Named UserName = Names.named("User");
    public static final Named FlightName = Names.named("Flight");
    public static final Named FlightSnapshot = Names.named("FlightSnapshot");

    public GuiceModuleFsPrimeranksServer() {
    }

    @Override
    public void configure() {
        bind(Dao.class).annotatedWith(UserName)
                .to(DaoUser.class).in(Singleton.class);
        bind(Dao.class).annotatedWith(FlightName)
                .to(DaoFlight.class).in(Singleton.class);
        bind(Dao.class).annotatedWith(FlightSnapshot)
                .to(DaoFlightSnapshot.class).in(Singleton.class);
    }
}