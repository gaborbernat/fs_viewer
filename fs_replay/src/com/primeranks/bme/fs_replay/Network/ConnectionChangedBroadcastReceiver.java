/*
 * ConnectionChangedBroadcastReceiver.java ->
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

package com.primeranks.bme.fs_replay.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.util.Log;
import com.primeranks.bme.fs_replay.Config;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.params.HttpParams;

public class ConnectionChangedBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        String info = intent.getStringExtra(ConnectivityManager.EXTRA_EXTRA_INFO);
        NetworkInfo nwInfo =
                intent
                        .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        Log.d(Config.LOG_AS, info + ": " + nwInfo.getReason());

        HttpParams httpParams = Client.getHttpClient().getParams();
        if (nwInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            String proxyHost = Proxy.getHost(context);
            if (proxyHost == null) {
                proxyHost = Proxy.getDefaultHost();
            }
            int proxyPort = Proxy.getPort(context);
            if (proxyPort == -1) {
                proxyPort = Proxy.getDefaultPort();
            }
            if (proxyHost != null && proxyPort > -1) {
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            } else {
                httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
            }
        } else {
            httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
        }
    }
}
