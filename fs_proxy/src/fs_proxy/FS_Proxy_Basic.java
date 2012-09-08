/*
 * FS_Proxy_Basic.java ->
 * Copyright (C) 2012-05-10 Gábor Bernát
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

package fs_proxy;

import com.flightsim.fsuipc.FSAircraft;
import com.flightsim.fsuipc.FSUIPC;
import com.flightsim.fsuipc.fsuipc_wrapper;
import net.primeranks.fs_data.Flight;
import net.primeranks.fs_data.FlightSnapshot;
import net.primeranks.fs_data.User;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FS_Proxy_Basic implements FS_Proxy_I {
    static Logger log = Logger.getLogger(Config.class.getName());
    static Long ZERO = new Long(0);

    private int refreshInterval;
    private Flight flight;
    private User user;

    private final Dao dao;
    private FSUIPC general;
    private FSAircraft aircraft;

    private Timer t;

    @Inject
    FS_Proxy_Basic(Dao s) {
        dao = s;
    }


    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    private enum FSInformation {
        AIRCRAFT_TYPE_NAME(0X3160, 24, Type.STRING),
        AIRCRAFT_CODE(0X3500, 24, Type.STRING),
        SIMULATOR_LOCAL_HOUR(0X0238, 1, Type.BYTE),
        SIMULATOR_LOCAL_MINUTE(0X0239, 1, Type.BYTE),
        SIMULATOR_SECOND(0X023A, 1, Type.BYTE),
        SIMULATOR_ZULU_HOUR(0X023B, 1, Type.BYTE),
        SIMULATOR_ZULU_MINUTE(0X023C, 1, Type.BYTE),
        SIMULATOR_YEAR(0X0240, 2, Type.SHORT),
        SIMULATOR_DAY_NR_IN_YEAR(0X023E, 2, Type.SHORT),
        IN_MENU(3365, 1, Type.BYTE); // zero if in game

        private enum Type {
            BYTE, SHORT, INT, LONG, DOUBLE, STRING
        }

        private final int offset;
        private final int size;
        private final Type type;

        FSInformation(int offset, int size, Type type) {
            this.offset = offset;
            this.size = size;
            this.type = type;
        }

        public int offset() {
            return this.offset;
        }

        public int size() {
            return this.size;
        }

        public Type type() {
            return this.type;
        }

    }

    private boolean init() {
        log.log(Level.FINE, "Starting fs_proxy.");
        log.log(Level.FINE, "The current class path is (looking for DLL from here): "
                + new java.io.File(".").getAbsolutePath());
        log.log(Level.FINE, "Looking for the DLL.");

        user = new User();
        user.setDomain(Util.getDomain());
        user.setName(Util.getUserName());
        log.log(Level.INFO, "Local UserName: " + user.toString());

        user.setId(dao.getUserID(user));
        if (user.getId() == null || user.getId().equals(User.INVALID_ID)) {
            log.log(Level.INFO, "User not found on server. Creating it.");
            user.setId(dao.createUser(user));
        }
        log.log(Level.INFO, "Server UserName: " + user.toString());

        return user.getId() != null && !user.getId().equals(User.INVALID_ID);
    }

    private Object readFromGeneral(FSInformation x) {
        Object r = null;
        switch (x.type()) {
            case BYTE:
                r = general.getByte(x.offset());
                break;
            case SHORT:
                r = general.getShort(x.offset());
                break;
            case INT:
                r = general.getInt(x.offset());
                break;
            case LONG:
                r = general.getLong(x.offset());
                break;
            case DOUBLE:
                r = general.getDouble(x.offset());
                break;
            case STRING:
                r = general.getString(x.offset(), x.size()).trim();
                break;
        }

        return r;
    }

    private FlightSnapshot readSnapShot() {
        Long simulationTimeStamp = FlightSnapshot.DEFAULT.getSimulationTimeStamp();
        Short y = (Short) readFromGeneral(FSInformation.SIMULATOR_YEAR);
        Short d = (Short) readFromGeneral(FSInformation.SIMULATOR_DAY_NR_IN_YEAR);
        Byte h = (Byte) readFromGeneral(FSInformation.SIMULATOR_ZULU_HOUR);
        Byte m = (Byte) readFromGeneral(FSInformation.SIMULATOR_ZULU_MINUTE);
        Byte s = (Byte) readFromGeneral(FSInformation.SIMULATOR_SECOND);

        try {
            Date x = new SimpleDateFormat("y-D k:m:s z").parse(String.format("%d-%d %d:%d:%d UTC", y, d - 1, h, m, s));
            simulationTimeStamp = x.getTime();
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Reading the simulation timestamp failed:" + e.getMessage());
        }

        return new FlightSnapshot.Builder()
                // .id() -> Server side generated - irrelevant here
                .flightId(flight.getId())
                .measurementTimeStamp(System.currentTimeMillis())
                .simulationTimeStamp(simulationTimeStamp)
                .aircraftTypeName((String) readFromGeneral(FSInformation.AIRCRAFT_TYPE_NAME))
                .aircraftCode((String) readFromGeneral(FSInformation.AIRCRAFT_CODE))
                .altitude(aircraft.Altitude())
                .heading(aircraft.Heading())
                .longitude(aircraft.Longitude())
                .latitude(aircraft.Latitude())
                .verticalSpeed(aircraft.VerticalSpeed())
                .build();
    }

    public void run() {
        run(this.refreshInterval);
    }

    @Override
    public void run(int refreshInterval) {
        this.refreshInterval = refreshInterval;

        if (!init()) {
            log.log(Level.SEVERE, "The initialization failed. Stopping.");
            return;
        }

        general = new FSUIPC();
        aircraft = new FSAircraft();
        t = new Timer();

        tryToConnectToFSUIPC = new TryToConnectToFSUIPC();
        t.scheduleAtFixedRate(tryToConnectToFSUIPC, 0, refreshInterval);
    }

    private TryToConnectToFSUIPC tryToConnectToFSUIPC;

    class TryToConnectToFSUIPC extends TimerTask {
        @Override
        public void run() {
            if (fsuipc_wrapper.Open(fsuipc_wrapper.SIM_ANY) != 0) {
                tryToTakeMeasurement = new TryToTakeMeasurement();
                t.scheduleAtFixedRate(tryToTakeMeasurement, 0, refreshInterval);
                tryToConnectToFSUIPC.cancel();

                // Create a new flight
                flight = new Flight();
                flight.setUserId(user.getId());
                flight.setStartDate(System.currentTimeMillis());
                flight.setPeriodicity(refreshInterval);
                flight = dao.addFlight(flight);                 // Create it server side
                log.log(Level.INFO, "Started flight at: " + flight.getStartDate());
                return;
            }
            log.log(Level.INFO, new SimpleDateFormat("HH:mm:ss ").format(new Date()) + "FSUIPC not found. Retrying later.");
        }
    }

    private TryToTakeMeasurement tryToTakeMeasurement;

    class TryToTakeMeasurement extends TimerTask {
        FlightSnapshot current, previous = FlightSnapshot.DEFAULT, diff;

        @Override
        public void run() {
            fsuipc_wrapper.Close();
            if (fsuipc_wrapper.Open(fsuipc_wrapper.SIM_ANY) == 0) {
                // Start trying to connect
                tryToTakeMeasurement.cancel();
                tryToConnectToFSUIPC = new TryToConnectToFSUIPC();
                t.scheduleAtFixedRate(tryToConnectToFSUIPC, 0, refreshInterval);

                // Update flight details
                flight.setEndDate(System.currentTimeMillis());
                dao.addFlight(flight);
                log.log(Level.INFO, "Finished flight at: " + flight.getEndDate());
                return;
            }

            current = readSnapShot();
            diff = current.differenceFrom(previous);
            if (diff != null && !diff.isDefault() &&
                    !current.getSimulationTimeStamp().equals(previous.getSimulationTimeStamp())
                    && diff.getSimulationTimeStamp().compareTo(ZERO) > 0) {
                diff.setMeasurementTimeStamp(current.getMeasurementTimeStamp());
                dao.addSnapshotToFlight(diff);
                log.log(Level.FINE, "Measurement: " + previous);
                log.log(Level.INFO, "Diff:        " + diff);
                previous = current;
            }

        }
    }
}
