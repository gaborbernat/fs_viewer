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

import com.google.inject.Key;
import com.google.inject.name.Names;
import net.primeranks.fs_viewer.fs_data.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import java.util.List;

@Path("user/")
public class ResourceUser extends Resource_RESTInjectorProvider {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getPlayerByNameInJSON(@QueryParam("name") String name, @QueryParam("domain") String domain) {
        List<User> u = dao().findUserByNameAndDomain(name, domain);
        return u;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public String createUser(JAXBElement<User> user) {
        User u = user.getValue();
        u = dao().createUser(u);
        return u.getId() != User.INVALID_ID ? u.getId().toString() : null;
    }

    private DaoUser dao() {
        return (DaoUser) getInjectorInstance().getInstance(Key.get(Dao.class, Names.named("objectify.dao.User")));
    }
}