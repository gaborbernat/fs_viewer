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

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import net.primeranks.fs_viewer.fs_data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DaoUser extends Dao {
    Logger log = Logger.getLogger(DaoUser.class.getName());

    public User findUserById(Long id) {
        return ofy().get(User.class, id);
    }

    public List<User> findUserByNameAndDomain(String name, String domain) {
        Query<User> q = ofy().query(User.class);
        if (name != null && name.length() != 0)
            q.filter("name", name);
        if (domain != null && domain.length() != 0)
            q.filter("domain", domain);
        return q.list();
    }

    public User createUser(User u) {
        List<User> l = findUserByNameAndDomain(u.getName(), u.getDomain());
        if (l.size() == 0)
            ofy().put(u);
        else
            u = l.get(0);
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