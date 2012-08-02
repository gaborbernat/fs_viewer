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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fs_data.User;

import java.util.List;

/**
 * Handles the local copy of the user list
 */
public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, List<User> u) {  // The custom adapter extends the default one
        super(context,                     // - Use the same context
                R.layout.user,             // - With the user layout
                u);                        // - With the given user list
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Use the layout inflater to allow handling long lists
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user, parent, false);
        }

        TextView userName;
        TextView userDomain;

        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            userName = (TextView) convertView.findViewById(R.id.user);
            userDomain = (TextView) convertView.findViewById(R.id.domain);

            User user = getItem(position);      // Get the user data
            if (user != null) {
                userName.setText(user.getName());
                userDomain.setText(user.getDomain());
            }
            holder = new ViewHolder(userName, userDomain);
            convertView.setTag(holder);
        }
        return convertView;
    }

    private class ViewHolder {
        protected final TextView name;
        protected final TextView domain;

        public ViewHolder(TextView name, TextView domain) {
            this.name = name;
            this.domain = domain;
        }
    }
}
