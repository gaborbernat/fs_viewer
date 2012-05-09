/*
 * Main.java ->
 * Copyright (C) 2012-05-08 Gábor Bernát
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

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * The Main entry point for the proxy class.
 */
public class Main {
    public static void main(String s[]) {
        System.out.println(Util.getWorkingDirectory());
        InputStream is = Main.class.getClassLoader().getResourceAsStream("META-INF/Xlogging.properties");
        try {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Injector injector = Guice.createInjector(new FsProxyModule());
        FS_Proxy_I x = injector.getInstance(FS_Proxy_I.class);
        x.run(1000);
    }
}