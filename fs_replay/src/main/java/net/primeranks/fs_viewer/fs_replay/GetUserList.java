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
package com.primeranks.bme.fs_replay;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import fs_data.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.util.List;

/**
 * Get a list of users working. No parameter, no progress report and a list of users as answer.
 */
public class GetUserList extends AsyncTask<Void, Void, List<User>> {
    private EntryPointActivity a;

    public GetUserList(EntryPointActivity a) {
        this.a = a;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(a, a.getString(R.string.notifyStartGetUserList), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<User> doInBackground(Void... args) {
        try {
            Log.d(Config.LOG_AS, "Start to request data from: " + Config.FS_USER_ENTRY);
            HttpClient httpClient = EntryPointActivity.getHttpClient();
            HttpGet request = new HttpGet(Config.FS_USER_ENTRY);
            HttpResponse response = httpClient.execute(request);
            InputStream data = response.getEntity().getContent();
            return JSONUserParser.parseData(data);
        } catch (Exception e) {
            Log.d(Config.LOG_AS, "Exception" + e.toString() + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<User> l) {
        if (l == null) {
            Toast.makeText(a, a.getString(R.string.errorGetUserList), Toast.LENGTH_LONG).show();
            return;
        }
        String t = a.getString(R.string.returnedUserCount, l.size());
        Toast.makeText(a, t, Toast.LENGTH_LONG).show();
        a.setUserList(l);
    }

}
