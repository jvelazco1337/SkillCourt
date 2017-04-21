package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;


public class StartScreen extends AppCompatActivity
{
    Button myButton;
    ImageButton myImageButton;

    static Socket clientSocket;
    OutputStream myOutStream = null;

    static int port = 3024;
    int[] seq = new int[6];
    int counter= 0;
    boolean pauseSequence = false;
    final int OFF = 6;

    String username = "pi";
    String password = "raspberry";
    String javiIphoneIp = "172.20.10.12";
    String javiHouseIp = "192.168.0.16";
    String rijPiFiuIp = "10.109.190.28";
    String pabloPiFiuIp = "10.109.153.8";
    String pabloPhoneIp = "192.168.43.56";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        backButton();
        connectButton();
        greenButton();
        pauseButton();
        blueButton();
        disconnectButton();
    }


    public void backButton()
    {
        myImageButton = (ImageButton) findViewById(R.id.backButton);
        myImageButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(StartScreen.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }



    public void blueButton()
    {
        myButton = (Button) findViewById(R.id.blueButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {

                        counter = 0;
                        pauseSequence = false;
                        while(counter <= seq.length - 1)
                        {

                            try
                            {
                                myOutStream.write(seq[counter]);

                                System.out.println("Sending " + seq[counter]);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                            System.out.println("Sent Sequence");

                            try
                            {
                                Thread.sleep(1500); //switch every 1.5 seconds
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }

                            if (counter == seq.length - 1)
                            {
                                counter = 0;
                            }
                            else if (pauseSequence == true)
                            {
                                break;
                            }
                            else
                            {
                                counter++;
                            }
                        }
                        return null;
                    }
                }.execute(1);
            }
        });
    }


    public void connectButton()
    {
        myButton = (Button) findViewById(R.id.connectButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        System.out.println("Setting up");

                        //Exception required here
                        try
                        {
                            clientSocket = new Socket(pabloPiFiuIp, port);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        System.out.println("Connected to server");

                        try
                        {
                            myOutStream = clientSocket.getOutputStream();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void disconnectButton()
    {
        myButton = (Button) findViewById(R.id.disconnectButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        try
                        {
                            myOutStream.write(OFF);
                            myOutStream.close();
                            clientSocket.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        System.out.println("Disconnected Successfully");
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void greenButton()
    {
        myButton = (Button) findViewById(R.id.setSeqButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                seq[0] = 1;
                seq[1] = 0;
                seq[2] = 1;
                seq[3] = 0;
                seq[4] = 1;
                seq[5] = 0;

                System.out.println("Sequence Assigned");
            }
        });
    }

    public void pauseButton()
    {
        myButton = (Button) findViewById(R.id.pauseButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                pauseSequence = true;
                System.out.println("Pausing Sequence");
            }
        });

    }


}
