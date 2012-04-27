package fs_proxy;

import com.flightsim.fsuipc.FSAircraft;
import com.flightsim.fsuipc.FSUIPC;
import com.flightsim.fsuipc.fsuipc_wrapper;
import com.primeranks.net.fs_data.FlightSnapshot;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: gabor.bernat
 * Date: 4/22/12
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class FS_Proxy {
    private FSUIPC general;
    private FSAircraft aircraft;
    private int refreshInterval;

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
        SIMULATOR_ZULU_MINUTE(0X023A, 1, Type.BYTE),
        SIMULATOR_YEAR(0X0240, 2, Type.SHORT),
        SIMULATOR_DAY_NR_IN_YEAR(0X024E, 2, Type.SHORT);

        private enum Type {
            BYTE, SHORT, INT, LONG, DOUBLE, STRING
        }

        ;

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

    ;

    private static String getUserName() {
        try {
            return System.getProperty("user.name", "NoUserName") + "@" + InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return ";";
    }

    private int init() {
        System.out.println("Starting fs_proxy.");
        System.out.println("The current class path is (looking for DLL from here): " + new java.io.File(".").getAbsolutePath());
        System.out.println("Looking for the DLL.");
        System.out.println("UserName:" + getUserName());

        int ret = fsuipc_wrapper.Open(fsuipc_wrapper.SIM_ANY);
        return ret;
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
        return new FlightSnapshot.Builder()
                .measurementTimeStamp(System.currentTimeMillis() / 1000L)
                .simulationTimeStamp(FlightSnapshot.Builder.TIME_DEFAULT)
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

    public void run(int refreshInterval) {
        this.refreshInterval = refreshInterval;

        if (0 == init()) {
            System.out.println("Flight sim not found. Please make sure the Flight Simulator is running and the DLL is in the path.");
            return;
        }
        general = new FSUIPC();
        aircraft = new FSAircraft();
        FlightSnapshot current, previous = null, diff;
        int i = 0;
        while (true) {
            ++i;
            current = readSnapShot();
            diff = current.differenceFrom(previous);
            if (diff != null && !diff.isDefault()) {
                previous = current;
                System.out.println("" + i + "->" + diff);
            }


            try {
                Thread.sleep(this.refreshInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
