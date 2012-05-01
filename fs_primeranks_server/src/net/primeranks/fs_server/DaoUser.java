/*
 * DaoUser.java ->
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

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import net.primeranks.fs_data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DaoUser extends Dao {
    Logger log = Logger.getLogger(DaoUser.class.getName());

    public User findUserById(Long id) {
        return ofy().get(User.class, id);
    }

    public User findUserByNameAndDomain(String name, String domain) {
        return ofy().query(User.class).filter("name", name).filter("domain", domain).get();
    }

    public User createUser(User u) {
        if (findUserByNameAndDomain(u.getName(), u.getDomain()) == null) {
            ofy().put(u);
        }
        return u;
    }

    public List<User> getAllUsers() {
        List<User> r = new ArrayList<User>();

        Objectify ofy = ObjectifyService.begin();
        Query<User> q = ofy.query(User.class);
        Iterable<User> tbl = q.fetch();

        for (User p : tbl) {
            r.add(p);
        }
        return r;
    }
}