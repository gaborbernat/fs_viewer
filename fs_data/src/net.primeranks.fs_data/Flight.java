/*
 * Flight.java ->
 * Copyright (C) 2012-05-01 Gábor Bernát
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

/**
 * Created with IntelliJ IDEA.
 * User: gabor.bernat
 * Date: 4/22/12
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */
package net.primeranks.fs_data;


import java.util.List;

public class Flight {
    private Long id;
    private List<FlightSnapshot> flightData;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FlightSnapshot> getFlightData() {
        return flightData;
    }

    public void setFlightData(List<FlightSnapshot> flightData) {
        this.flightData = flightData;
    }

    public void addFlightData(FlightSnapshot... instances) {
        if (instances != null) {
            for (FlightSnapshot i : instances) {
                this.flightData.add(i);
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
