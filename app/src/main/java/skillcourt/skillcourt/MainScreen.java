package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;
import java.net.*;
import java.util.Properties;

public class MainScreen extends AppCompatActivity
{
    Button myButton;

    static Socket clientSocket;
    static String homeIp = "10.109.153.8"; // internal ip of server (aka Pi)
    static int port = 3031;
    OutputStream outputstream;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);


        buttonSend7();
        buttonSend6();
        startButton();
        speedButton();
        songButton();
        connectToPiButton();



    }



    public void buttonSend6()
    {
        myButton = (Button) findViewById(R.id.buttonSend6);
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
                            outputstream.write(6);

                            System.out.println("Sending 6");
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        System.out.println("6 Sent");

                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void buttonSend7()
    {
        myButton = (Button) findViewById(R.id.sendButton7);
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
                            System.out.println("Setting up");
                            JSch jsch = new JSch();
                            Session session = jsch.getSession("pi", "192.168.0.16", 22);
                            session.setPassword("raspberry");

                            // Avoid asking for key confirmation
                            Properties prop = new Properties();
                            prop.put("StrictHostKeyChecking", "no");
                            session.setConfig(prop);

                            session.connect();
                            System.out.println("Connected");
                            // SSH Channel
                            ChannelExec channelssh = (ChannelExec)
                                    session.openChannel("exec");
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            channelssh.setOutputStream(baos);

                            // Execute command
                            channelssh.setCommand("python3 /home/pi/SkillCourtpi/skillpi/test.py");
                            System.out.println("Command Sent");
                            channelssh.connect();
                            channelssh.disconnect();
                            System.out.println("Closing");
                            session.disconnect();
                            System.out.println("Closed");

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }.execute(1);
            }
        });
    }


    public void startButton()
    {
        myButton = (Button) findViewById(R.id.startButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, StartScreen.class);
                startActivity(intent);
            }
        });
    }

    public void speedButton()
    {
        myButton = (Button) findViewById(R.id.speedButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, SpeedScreen.class);
                startActivity(intent);
            }
        });
    }

    public void songButton()
    {
        myButton = (Button) findViewById(R.id.songButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, SongSelectionScreen.class);
                startActivity(intent);

            }
        });


    }

    public void connectToPiButton()
    {
        myButton = (Button) findViewById(R.id.connectToPiButton);
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
                            clientSocket = new Socket(homeIp, port);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        System.out.println("Connected to server");

                        //Exception required here

                        try
                        {
                            outputstream = clientSocket.getOutputStream();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);

            }
        });
    }


}
