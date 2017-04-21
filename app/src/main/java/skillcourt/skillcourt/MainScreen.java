package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Properties;

public class MainScreen extends AppCompatActivity
{
    Button myButton;
    ImageButton myImageButton;

    String username = "pi";
    String password = "raspberry";
    String javiIphoneIp = "172.20.10.12";
    String javiHouseIp = "192.168.0.16";
    String rijPiFiuIp = "10.109.190.28";
    String pabloPiFiuIp = "10.109.153.8";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        startButton();
        songButton();
        shutdownPiButton();
        rebootPiButton();
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

                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        try
                        {
                            System.out.println("Setting up");
                            JSch jsch = new JSch();
                            Session session = jsch.getSession(username, pabloPiFiuIp, 22);
                            session.setPassword(password);

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
                            channelssh.setCommand("sudo /home/pi/SCFS/./audioFinal");
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

    public void shutdownPiButton()
    {
        myImageButton = (ImageButton) findViewById(R.id.shutdownPiButton);
        myImageButton.setOnClickListener(new View.OnClickListener()
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
                            Session session = jsch.getSession(username, pabloPiFiuIp, 22);
                            session.setPassword(password);

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
                            channelssh.setCommand("sudo shutdown -h now");
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

    public void rebootPiButton()
    {
        myImageButton = (ImageButton) findViewById(R.id.rebootPiButton);
        myImageButton.setOnClickListener(new View.OnClickListener()
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
                            Session session = jsch.getSession(username, pabloPiFiuIp, 22);
                            session.setPassword(password);

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
                            channelssh.setCommand("sudo reboot");
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


}
