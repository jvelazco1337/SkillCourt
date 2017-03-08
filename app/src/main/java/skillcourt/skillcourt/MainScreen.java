package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class MainScreen extends AppCompatActivity
{
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        try
        {
            testButton();
        } catch (JSchException e)
        {
            e.printStackTrace();
        }
        startButton();
        highScoresButton();
        devicesButton();






    }

    public void testButton() throws JSchException
    {
        // Button initialization
        Button test = (Button) findViewById(R.id.testButton);
        test.setOnClickListener(new View.OnClickListener()
        {
            RaspberryPi rasp1 = new RaspberryPi("pi", "raspberry", "192.168.43.99", 22);

            @Override
            public void onClick(View view)
            {
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        // new JSch object
                        JSch javaSecure = new JSch();

                        // RaspberryPi object info
                        String raspUser= rasp1.getUsername();
                        String raspPass= rasp1.getPassword();
                        String raspIp= rasp1.getIp();
                        int raspPort= rasp1.getPort();

                        // Session initiation
                        Session session = null;
                        try
                        {
                            session = javaSecure.getSession(raspUser, raspIp, raspPort);
                        } catch (JSchException e)
                        {
                            e.printStackTrace();
                        }
                        session.setPassword(raspPass);



                        Properties config = new Properties();
                        config.put("StrictHostKeyChecking", "no");
                        session.setConfig(config);

                        // connect to pi with given settings
                        try
                        {
                            session.connect();
                        } catch (JSchException e)
                        {
                            e.printStackTrace();
                        }


                        // new Channel object, and from my understanding the "exec" is to run the terminal
                        Channel channel = null;
                        try
                        {
                            channel = session.openChannel("exec");
                        } catch (JSchException e)
                        {
                            e.printStackTrace();
                        }
                        ChannelExec channelEX = (ChannelExec) channel;

                        // methods to connect and use the command in the terminal
                        channelEX.setCommand("hi");
                        try
                        {
                            channelEX.connect();
                        } catch (JSchException e)
                        {
                            e.printStackTrace();
                        }
                        channelEX.setErrStream(System.err);


                        // disconnect the session and the channel
                        channelEX.disconnect();
                        session.disconnect();

                        // confirmation print
                        System.out.println("Exit code: " + channelEX.getExitStatus());
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void startButton()
    {
        myButton = (Button) findViewById(R.id.addButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, StartScreen.class);
                startActivity(intent);
            }
        });
    }

    public void highScoresButton()
    {
        myButton = (Button) findViewById(R.id.removeButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, HighScoresScreen.class);
                startActivity(intent);
            }
        });
    }

    public void devicesButton()
    {
        myButton = (Button) findViewById(R.id.devicesButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, DevicesScreen.class);
                startActivity(intent);
            }
        });
    }


}
