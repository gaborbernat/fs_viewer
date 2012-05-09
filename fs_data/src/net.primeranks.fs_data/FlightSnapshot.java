/*
 * FlightSnapshot.java ->
 * Copyright (C) 2012-05-09 Gábor Bernát
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

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/*
    Flight measurement data for a given time slice.
    Immutable object, use the inner Builder object to create a new instance.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FlightSnapshot {
    @Id
    @XmlAttribute
    protected Long id;
    @XmlAttribute
    protected Long flightId;
    // Time
    @XmlAttribute
    protected long simulationTimeStamp;
    @XmlAttribute
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

    public static FlightSnapshot DEFAULT = Builder.DEFAULT.build();

    public FlightSnapshot(Builder b) {
        setValues(b);
    }

    public FlightSnapshot() {
        setValues(Builder.DEFAULT);
    }


    private void setValues(Builder b) {
        setValues(b.id, b.flightId, b.simulationTimeStamp, b.measurementTimeStamp, b.aircraftTypeName,
                b.aircraftCode, b.latitude, b.longitude, b.heading, b.altitude, b.verticalSpeed);
    }

    private void setValues(Long id, Long flightId, long simulationTimeStamp, long measurementTimeStamp, String aircraftTypeName,
                           String aircraftCode, double latitude, double longitude,
                           double heading, double altitude, double verticalSpeed) {
        this.id = id;
        this.flightId = flightId;
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


    /*
       See if any valid data is passed.
       @return True if all data fields correspond to the default settings.
    */
    public boolean isDefault() {
        return this.equals(FlightSnapshot.DEFAULT);
    }

    private Object checkIfZeroAndApplyDef(Object what, Object to, Object def) {
        Object r;
        if (what == null && to == null)
            r = def;
        else if (what == null && to != null)
            r = what;
        else if (what != null && to == null) {
            r = def;
        } else if (what.equals(to)) {
            r = def;
        } else r = what;

        return r;
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
                //.measurementTimeStamp(x.measurementTimeStamp == this.measurementTimeStamp ? DEFAULT.measurementTimeStamp : this.measurementTimeStamp)
                .simulationTimeStamp(x.simulationTimeStamp == this.simulationTimeStamp ? DEFAULT.simulationTimeStamp : this.simulationTimeStamp)
                .latitude(x.latitude == this.latitude ? DEFAULT.latitude : this.latitude)
                .longitude(x.longitude == this.longitude ? DEFAULT.longitude : this.longitude)
                .heading(x.heading == this.heading ? DEFAULT.heading : this.heading)
                .altitude(x.altitude == this.altitude ? DEFAULT.altitude : this.altitude)
                .verticalSpeed(x.verticalSpeed == this.verticalSpeed ? DEFAULT.verticalSpeed : this.verticalSpeed)

                .aircraftCode((String) checkIfZeroAndApplyDef(this.aircraftCode, x.aircraftCode, DEFAULT.aircraftCode))
                .aircraftTypeName((String) checkIfZeroAndApplyDef(this.aircraftTypeName, x.aircraftTypeName, DEFAULT.aircraftTypeName))
                        // These are not taking part in the difference
                        //.Id((Long) checkIfZeroAndApplyDef(this.id, x.id, DEFAULT.id))
                        //.flightId((Long) checkIfZeroAndApplyDef(this.flightId, x.flightId, DEFAULT.flightId))
                .build();

        // Ignore the measurement timestamp, as this does not provides valuable information, mostly.
        //.measurementTimeStamp(x.measurementTimeStamp == this.measurementTimeStamp ? Builder.TIME_DEFAULT : this.measurementTimeStamp)
    }

    /*
       Transforms the data into a string format.
       @return The object as a string.
    */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FlightSnapshot{");
        sb.append("id=").append(id);
        sb.append(", flightId=").append(flightId);
        sb.append(", simulationTimeStamp=").append(simulationTimeStamp);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightSnapshot that = (FlightSnapshot) o;

        if (Double.compare(that.altitude, altitude) != 0) return false;
        if (Double.compare(that.heading, heading) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (simulationTimeStamp != that.simulationTimeStamp) return false;
        if (Double.compare(that.verticalSpeed, verticalSpeed) != 0) return false;
        if (aircraftCode != null ? !aircraftCode.equals(that.aircraftCode) : that.aircraftCode != null) return false;
        if (aircraftTypeName != null ? !aircraftTypeName.equals(that.aircraftTypeName) : that.aircraftTypeName != null)
            return false;

        return true;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public long getSimulationTimeStamp() {
        return simulationTimeStamp;
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

    public static class Builder {
        public static Builder DEFAULT;

        static {
            DEFAULT = new Builder(true);  // create

            DEFAULT.id = null;        // set
            DEFAULT.flightId = null;

            DEFAULT.simulationTimeStamp = 0;
            DEFAULT.measurementTimeStamp = 0;
            // Aircraft type
            DEFAULT.aircraftTypeName = null;
            DEFAULT.aircraftCode = null;
            DEFAULT.latitude = Double.MAX_VALUE;
            DEFAULT.longitude = Double.MAX_VALUE;
            DEFAULT.heading = Double.MAX_VALUE;
            DEFAULT.altitude = Double.MAX_VALUE;
            DEFAULT.verticalSpeed = Double.MAX_VALUE;
        }

        public Long id;
        public Long flightId;
        // Time
        public long simulationTimeStamp;
        public long measurementTimeStamp;

        // Aircraft type
        private String aircraftTypeName;
        private String aircraftCode;

        // Position and orientation
        private double latitude;
        private double longitude;
        private double heading;
        private double altitude;

        // Speed
        private double verticalSpeed;

        public Builder() {
            id = DEFAULT.id;
            flightId = DEFAULT.flightId;
            simulationTimeStamp = DEFAULT.simulationTimeStamp;
            measurementTimeStamp = DEFAULT.measurementTimeStamp;
            aircraftTypeName = DEFAULT.aircraftTypeName;
            aircraftCode = DEFAULT.aircraftCode;
            latitude = DEFAULT.latitude;
            longitude = DEFAULT.longitude;
            heading = DEFAULT.heading;
            altitude = DEFAULT.altitude;
            verticalSpeed = DEFAULT.verticalSpeed;
        }

        private Builder(boolean noInit) {
        }

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

        public Builder flightId(Long flightId) {
            this.flightId = flightId;
            return this;
        }

        public Builder Id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Build a Flightsnapshot object.
         *
         * @return The FlightSnapshot object. If all its values are default returns null object.
         */
        public FlightSnapshot build() {
            // Avoid Object creation if
            FlightSnapshot x = new FlightSnapshot(this);
            if (x.isDefault())
                return null;
            else return x;
        }
    }
}
