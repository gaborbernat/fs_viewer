/*
 * GuiceConfigFsPrimeranksServer.java ->
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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/*
This module will setup the Jersey-based JAX-RS framework for use with Guide injection.
This is used to bootstrap Guice when the servet context is initialized.
*/
public class GuiceConfigFsPrimeranksServer extends GuiceServletContextListener {
    Logger log = Logger.getLogger(GuiceConfigFsPrimeranksServer.class.getName());

    @Override
    protected Injector getInjector() {
        final Map<String, String> params = new HashMap<String, String>();
        // The following line will use JerseyMainApplication.java to define Jersey resources
        params.put("javax.ws.rs.Application", "net.primeranks.fs_server.JerseyMainApplication");

        return Guice.createInjector(
                new GuiceModuleFsPrimeranksServer(),
                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                        // Route all requests for members of params through GuiceContainer
                        serve("/rest/*").with(GuiceContainer.class, params);
                    }
                });
    }
}