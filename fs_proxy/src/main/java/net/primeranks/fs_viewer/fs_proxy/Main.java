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