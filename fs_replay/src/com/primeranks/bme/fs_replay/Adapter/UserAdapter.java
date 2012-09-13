/*
 * UserAdapter.java ->
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

package com.primeranks.bme.fs_replay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.primeranks.bme.fs_replay.Data_Pojo.User;
import com.primeranks.bme.fs_replay.R;

import java.util.List;

/**
 * Handles the local copy of the select_list_item_user list
 */
public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, List<User> u) {  // The custom adapter extends the default one
        super(context,                     // - Use the same context
                R.layout.select_list_item_user,             // - With the select_list_item_user layout
                u);                        // - With the given select_list_item_user list
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Use the layout inflater to allow handling long lists
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_list_item_user, parent, false);
        }

        TextView userName;
        TextView userDomain;

        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            userName = (TextView) convertView.findViewById(R.id.user);
            userDomain = (TextView) convertView.findViewById(R.id.domain);

            User user = getItem(position);      // Get the select_list_item_user data
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
