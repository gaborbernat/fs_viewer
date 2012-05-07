/*
 * FlightSnapshotCore.java ->
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

/**
 * Created with IntelliJ IDEA.
 * User: gabor.bernat
 * Date: 5/6/12
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class FlightSnapshotCore {
    // Time
    protected long simulationTimeStamp;
    protected long measurementTimeStamp;

    // Aircraft type
    protected String aircraftTypeName;
    protected String aircraftCode;

    // Position and orientation
    protected double latitude;
    protected double longitude;
    protected double heading;
    protected double altitude;

    // Speed
    protected double verticalSpeed;

    protected FlightSnapshotCore(long simulationTimeStamp, long measurementTimeStamp, String aircraftTypeName,
                                 String aircraftCode, double latitude, double longitude,
                                 double heading, double altitude, double verticalSpeed) {
        this.simulationTimeStamp = simulationTimeStamp;
        this.measurementTimeStamp = measurementTimeStamp;
        this.aircraftTypeName = aircraftTypeName;
        this.aircraftCode = aircraftCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.heading = heading;
        this.altitude = altitude;
        this.verticalSpeed = verticalSpeed;
    }

    protected FlightSnapshotCore() {
        this.simulationTimeStamp = 0;
        this.measurementTimeStamp = 0;
        this.aircraftTypeName = null;
        this.aircraftCode = null;
        this.latitude = 0;
        this.longitude = 0;
        this.heading = 0;
        this.altitude = 0;
        this.verticalSpeed = 0;
    }

    /*
       Transforms the data into a string format.
       @return The object as a string.
    */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FlightSnapshot{");
        sb.append("simulationTimeStamp=").append(simulationTimeStamp);
        sb.append(", measurementTimeStamp=").append(measurementTimeStamp);
        sb.append(", aircraftTypeName='").append(aircraftTypeName).append('\'');
        sb.append(", aircraftCode='").append(aircraftCode).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", heading=").append(heading);
        sb.append(", altitude=").append(altitude);
        sb.append(", verticalSpeed=").append(verticalSpeed);
        sb.append('}');
        return sb.toString();
    }

    /*
       Check if two instances of the object are the same.
       This ignores the measurementTimeStamp field.
       @return True if the two objects are the same, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object
        if (o == null || getClass() != o.getClass()) return false; // Different type

        FlightSnapshot that = (FlightSnapshot) o; // Convert and compare

        return Double.compare(that.altitude, altitude) == 0 &&
                Double.compare(that.heading, heading) == 0 &&
                Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                simulationTimeStamp == that.simulationTimeStamp &&
                Double.compare(that.verticalSpeed, verticalSpeed) == 0 &&
                !(aircraftCode != null ? !aircraftCode.equals(that.aircraftCode) : that.aircraftCode != null) &&
                !(aircraftTypeName != null ? !aircraftTypeName.equals(that.aircraftTypeName) : that.aircraftTypeName != null);
    }

    /*
        Generate a hash code from the field members of the objects.
        The measurementTimeStamp field will not influence the value generated.
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (simulationTimeStamp ^ (simulationTimeStamp >>> 32));
        result = 31 * result + (aircraftTypeName != null ? aircraftTypeName.hashCode() : 0);
        result = 31 * result + (aircraftCode != null ? aircraftCode.hashCode() : 0);
        temp = latitude != +0.0d ? Double.doubleToLongBits(latitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = longitude != +0.0d ? Double.doubleToLongBits(longitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = heading != +0.0d ? Double.doubleToLongBits(heading) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = altitude != +0.0d ? Double.doubleToLongBits(altitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = verticalSpeed != +0.0d ? Double.doubleToLongBits(verticalSpeed) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
