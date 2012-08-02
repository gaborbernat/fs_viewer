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
public class  GuiceConfigFsPrimeranksServer extends GuiceServletContextListener {
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