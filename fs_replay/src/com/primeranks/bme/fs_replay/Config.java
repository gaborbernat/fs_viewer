/*
 * Config.java ->
 * Copyright (C) 2012-05-05 Gábor Bernát
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

package com.primeranks.bme.fs_replay;

public final class Config {
    public static final String FS_PRIMERANKS_API_REST_ENDPOINT = "http://primeranksfs.appspot.com/rest";
    public static final String FS_USER_ENTRY = FS_PRIMERANKS_API_REST_ENDPOINT + "/user";
   public static final String FS_FLIGHT_ENTRY = FS_PRIMERANKS_API_REST_ENDPOINT + "/flight";
    public static final String FS_FLIGHT_SNAPSHOT_ENTRY = FS_FLIGHT_ENTRY + "/snapshot";
    public static final String LOG_AS = "FS Replay";
    public static final String IDENTIFY_CLIENT_AS = LOG_AS + "/1.0";
}
