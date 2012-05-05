/*
 * Config.java ->
 * Copyright (C) 2012-05-06 Gábor Bernát
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

package fs_proxy;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: gabor.bernat
 * Date: 5/5/12
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    static Logger log = Logger.getLogger(Config.class.getName());
    public final static String PROTOCOL = "http";
    public final static String BASE_ADDRESS;
    public final static String REST_PATH = "rest/";
    public final static int BASE_PORT = -1;        // -1 -> select automatically
    public static final String LOG_AS = "FS Proxy";
    public static final String IDENTIFY_CLIENT_AS = LOG_AS + "/1.0";
    public static final String USER_PATH = "user/";

    static {
        if (false)
            BASE_ADDRESS = "www.primeranksfs.appspot.com/";
        else
            BASE_ADDRESS = "localhost:8989";
    }
}
