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

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import net.primeranks.fs_viewer.fs_data.Flight;
import net.primeranks.fs_viewer.fs_data.FlightSnapshot;
import net.primeranks.fs_viewer.fs_data.User;

public class Dao extends DAOBase {
    // Objectify is the simplest convenient interface to the Google App Engine datastore.
    // At startup register the DAO objects at Objectifies service.
    static {
        ObjectifyService.register(User.class);
        ObjectifyService.register(Flight.class);
        ObjectifyService.register(FlightSnapshot.class);
    }
}