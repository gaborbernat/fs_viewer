package fs_proxy;

import com.flightsim.fsuipc.FSAircraft;
import com.flightsim.fsuipc.FSUIPC;
import com.flightsim.fsuipc.fsuipc_wrapper;


public class Main
{
   static void Aircraft()
    {

    }

    public static void main(String s[])
    {
        System.out.println("Starting fs_proxy.");
        System.out.println("The current class path is (looking for DLL from here): " + new java.io.File(".").getAbsolutePath());
        System.out.println("Looking for the DLL.");

        int ret = fsuipc_wrapper.Open(fsuipc_wrapper.SIM_ANY);
        if(ret == 0 )
        {
            System.out.println("Flight sim not found. Please make sure the Flight Simulator is running and the DLL is in the path.");
        }
        else
        {
            System.out.println("Initializing the flight simulator wrapper returned: " + ret);

            FSUIPC general = new FSUIPC();
            int airPlaneOffset = 0X3160;
            String airPlaneName = general.getString(airPlaneOffset, 24).trim();
            int airPlaneTypeO = 0X3500;
            String airPlaneType = general.getString(airPlaneTypeO, 24).trim();

            System.out.println("Current airplane type is a: \"" + airPlaneName + "-" + airPlaneType  +"\".");

            long unixTime = System.currentTimeMillis() / 1000L; // LogTime
            System.out.println(general.getByte(0X239));         // Simulator Time HH-MM-SS 238-239-23A local
                                                                // HH-MM Zulu   23B-23C-23A
                                                                // YYYY - 0240 (2Byte)
                                                                // Day of year -> 023E (2Byte) - starting from 1

            FSAircraft aircraft = new FSAircraft();
            for (int i=0; i<100; ++i)
            {
                System.out.println("Latitude-Longitude: " + aircraft.Latitude() + "," + aircraft.Longitude());
                System.out.println("Heading:" + 360.0*aircraft.Heading()/(65536.0*65536.0));
                System.out.println("At altitude:" + aircraft.Altitude());
                System.out.println("With a speed of: " + aircraft.VerticalSpeed());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }




        }
    }
}