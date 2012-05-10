/*
 * EntryPointActivity.java ->
 * Copyright (C) 2012-05-10 Gábor Bernát
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

import android.app.ListActivity;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import fs_data.User;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntryPointActivity extends ListActivity implements AdapterView.OnItemClickListener {
    // Maintain a single HTTP client for the entire application
    private static final AbstractHttpClient httpClient;
    // Handle HTTP network failures
    private static final HttpRequestRetryHandler retryHandler;

    // Configure the HTTP client to handle multiple threaded access
    static { // A static initializer will create the object
        SchemeRegistry schemeRegistry = new SchemeRegistry();   // Register the default scheme : http and port 80
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

        // Connection settings for the Connection manager
        HttpParams connManagerParams = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(connManagerParams, 5);     // Max connection # at once
        ConnManagerParams.setMaxConnectionsPerRoute(connManagerParams,
                new ConnPerRouteBean(5));                                   // From which to the same host at once
        ConnManagerParams.setTimeout(connManagerParams, 15 * 1000);         // After 15s time out

        // Create a thread safe connection manager with the upper settings
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(connManagerParams, schemeRegistry);

        // Configure the client settings
        HttpParams clientParams = new BasicHttpParams();
        HttpProtocolParams.setUserAgent(clientParams, Config.IDENTIFY_CLIENT_AS);// Identify outgoing connections as
        HttpConnectionParams.setConnectionTimeout(clientParams, 15 * 1000); // Connection timeout
        HttpConnectionParams.setSoTimeout(clientParams, 15 * 1000);         // Socket timeout
        httpClient = new DefaultHttpClient(cm, clientParams);               // Create the a customized HTTP client

        // What to do if a connection fails
        retryHandler = new DefaultHttpRequestRetryHandler(5, false) {       // Retry 5 times, let the default
            // implementation decide to retry or not
            public boolean retryRequest(IOException exception, int executionCount,
                                        HttpContext context) {
                if (!super.retryRequest(exception, executionCount, context)) {
                    Log.d(Config.LOG_AS, Resources.getSystem().getString(R.string.noMoreHTTPRetry));
                    return false;
                }
                try {
                    Thread.sleep(2000);                                     // Wait between retries
                } catch (InterruptedException ignored) {
                }
                Log.d(Config.LOG_AS, Resources.getSystem().getString(R.string.retryHTTPLog));
                return true;
            }
        };

        // Use our customer retry handler, instead of the default one
        httpClient.setHttpRequestRetryHandler(retryHandler);
    }

    // Interface to access the application wide HTTP client
    public static HttpClient getHttpClient() {
        return httpClient;
    }

    private UserAdapter userAdapter;
    private List<User> userList;
    private Button backToTop_footer;
    private final int showBackToTopAfter = 8;
    private View headerUser;

    private ConnectionChangedBroadcastReceiver connectionReceiver;

    /*
     Set up the creation of the Activity
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_point);

        ListView listView = getListView();
        listView.setOnItemClickListener(this);

        // Add a back to top button on the bottom       - Create the item
        backToTop_footer = (Button) getLayoutInflater().inflate(R.layout.user_footer, null);
        backToTop_footer.setCompoundDrawablesWithIntrinsicBounds(
                getResources().getDrawable(android.R.drawable.ic_menu_upload), null, null, null);
        backToTop_footer.setVisibility(View.INVISIBLE);            // While empty do not show it
        headerUser = getLayoutInflater().inflate(R.layout.user_header, null);
        listView.addFooterView(backToTop_footer, null, true);      // Add it to the bottom
        listView.addHeaderView(headerUser, null, false);

        userList = new ArrayList<User>();                   // Start with an empty one
        this.userAdapter = new UserAdapter(this, userList);
        listView.setAdapter(this.userAdapter);
        listView.setItemsCanFocus(false);

        connectionReceiver = new ConnectionChangedBroadcastReceiver();
        registerReceiver(connectionReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /*
   Go to the top of the view
    */
    public void backToTop() {
        getListView().setSelection(0);
    }

    public void setUserList(List<User> l) {
        userList.clear();
        userList.addAll(l);
        userAdapter.notifyDataSetChanged();
        if (l.size() < showBackToTopAfter) {
            backToTop_footer.setVisibility(View.INVISIBLE);
        } else
            backToTop_footer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetUserList(this).execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User u = userAdapter.getItem(position);
        Toast.makeText(this, getString(R.string.userSelectedToastText) + u.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(connectionReceiver);
        super.onStop();
    }
}
