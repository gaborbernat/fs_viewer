/*
 * FlightSnapshotJAXBAdapter.java ->
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

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FlightSnapshotJAXBAdapter extends XmlAdapter<FlightSnapshotJAXBAdapted, FlightSnapshot> {

    /**
     * Convert a value type to a bound type.
     *
     * @param a The value to be converted. Can be null.
     * @throws Exception if there's an error during the conversion. The caller is responsible for
     *                   reporting the error to the user through {@link javax.xml.bind.ValidationEventHandler}.
     */
    @Override
    public FlightSnapshot unmarshal(FlightSnapshotJAXBAdapted a) throws Exception {
        return new FlightSnapshot.Builder()
                .measurementTimeStamp(a.getMeasurementTimeStamp())
                .simulationTimeStamp(a.getSimulationTimeStamp())
                .aircraftTypeName(a.getAircraftTypeName())
                .aircraftCode(a.getAircraftCode())
                .altitude(a.getAltitude())
                .heading(a.getHeading())
                .longitude(a.getLongitude())
                .latitude(a.getLatitude())
                .verticalSpeed(a.getVerticalSpeed())
                .build();


    }

    @Override
    public FlightSnapshotJAXBAdapted marshal(FlightSnapshot f) throws Exception {

        FlightSnapshotJAXBAdapted a = new FlightSnapshotJAXBAdapted();
        a.setMeasurementTimeStamp(f.measurementTimeStamp());
        a.setSimulationTimeStamp(f.simulationTimeStamp());
        a.setAircraftCode(f.aircraftCode());
        a.setAircraftTypeName(f.aircraftTypeName());
        a.setAltitude(f.altitude());
        a.setHeading(f.heading());
        a.setLongitude(f.longitude());
        a.setLatitude(f.latitude());
        a.setVerticalSpeed(f.verticalSpeed());
        return a;

    }

}
