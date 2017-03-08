package skillcourt.skillcourt;

import java.util.ArrayList;

/**
 * Created by jvela on 3/3/2017.
 */

public class RaspberryPiManager
{
    public ArrayList<RaspberryPi> myList;

    public RaspberryPiManager()
    {
        myList = new ArrayList<>();
    }


    public void addPi(RaspberryPi rasp1)
    {
        myList.add(rasp1);
    }

    public void removePi(int one)
    {
        myList.remove(one);
    }

    public void getPi(int one)
    {
        myList.get(one);
    }

}
