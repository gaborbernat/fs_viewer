/*
 * JSONParser.java ->
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

package com.primeranks.bme.fs_replay.DAO;

import android.util.Log;
import com.primeranks.bme.fs_replay.Data_Pojo.Flight;
import com.primeranks.bme.fs_replay.Data_Pojo.FlightSnapshot;
import com.primeranks.bme.fs_replay.Data_Pojo.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JSONParser {

    public static List<User> parseUserList(InputStream json) {
        JSONObject all = parseInput(json);
        if (all == null)
            return null;
        JSONArray json_list;
        try {
            json_list = (JSONArray) all.get("user");
        } catch (JSONException e) {
            return null;
        }

        List<User> l = new ArrayList<User>();

        for (int i = 0; i < json_list.length(); ++i) {
            JSONObject c;
            try {
                c = (JSONObject) json_list.get(i);
            } catch (JSONException e) {
                continue;
            }
            User u = new User();

            try {
                u.setId(c.getLong("id"));
            } catch (JSONException e) {
                u.setId(null);
            }
            try {
                u.setDomain(c.getString("domain"));
            } catch (JSONException e) {
                u.setDomain(null);
            }
            try {
                u.setName(c.getString("name"));
            } catch (JSONException e) {
                u.setName(null);
            }

            l.add(u);
        }
        Collections.sort(l, new UserCompareByName());
        return l;
    }

    public static class UserCompareByName implements Comparator<User> {

        @Override
        public int compare(User o, User t) {
            return o.getName().toLowerCase().compareTo(t.getName().toLowerCase());
        }
    }

    private static JSONObject parseInput(InputStream json) {
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
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Log.d(JSONParser.class.toString(), "Failed to close input: " + e.getMessage());
            }
        }
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e) {
            return null;
        }
    }

    public static List<Flight> parseFlightList(InputStream json) {

        JSONObject all = parseInput(json);
        if (all == null)
            return null;
        JSONArray json_list = null;
        try {
            json_list = (JSONArray) all.get("flight_meta");
        } catch (JSONException e) {
            Log.d(JSONParser.class.toString(), e.getLocalizedMessage());
        }
        if (json_list == null)
            return null;

        List<Flight> l = new ArrayList<Flight>();

        for (int i = 0; i < json_list.length(); ++i) {
            JSONObject c;
            try {
                c = (JSONObject) json_list.get(i);
            } catch (JSONException e) {
                Log.d(JSONParser.class.toString(), e.getLocalizedMessage());
                continue;
            }

            Flight u = new Flight();

            try {
                u.setId(c.getLong("@id"));
            } catch (JSONException e) {
                u.setId(null);
            }
            try {
                u.setEndDate(c.getLong("endDate"));
            } catch (JSONException e) {
                u.setEndDate(0);
            }
            try {
                u.setStartDate(c.getLong("startDate"));
            } catch (JSONException e) {
                u.setStartDate(0);
            }
            try {
                u.setUserId(c.getLong("userId"));
            } catch (JSONException e) {
                u.setUserId((long) 0);
            }
            try {
                u.setPeriodicity(c.getInt("periodicity"));
            } catch (JSONException e) {
                u.setPeriodicity(100);
            }

            l.add(u);
        }
        Collections.sort(l, new FlightCompareByStartDate());
        return l;
    }


    public static class FlightCompareByStartDate implements Comparator<Flight> {

        @Override
        public int compare(Flight o, Flight t) {
            if (o.getStartDate() < t.getStartDate()) {
                return 1;
            } else if (o.getStartDate() == t.getStartDate())
                return 0;
            else return -1;
        }
    }

    public static List<FlightSnapshot> parseFlightSnapshotList(InputStream data) {

        JSONObject all = parseInput(data);
        if (all == null)
            return null;
        JSONArray json_list;
        try {
            json_list = (JSONArray) all.get("flightSnapshot");
        } catch (JSONException e) {
            return null;
        }
        FlightSnapshot.Builder b = new FlightSnapshot.Builder();
        List<FlightSnapshot> l = new ArrayList<FlightSnapshot>();


        for (int i = 0; i < json_list.length(); ++i) {
            JSONObject c;
            try {
                c = (JSONObject) json_list.get(i);
            } catch (JSONException e) {
                continue;
            }

            Long id;
            Long flightId;
            // Time
            Long simulationTimeStamp;
            Long measurementTimeStamp;

            // Aircraft type
            String aircraftTypeName;
            String aircraftCode;

            // Position and orientation
            Double latitude;
            Double longitude;
            Double heading;
            Double altitude;
            // Speed
            Double verticalSpeed;

            try {
                id = c.getLong("@id");
            } catch (JSONException e) {
                id = null;
            }
            try {
                flightId = c.getLong("@flightId");
            } catch (JSONException e) {
                flightId = null;
            }
            try {
                simulationTimeStamp = c.getLong("@simulationTimeStamp");
            } catch (JSONException e) {
                simulationTimeStamp = null;
            }

            try {
                measurementTimeStamp = c.getLong("@measurementTimeStamp");
            } catch (JSONException e) {
                measurementTimeStamp = null;
            }

            try {
                aircraftTypeName = c.getString("aircraftTypeName");
            } catch (JSONException e) {
                aircraftTypeName = null;
            }

            try {
                aircraftCode = c.getString("aircraftCode");
            } catch (JSONException e) {
                aircraftCode = null;
            }

            try {
                latitude = c.getDouble("latitude");
            } catch (JSONException e) {
                latitude = null;
            }

            try {
                longitude = c.getDouble("longitude");
            } catch (JSONException e) {
                longitude = null;
            }

            try {
                heading = c.getDouble("heading");
            } catch (JSONException e) {
                heading = null;
            }

            try {
                altitude = c.getDouble("altitude");
            } catch (JSONException e) {
                altitude = null;
            }

            try {
                verticalSpeed = c.getDouble("verticalSpeed");
            } catch (JSONException e) {
                verticalSpeed = null;
            }


            b.Id(id);
            b.flightId(flightId);
            b.simulationTimeStamp(simulationTimeStamp);
            b.measurementTimeStamp(measurementTimeStamp);
            b.aircraftTypeName(aircraftTypeName);
            b.aircraftCode(aircraftCode);
            b.latitude(latitude);
            b.longitude(longitude);
            b.heading(heading);
            b.altitude(altitude);
            b.verticalSpeed(verticalSpeed);
            FlightSnapshot u = b.build();
            l.add(u);
        }
        Collections.sort(l, new FlightSnapshotCompareByDate());
        return l;
    }


    public static class FlightSnapshotCompareByDate implements Comparator<FlightSnapshot> {

        @Override
        public int compare(FlightSnapshot o, FlightSnapshot t) {
            if (o.getMeasurementTimeStamp() < t.getMeasurementTimeStamp())
                return 1;
            else if (o.getMeasurementTimeStamp() == t.getMeasurementTimeStamp())
                return 0;
            else
                return -1;
        }
    }


}
