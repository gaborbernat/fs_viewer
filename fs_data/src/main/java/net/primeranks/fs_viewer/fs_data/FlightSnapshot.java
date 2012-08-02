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
package net.primeranks.fs_viewer.fs_data;

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
public class FlightSnapshot extends Flight {
    @Id
    @XmlAttribute
    protected Long id;
    @XmlAttribute
    protected Long flightId;
    // Time
    @XmlAttribute
    protected Long simulationTimeStamp;
    @XmlAttribute
    protected Long measurementTimeStamp;

    // Aircraft type
    protected String aircraftTypeName;
    protected String aircraftCode;

    // Position and orientation
    protected Double latitude;
    protected Double longitude;
    protected Integer heading;
    protected Integer altitude;
    // Speed
    protected Integer verticalSpeed;

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

    private void setValues(Long id, Long flightId, Long simulationTimeStamp, Long measurementTimeStamp, String aircraftTypeName,
                           String aircraftCode, Double latitude, Double longitude,
                           Integer heading, Integer altitude, Integer verticalSpeed) {
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

    private Object applyDiff(Object what, Object prev, Object def) {
        Object r;
        if (what == null && prev == null)
            r = def;
        else if (what == null)
            r = prev;
        else if (prev == null) {
            r = what;
        } else if (what.equals(prev)) {
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
                //.measurementTimeStamp(this.measurementTimeStamp)
                .simulationTimeStamp((Long) applyDiff(this.simulationTimeStamp, x.simulationTimeStamp, DEFAULT.simulationTimeStamp))
                .latitude((Double) applyDiff(this.latitude, x.latitude, DEFAULT.latitude))
                .longitude((Double) applyDiff(this.longitude, x.longitude, DEFAULT.longitude))
                .heading((Integer) applyDiff(this.heading, x.heading, DEFAULT.heading))
                .altitude((Integer) applyDiff(this.altitude, x.altitude, DEFAULT.altitude))
                .verticalSpeed((Integer) applyDiff(this.verticalSpeed, x.verticalSpeed, DEFAULT.verticalSpeed))
                .aircraftCode((String) applyDiff(this.aircraftCode, x.aircraftCode, DEFAULT.aircraftCode))
                .aircraftTypeName((String) applyDiff(this.aircraftTypeName, x.aircraftTypeName, DEFAULT.aircraftTypeName))
                        // These are not taking part in the difference
                        //.Id((Long) applyDiff(this.id, x.id, DEFAULT.id))
                .flightId(this.flightId)
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

        return !(aircraftCode != null ? !aircraftCode.equals(that.aircraftCode) : that.aircraftCode != null) &&
                !(aircraftTypeName != null ? !aircraftTypeName.equals(that.aircraftTypeName) : that.aircraftTypeName != null) &&
                !(altitude != null ? !altitude.equals(that.altitude) : that.altitude != null) &&
                !(heading != null ? !heading.equals(that.heading) : that.heading != null) &&
                !(latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) &&
                !(longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) &&
                !(simulationTimeStamp != null ? !simulationTimeStamp.equals(that.simulationTimeStamp) : that.simulationTimeStamp != null) &&
                !(verticalSpeed != null ? !verticalSpeed.equals(that.verticalSpeed) : that.verticalSpeed != null);

    }

    @Override
    public int hashCode() {
        int result = simulationTimeStamp != null ? simulationTimeStamp.hashCode() : 0;
        result = 31 * result + (aircraftTypeName != null ? aircraftTypeName.hashCode() : 0);
        result = 31 * result + (aircraftCode != null ? aircraftCode.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (heading != null ? heading.hashCode() : 0);
        result = 31 * result + (altitude != null ? altitude.hashCode() : 0);
        result = 31 * result + (verticalSpeed != null ? verticalSpeed.hashCode() : 0);
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

    public Long getSimulationTimeStamp() {
        return simulationTimeStamp;
    }

    public void setSimulationTimeStamp(Long simulationTimeStamp) {
        this.simulationTimeStamp = simulationTimeStamp;
    }

    public Long getMeasurementTimeStamp() {
        return measurementTimeStamp;
    }

    public void setMeasurementTimeStamp(Long measurementTimeStamp) {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getHeading() {
        return heading;
    }

    public void setHeading(Integer heading) {
        this.heading = heading;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Integer getVerticalSpeed() {
        return verticalSpeed;
    }

    public void setVerticalSpeed(Integer verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public static class Builder {
        public static Builder DEFAULT;

        static {
            DEFAULT = new Builder(true);  // create

            DEFAULT.id = null;        // set
            DEFAULT.flightId = null;

            DEFAULT.simulationTimeStamp = null;
            DEFAULT.measurementTimeStamp = null;
            // Aircraft type
            DEFAULT.aircraftTypeName = null;
            DEFAULT.aircraftCode = null;
            DEFAULT.latitude = null;
            DEFAULT.longitude = null;
            DEFAULT.heading = null;
            DEFAULT.altitude = null;
            DEFAULT.verticalSpeed = null;
        }

        public Long id;
        public Long flightId;
        // Time
        public Long simulationTimeStamp;
        public Long measurementTimeStamp;

        // Aircraft type
        private String aircraftTypeName;
        private String aircraftCode;

        // Position and orientation
        private Double latitude;
        private Double longitude;
        private Integer heading;
        private Integer altitude;

        // Speed
        private Integer verticalSpeed;

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

        public Builder simulationTimeStamp(Long simulationTimeStamp) {
            this.simulationTimeStamp = simulationTimeStamp;
            return this;
        }

        public Builder measurementTimeStamp(Long measurementTimeStamp) {
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

        public Builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder heading(Integer heading) {
            this.heading = heading;
            return this;
        }

        public Builder altitude(Integer altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder verticalSpeed(Integer verticalSpeed) {
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
