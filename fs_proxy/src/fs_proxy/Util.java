/*
 * Util.java ->
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

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    static Logger log = Logger.getLogger(Util.class.getName());

    /*
     * Get the current user name of the system.
     */
    public static String getUserName() {
        String name;
        name = System.getProperty("user.name", "NoUserName");
        return name;
    }

    /*
    * Return the current domain of the computer. Null in case of failure.
    */
    public static String getDomain() {
        String domain;
        try {
            domain = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.log(Level.SEVERE, e.getMessage() + "\n" + e.getStackTrace().toString());
            domain = null;
        }
        return domain;
    }

    public static String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * Return the current users home directory.
     * If doesn't exists yet, create it.
     *
     * @return File
     */
    public static File getSettingsDirectory() {
        String userHome = System.getProperty("user.home");
        if (userHome == null) {
            throw new IllegalStateException("user.home==null");
        }
        File home = new File(userHome);
        File settingsDirectory = new File(home, ".fs_proxy");
        if (!settingsDirectory.exists()) {
            if (!settingsDirectory.mkdir()) {
                log.log(Level.SEVERE, "Could not make settings directory: " + settingsDirectory.toString());
                throw new IllegalStateException(settingsDirectory.toString());
            }
        }
        return settingsDirectory;
    }
}
