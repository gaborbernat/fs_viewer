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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.util.Log;
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

        HttpParams httpParams = EntryPointActivity.getHttpClient().getParams();
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
