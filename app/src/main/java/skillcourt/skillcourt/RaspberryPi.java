package skillcourt.skillcourt;

import java.util.ArrayList;

/**
 * Created by jvela on 3/1/2017.
 */

public class RaspberryPi
{
    private String username, password, ip;
    private int port;
    private ArrayList<RaspberryPi> myRaspPi = new ArrayList<>();

    /**
     * Creates Raspberry Pi object
     * @param username Username for Raspberry Pi
     * @param password Password for Raspberry Pi
     * @param ip IP for Raspberry Pi
     * @param port Port for for Raspberry Pi
     */
    public RaspberryPi(String username, String password, String ip, int port)
    {
        this.username = username;
        this.password = password;
        this. ip = ip;
        this.port = port;
    }

    /**
     * Returns the username of a Raspberry Pi
     * @return the username of a Raspberry Pi
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Returns the password of a Raspberry Pi
     * @return the password of a Raspberry Pi
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Returns the IP of a Raspberry Pi
     * @return the IP of a Raspberry Pi
     */
    public String getIp()
    {
        return ip;
    }

    /**
     * Returns the port of a Raspberry Pi
     * @return the port of a Raspberry Pi
     */
    public int getPort()
    {
        return port;
    }





    public void addPi(RaspberryPi rasp1)
    {
        myRaspPi.add(rasp1);
    }

}
