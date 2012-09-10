/*
 * JSONUserParser.java ->
 * Copyright (C) 2012-09-10 Gábor Bernát
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

package com.primeranks.bme.fs_replay.DAO;

import net.primeranks.fs_data.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSONUserParser {

    public static List<User> parseData(InputStream json) throws Exception {

        // The Android relies that the entire JSON object is in the memory already, read it into a buffer
        BufferedReader reader = new BufferedReader(new InputStreamReader(json));
        StringBuilder sb = new StringBuilder();

        try {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            reader.close();
        }
        JSONObject all = new JSONObject(sb.toString());
        JSONArray json_list = (JSONArray) all.get("user");

        List<User> l = new ArrayList<User>();

        for (int i = 0; i < json_list.length(); ++i) {
            JSONObject c = (JSONObject) json_list.get(i);
            User u = new User();
            u.setId(new Long(c.getInt("id")));
            u.setDomain(c.getString("domain"));
            u.setName(c.getString("name"));
            l.add(u);
        }
        return l;
    }
}
