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
package net.primeranks.fs_viewer.fs_proxy;

import javax.ws.rs.core.MediaType;


public class Config {
    public static final String TRANSPORT_FORMAT = MediaType.APPLICATION_JSON;
    public final static String PROTOCOL = "http";
    public final static String BASE_ADDRESS;
    public final static String REST_PATH = "rest/";
    public final static int BASE_PORT = -1;        // -1 -> select automatically
    public static final String LOG_AS = "FS Proxy";
    public static final String IDENTIFY_CLIENT_AS = LOG_AS + "/1.0";
    public static final String USER_PATH = "user/";

    static {
        if (true)
            BASE_ADDRESS = "www.primeranksfs.appspot.com";
        else
            BASE_ADDRESS = "localhost:8989";
    }

    public static final String FLIGHT_PATH = "flight";
    public static final String FLIGHTSNAPSHOT_PATH = "flight/snapshot";
}
