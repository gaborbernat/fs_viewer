/*
 * SelectFromListActivity.java ->
 * Copyright (C) 2012-09-11 Gábor Bernát
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

package com.primeranks.bme.fs_replay.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.primeranks.bme.fs_replay.Adapter.FlightAdapter;
import com.primeranks.bme.fs_replay.Adapter.UserAdapter;
import com.primeranks.bme.fs_replay.DAO.GetFlightList;
import com.primeranks.bme.fs_replay.DAO.GetUserList;
import com.primeranks.bme.fs_replay.Data_Pojo.Flight;
import com.primeranks.bme.fs_replay.Data_Pojo.User;
import com.primeranks.bme.fs_replay.R;

import java.util.ArrayList;
import java.util.List;

import static com.primeranks.bme.fs_replay.Activity.SelectFromListActivity.Type.FLIGHT;
import static com.primeranks.bme.fs_replay.Activity.SelectFromListActivity.Type.USER;

public class SelectFromListActivity extends ListActivity implements AdapterView.OnItemClickListener {

    enum Type {
        USER,
        FLIGHT
    }

    private final static String ThisActivityTypeString = "ThisActivityType";
    private final static String ThisUser = "ThisUser";
    public final static String FlightString = "Flight";

    private Type _activityType;

    private User _userForFlight;

    private ArrayAdapter<?> _listDataBindAdapter;

    private List<User> _userList;
    private List<Flight> _flightList;

    private Button _backToTop_footer;
    public ProgressBar progressBar;

    /*
     Set up the creation of the Activity
     */
    public void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        int t = i.getIntExtra(ThisActivityTypeString, -1);
        _activityType = (t == FLIGHT.ordinal()) ? FLIGHT : USER;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_list_core);

        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);

        ListView listView = getListView();
        listView.setOnItemClickListener(this);

        // Add a back to top button on the bottom       - Create the item
        _backToTop_footer = (Button) getLayoutInflater().inflate(R.layout.select_list_footer, null);
        _backToTop_footer.setCompoundDrawablesWithIntrinsicBounds(
                getResources().getDrawable(android.R.drawable.ic_menu_upload), null, null, null);
        _backToTop_footer.setVisibility(View.INVISIBLE);
        listView.addFooterView(_backToTop_footer, null, false);

        switch (_activityType) {
            case USER: {
                _userList = new ArrayList<User>();                   // Start with an empty one
                _listDataBindAdapter = new UserAdapter(this, _userList);
                break;
            }
            case FLIGHT: {
                _userForFlight = (User) i.getSerializableExtra(ThisUser);
                _flightList = new ArrayList<Flight>();
                _listDataBindAdapter = new FlightAdapter(this, _flightList);
                break;
            }
        }

        View _headerUser = getLayoutInflater().inflate(R.layout.select_list_header_user, null);
        listView.addHeaderView(_headerUser, null, false);
        listView.setAdapter(_listDataBindAdapter);
        listView.setItemsCanFocus(false);

        switch (_activityType) {
            case USER: {
                new GetUserList(this).execute();
                break;
            }
            case FLIGHT: {
                new GetFlightList(this).execute(_userForFlight);
                break;
            }
        }
    }

    /*
   Go to the top of the view
    */
    public void backToTop(View v) {
        getListView().setSelection(0);
    }

    public void setUserList(List<User> l) {
        _userList.clear();
        if(l != null)
        _userList.addAll(l);
        ListUpdated(_userList.size());
    }

    public void setFlightList(List<Flight> l) {
        _flightList.clear();
        if( l != null)
        _flightList.addAll(l);
        ListUpdated(_flightList.size());
    }

    public void ListUpdated(int l) {
        _listDataBindAdapter.notifyDataSetChanged();
        int _showBackToTopAfter = 7;
        if (l < _showBackToTopAfter) {
            _backToTop_footer.setVisibility(View.INVISIBLE);
        } else
            _backToTop_footer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (_activityType) {
            case USER: {
                User u = ((UserAdapter) _listDataBindAdapter).getItem(position - 1);
                Intent i = new Intent(SelectFromListActivity.this, SelectFromListActivity.class);
                i.putExtra(ThisActivityTypeString, FLIGHT.ordinal());
                i.putExtra(ThisUser, u);
                startActivity(i);
                break;
            }
            case FLIGHT: {
                Flight f = ((FlightAdapter) _listDataBindAdapter).getItem(position - 1);
                //new GoogleMapsDrawPath();
                Intent i = new Intent(this, GoogleMaps.class);
                i.putExtra(FlightString, f);
                startActivity(i);
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
