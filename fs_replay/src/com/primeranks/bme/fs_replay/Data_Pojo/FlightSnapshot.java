/*
 * FlightSnapshot.java ->
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
 * You should have received a copy of the GNU General Public License aLong with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.primeranks.bme.fs_replay.Data_Pojo;

public class FlightSnapshot {
    protected Long id;
    protected Long flightId;
    // Time
    protected Long simulationTimeStamp;
    protected Long measurementTimeStamp;

    // Aircraft type
    protected String aircraftTypeName;
    protected String aircraftCode;

    // Position and orientation
    protected Double latitude;
    protected Double Longitude;
    protected Double heading;
    protected Double altitude;
    // Speed
    protected Double verticalSpeed;

    public static FlightSnapshot DEFAULT = Builder.DEFAULT.build();
    public static FlightSnapshot OFFSET = Builder.OFFSET.build();

    public FlightSnapshot(Builder b) {
        setValues(b);
    }

    public FlightSnapshot() {
        setValues(Builder.DEFAULT);
    }


    private void setValues(Builder b) {
        setValues(b.id, b.flightId, b.simulationTimeStamp, b.measurementTimeStamp, b.aircraftTypeName,
                b.aircraftCode, b.latitude, b.Longitude, b.heading, b.altitude, b.verticalSpeed);
    }

    private void setValues(Long id, Long flightId, Long simulationTimeStamp, Long measurementTimeStamp, String aircraftTypeName,
                           String aircraftCode, Double latitude, Double Longitude,
                           Double heading, Double altitude, Double verticalSpeed) {
        this.id = id;
        this.flightId = flightId;
        this.simulationTimeStamp = simulationTimeStamp;
        this.measurementTimeStamp = measurementTimeStamp;
        this.aircraftTypeName = aircraftTypeName;
        this.aircraftCode = aircraftCode;
        this.latitude = latitude;
        this.Longitude = Longitude;
        this.heading = heading;
        this.altitude = altitude;
        this.verticalSpeed = verticalSpeed;

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
        return Longitude;
    }

    public void setLongitude(Double Longitude) {
        this.Longitude = Longitude;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getVerticalSpeed() {
        return verticalSpeed;
    }

    public void setVerticalSpeed(Double verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public static class Builder {
        public static Builder DEFAULT;
        public static Builder OFFSET;

        static {
            DEFAULT = new Builder(true);  // create

            DEFAULT.id = null;        // set
            DEFAULT.flightId = null;

            DEFAULT.simulationTimeStamp = new Long(0);
            DEFAULT.measurementTimeStamp = new Long(0);
            // Aircraft type
            DEFAULT.aircraftTypeName = null;
            DEFAULT.aircraftCode = null;
            DEFAULT.latitude = Double.MAX_VALUE;
            DEFAULT.Longitude = Double.MAX_VALUE;
            DEFAULT.heading = Double.MAX_VALUE;
            DEFAULT.altitude = Double.MAX_VALUE;
            DEFAULT.verticalSpeed = Double.MAX_VALUE;

            // The offset values
            OFFSET = new Builder(true);
            OFFSET.latitude = 1E-3;
            OFFSET.Longitude = 1E-3;
            OFFSET.heading = 1E-1;
            OFFSET.altitude = 1.0;
            DEFAULT.verticalSpeed = 1.0;
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
        private Double Longitude;
        private Double heading;
        private Double altitude;

        // Speed
        private Double verticalSpeed;

        public Builder() {
            id = DEFAULT.id;
            flightId = DEFAULT.flightId;
            simulationTimeStamp = DEFAULT.simulationTimeStamp;
            measurementTimeStamp = DEFAULT.measurementTimeStamp;
            aircraftTypeName = DEFAULT.aircraftTypeName;
            aircraftCode = DEFAULT.aircraftCode;
            latitude = DEFAULT.latitude;
            Longitude = DEFAULT.Longitude;
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

        public Builder heading(Double heading) {
            this.heading = heading;
            return this;
        }

        public Builder altitude(Double altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder longitude(Double Longitude) {
            this.Longitude = Longitude;
            return this;
        }

        public Builder verticalSpeed(Double verticalSpeed) {
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
            return x;
        }
    }
}
