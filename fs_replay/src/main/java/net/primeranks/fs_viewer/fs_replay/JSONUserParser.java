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

import net.primeranks.fs_viewer.fs_data.User;
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
