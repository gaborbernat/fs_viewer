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

import com.google.inject.AbstractModule;

/**
 * User: Gábor Bernát
 * Date: 5/5/12 - 8:55 PM
 * Time:
 */
public class FsProxyModule extends AbstractModule {
    public FsProxyModule() {
    }

    @Override
    public void configure() {
        bind(FS_Proxy_I.class).to(FS_Proxy_Basic.class);
        bind(Dao.class).to(ApacheHttpClientRESTConnection.class);
    }
}