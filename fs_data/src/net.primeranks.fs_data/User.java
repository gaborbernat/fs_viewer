/*
 * User.java ->
 * Copyright (C) 2012-05-04 Gábor Bernát
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

package net.primeranks.fs_data;

import com.googlecode.objectify.annotation.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "user")  // For the transport protocol
@Entity(name = "user")          // for the storage
@Indexed                      // Make an index for all the fields unless specified not to
public class User {
    @Id
    private Long id;
    // The username.
    private String name;
    // The domain of the user. If it does not ends as a . something, it suggests a local area usage.
    private String domain;

    // JAXB needs this
    public User() {
    }

    public User(Long id, String name, String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("U");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", domain='").append(domain).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
