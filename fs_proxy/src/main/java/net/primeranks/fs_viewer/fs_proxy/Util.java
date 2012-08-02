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

import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Util {
    static org.slf4j.Logger log = LoggerFactory.getLogger(Util.class.getName());

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
            log.error(e.getMessage() + "\n" + e.getStackTrace().toString());
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
                log.error( "Could not make settings directory: " + settingsDirectory.toString());
                throw new IllegalStateException(settingsDirectory.toString());
            }
        }
        return settingsDirectory;
    }
}
