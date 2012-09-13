/*
 * GetFlightSnapshots.java ->
 * Copyright (C) 2012-09-12 Gábor Bernát
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
import android.view.View;
import com.primeranks.bme.fs_replay.Activity.GoogleMaps;
import com.primeranks.bme.fs_replay.Config;
import com.primeranks.bme.fs_replay.Data_Pojo.Flight;
import com.primeranks.bme.fs_replay.Data_Pojo.FlightSnapshot;
import com.primeranks.bme.fs_replay.Network.Client;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.util.List;

public class GetFlightSnapshots extends AsyncTask<Flight, Void, List<FlightSnapshot>> {

    private GoogleMaps a;

    public GetFlightSnapshots(GoogleMaps a) {
        this.a = a;
    }

    @Override
    protected void onPreExecute() {
        a.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<FlightSnapshot> doInBackground(Flight... users) {
        try {
            if(users == null || users[0]==null) return null;
            String uri = getUriForFlight(users[0]);
            Log.d(Config.LOG_AS, "Start to request data from: " + uri);
            HttpClient httpClient = Client.getHttpClient();
            HttpGet request = new HttpGet(uri);
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                return null;
            InputStream data = response.getEntity().getContent();
            return JSONParser.parseFlightSnapshotList(data);

        } catch (Exception e) {
            Log.d(Config.LOG_AS, "Exception" + e.toString() + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String getUriForFlight(Flight f)
    {
        //http://primeranksfs.appspot.com/rest/flight?userId=10009
        return  String.format("%s?flightId=%d", Config.FS_FLIGHT_SNAPSHOT_ENTRY, f.getId());
    }

    @Override
    protected void onPostExecute(List<FlightSnapshot> l) {
        a.progressBar.setVisibility(View.GONE);
        a.setFlightSnapshot(l);
    }

}
