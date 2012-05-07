/*
 * FlightSnapshotJAXBAdapted.java ->
 * Copyright (C) 2012-05-07 Gábor Bernát
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

package net.primeranks.fs_data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Unfortunately the JAXB cannot handle Immutable objects such as the FlightSnapshot is.
 * This is an adapter class that has the same structure as FlightSnapshot, however no longer immutable.
 */
@XmlRootElement
public class FlightSnapshotJAXBAdapted extends FlightSnapshotCore {
    public FlightSnapshotJAXBAdapted() {
    }

    public long getSimulationTimeStamp() {
        return this.simulationTimeStamp;
    }

    public void setSimulationTimeStamp(long simulationTimeStamp) {
        this.simulationTimeStamp = simulationTimeStamp;
    }

    public long getMeasurementTimeStamp() {
        return measurementTimeStamp;
    }

    public void setMeasurementTimeStamp(long measurementTimeStamp) {
        this.measurementTimeStamp = measurementTimeStamp;
    }

    public String getAircraftTypeName() {
        return aircraftTypeName;
    }

    public void setAircraftTypeName(String aircraftTypeName) {
        this.aircraftTypeName = aircraftTypeName;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getVerticalSpeed() {
        return verticalSpeed;
    }

    public void setVerticalSpeed(double verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

}
