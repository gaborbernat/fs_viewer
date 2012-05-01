/*
 * ResourceUserTest.java ->
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

import net.primeranks.fs_data.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: gabor.bernat
 * Date: 5/1/12
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceUserTest {
    private enum UserInstances {
        EMPTY(null, null, null),
        YETI_1_sas(new Long(1), "Yeti", "sas");

        private final User u;

        UserInstances(Long i, String n, String d) {
            u = new User(i, n, d);
        }

        public User getUser() {
            return u;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("UserInstances");
            sb.append("{u=").append(u);
            sb.append('}');
            return sb.toString();
        }
    }

    ;
    private ResourceUser ru;

    @Before
    public void setUp() throws Exception {
        ru = new ResourceUser();
        ru.createUser(new JAXBElement<User>(new QName("http://www.novell.com/role/service", "userDN"),
                User.class,
                UserInstances.YETI_1_sas.getUser()));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetUserByNameAndDomainInXML() throws Exception {
        System.out.println("Testing read user as XML");
        assertSame(UserInstances.EMPTY, UserInstances.EMPTY);
        assertEquals(UserInstances.EMPTY, UserInstances.EMPTY);

        assertSame(UserInstances.YETI_1_sas, UserInstances.YETI_1_sas);
        assertEquals(UserInstances.YETI_1_sas, UserInstances.YETI_1_sas);

        assertThat(UserInstances.EMPTY, not(UserInstances.YETI_1_sas));
        // This should fail
        // assertThat(UserInstances.EMPTY, is(UserInstances.YETI_1_sas));
    }


    @Test
    public void testGetPlayerByNameInJSON() throws Exception {
        System.out.println("Testing read user as XML");
        assertSame(UserInstances.EMPTY, UserInstances.EMPTY);
        assertEquals(UserInstances.EMPTY, UserInstances.EMPTY);

        assertSame(UserInstances.YETI_1_sas, UserInstances.YETI_1_sas);
        assertEquals(UserInstances.YETI_1_sas, UserInstances.YETI_1_sas);

        assertThat(UserInstances.EMPTY, not(UserInstances.YETI_1_sas));
    }

    @Test
    public void testCreateUser() throws Exception {
        assertThat(ru.getUserByNameAndDomainInXML(UserInstances.YETI_1_sas.getUser().getName(),
                UserInstances.YETI_1_sas.getUser().getDomain()),
                is(UserInstances.YETI_1_sas.getUser()));
    }
}
