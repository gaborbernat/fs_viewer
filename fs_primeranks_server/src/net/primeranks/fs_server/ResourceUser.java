/*
 * ResourceUser.java ->
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

import net.primeranks.fs_data.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import java.util.List;

@Path("user/")
public class ResourceUser extends Resource_RESTInjectorProvider {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getPlayerByNameInJSON(@QueryParam("name") String name, @QueryParam("domain") String domain) {
        List<User> u = Dao.User().findUserByNameAndDomain(name, domain);
        return u;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public String createUser(JAXBElement<User> user) {
        User u = user.getValue();
        u = Dao.User().createUser(u);
        return u.getId() != User.INVALID_ID ? u.getId().toString() : null;
    }


}