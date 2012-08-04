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
package net.primeranks.fs_viewer.fs_replay;

public final class Config {
    public static final String FS_PRIMERANKS_API_REST_ENDPOINT = "http://primeranksfs.appspot.com/rest/";
    public static final String FS_USER_ENTRY = FS_PRIMERANKS_API_REST_ENDPOINT + "user/";
    public static final String LOG_AS = "FS Replay";
    public static final String IDENTIFY_CLIENT_AS = LOG_AS + "/1.0";
}
