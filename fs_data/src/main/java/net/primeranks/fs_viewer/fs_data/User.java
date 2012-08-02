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
package net.primeranks.fs_viewer.fs_data;

import com.googlecode.objectify.annotation.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "user")  // For the transport protocol
@Entity(name = "user")          // For the storage
@Indexed                        // For the storage -> Make an index for all the fields unless specified not to
public class User {
    public final static Long INVALID_ID = null;
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
