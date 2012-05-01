/*
 * ResourceUser.java ->
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

import com.google.inject.Key;
import com.google.inject.name.Names;
import net.primeranks.fs_data.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

@Path("/user")
public class ResourceUser extends ResourceRESTInjectorProvider {
    @GET
    @Path("{name}/xml")
    @Produces(MediaType.TEXT_XML)
    public User getUserByNameAndDomainInXML(@PathParam("name") String name, @PathParam("domain") String domain) {
        return dao().findUserByNameAndDomain(name, domain);
    }

    @GET
    @Path("{name}/json")
    @Produces("application/json")
    public User getPlayerByNameInJSON(@PathParam("name") String name, @PathParam("domain") String domain) {
        User r = dao().findUserByNameAndDomain(name, domain);
        return r;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createUser(JAXBElement<User> user) {
        User u = user.getValue();
        dao().createUser(u);
        return Response.created(uriInfo.getAbsolutePath()).build();
    }

    private DaoUser dao() {
        return (DaoUser) getInjectorInstance().getInstance(Key.get(Dao.class, Names.named("objectify.dao.User")));
    }
}