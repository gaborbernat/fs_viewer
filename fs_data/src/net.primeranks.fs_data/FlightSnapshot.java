/*
 * FlightSnapshot.java ->
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
package net.primeranks.fs_data;

import javax.xml.bind.annotation.XmlRootElement;

/*
    Flight measurement data for a given time slice.
    Immutable object, use the inner Builder object to create a new instance.
 */
@XmlRootElement
public class FlightSnapshot {
    // Time
    private final long simulationTimeStamp;
    private final long measurementTimeStamp;

    // Aircraft type
    private final String aircraftTypeName;
    private final String aircraftCode;

    // Position and orientation
    private final double latitude;
    private final double longitude;
    private final double heading;
    private final double altitude;

    // Speed
    private final double verticalSpeed;

    private FlightSnapshot(Builder b) {
        simulationTimeStamp = b.simulationTimeStamp;
        measurementTimeStamp = b.measurementTimeStamp;
        aircraftCode = b.aircraftCode;
        aircraftTypeName = b.aircraftTypeName;
        latitude = b.latitude;
        longitude = b.longitude;
        heading = b.heading;
        altitude = b.altitude;
        verticalSpeed = b.verticalSpeed;
    }

    public static class Builder {
        public static final double LATITUDE_DEFAULT = Double.MAX_VALUE;
        public static final double LONGITUDE_DEFAULT = Double.MAX_VALUE;
        public static final double HEADING_DEFAULT = Double.MAX_VALUE;
        public static final double ALTITUDE_DEFAULT = Double.MAX_VALUE;
        public static final long TIME_DEFAULT = 0;
        public static final double VERTICAL_SPEED_DEFAULT = Double.MAX_VALUE;

        // Time
        public long simulationTimeStamp = TIME_DEFAULT;
        public long measurementTimeStamp = TIME_DEFAULT;

        // Aircraft type
        private String aircraftTypeName = null;
        private String aircraftCode = null;

        // Position and orientation
        private double latitude = LATITUDE_DEFAULT;
        private double longitude = LONGITUDE_DEFAULT;
        private double heading = HEADING_DEFAULT;
        private double altitude = ALTITUDE_DEFAULT;

        // Speed
        private double verticalSpeed = VERTICAL_SPEED_DEFAULT;

        public Builder simulationTimeStamp(long simulationTimeStamp) {
            this.simulationTimeStamp = simulationTimeStamp;
            return this;
        }

        public Builder measurementTimeStamp(long measurementTimeStamp) {
            this.measurementTimeStamp = measurementTimeStamp;
            return this;
        }

        public Builder aircraftTypeName(String aircraftTypeName) {
            this.aircraftTypeName = aircraftTypeName;
            return this;
        }

        public Builder aircraftCode(String aircraftCode) {
            this.aircraftCode = aircraftCode;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder heading(double heading) {
            this.heading = heading;
            return this;
        }

        public Builder altitude(double altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder verticalSpeed(double verticalSpeed) {
            this.verticalSpeed = verticalSpeed;
            return this;
        }

        /**
         * Build a Flightsnapshot object.
         *
         * @return The FlightSnapshot object. If all its values are default returns null object.
         */
        public FlightSnapshot build() {
            // Avoid Object creation if
            if (TIME_DEFAULT == simulationTimeStamp &&
                    TIME_DEFAULT == measurementTimeStamp &&
                    null == aircraftCode &&
                    null == aircraftTypeName &&
                    ALTITUDE_DEFAULT == altitude &&
                    LONGITUDE_DEFAULT == longitude &&
                    LATITUDE_DEFAULT == latitude &&
                    VERTICAL_SPEED_DEFAULT == verticalSpeed &&
                    HEADING_DEFAULT == heading)
                return null;
            return new FlightSnapshot(this);
        }
    }

    /*
       See if any valid data is passed.
       @return True if all data fields correspond to the default settings.
    */
    public boolean isDefault() {
        return Builder.TIME_DEFAULT == simulationTimeStamp &&
                Builder.TIME_DEFAULT == measurementTimeStamp &&
                null == aircraftCode &&
                null == aircraftTypeName &&
                Builder.ALTITUDE_DEFAULT == altitude &&
                Builder.LONGITUDE_DEFAULT == longitude &&
                Builder.LATITUDE_DEFAULT == latitude &&
                Builder.VERTICAL_SPEED_DEFAULT == verticalSpeed &&
                Builder.HEADING_DEFAULT == heading;
    }

    public long simulationTimeStamp() {
        return simulationTimeStamp;
    }

    public long measurementTimeStamp() {
        return measurementTimeStamp;
    }

    public String aircraftTypeName() {
        return aircraftTypeName;
    }

    public String aircraftCode() {
        return aircraftCode;
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    public double heading() {
        return heading;
    }

    public double altitude() {
        return altitude;
    }

    public double verticalSpeed() {
        return verticalSpeed;
    }

    /**
     * Return the different fields between the two objects. Null if they are the same. Measurement time ignored.
     *
     * @param x The object to compare with.
     * @return Returns the different fields in the two objects.
     */
    public FlightSnapshot differenceFrom(FlightSnapshot x) {
        if (x == null)
            return this;
        return new Builder()
                .simulationTimeStamp(x.simulationTimeStamp == this.simulationTimeStamp ? Builder.TIME_DEFAULT : this.simulationTimeStamp)
                        // Ignore the measurement timestamp, as this does not provides valuable information, mostly.
                        //.measurementTimeStamp(x.measurementTimeStamp == this.measurementTimeStamp ? Builder.TIME_DEFAULT : this.measurementTimeStamp)
                .aircraftCode(x.aircraftCode.equals(this.aircraftCode) ? null : this.aircraftCode)
                .aircraftTypeName(x.aircraftTypeName.equals(this.aircraftTypeName) ? null : this.aircraftTypeName)
                .latitude(x.altitude == this.altitude ? Builder.LATITUDE_DEFAULT : this.altitude)
                .longitude(x.longitude == this.longitude ? Builder.LONGITUDE_DEFAULT : this.longitude)
                .heading(x.heading == this.heading ? Builder.HEADING_DEFAULT : this.heading)
                .altitude(x.altitude == this.altitude ? Builder.ALTITUDE_DEFAULT : this.altitude)
                .verticalSpeed(x.verticalSpeed == this.verticalSpeed ? Builder.VERTICAL_SPEED_DEFAULT : this.verticalSpeed)
                .build();
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
