/*
 * GetUserList.java ->
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

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.primeranks.bme.fs_replay.Activity.SelectFromListActivity;
import com.primeranks.bme.fs_replay.Config;
import com.primeranks.bme.fs_replay.Network.Client;
import com.primeranks.bme.fs_replay.R;
import net.primeranks.fs_data.User;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.util.List;

/**
 * Get a list of users working. No parameter, no progress report and a list of users as answer.
 */
public class GetUserList extends AsyncTask<Void, Void, List<User>> {
    private SelectFromListActivity a;

    public GetUserList(SelectFromListActivity a) {
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
            HttpClient httpClient = Client.getHttpClient();
            HttpGet request = new HttpGet(Config.FS_USER_ENTRY);
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                return null;
            InputStream data = response.getEntity().getContent();
            return JSONParser.parseUserList(data);
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
