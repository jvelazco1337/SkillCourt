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

import java.io.*;
import java.net.*;

import java.util.Properties;

public class MainScreen extends AppCompatActivity
{
    Button myButton;

    static Socket clientSocket;
    static String homeIp="10.109.153.8"; //internal ip of server (aka Pi in this case)
    static int port=5454;
    String response;

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
        buttonSend0();
        buttonSend1();
        buttonSend4();
        startButton();
        highScoresButton();
        devicesButton();



    }

    public void buttonSend0()
    {
        myButton = (Button) findViewById(R.id.buttonSend0);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        try {
                            Socket socket = new Socket(homeIp, port);
                            InputStream inputStream = socket.getInputStream();
                            ByteArrayOutputStream byteArrayOutputStream =
                                    new ByteArrayOutputStream(1024);
                            byte[] buffer = new byte[1024];

                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1){
                                byteArrayOutputStream.write(buffer, 0, bytesRead);
                            }

                            socket.close();
                            response = byteArrayOutputStream.toString("UTF-8");

                        } catch (UnknownHostException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void buttonSend1()
    {
        myButton = (Button) findViewById(R.id.buttonSend1);
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
                            clientSocket = new Socket(homeIp, port);
                            PrintStream out = new PrintStream(clientSocket.getOutputStream());
                            out.println("1");
                        } catch (UnknownHostException e)
                        {
                            //Log.e("aaa","Don't know about host: "+homeIp+"."+e.getMessage());
                            System.out.println("Don't know about host: "+homeIp+"."+e.getMessage());

                            //System.exit(1);
                        } catch (IOException e)
                        {
                            //Log.e("aaa","Couldn't get I/O for the connection to:         "+homeIp+"."+e.getMessage());
                            System.out.println("Couldn't get I/O for the connection to: "+homeIp+"."+e.getMessage());

                            //System.exit(1);
                        }

                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void buttonSend4()
    {
        myButton = (Button) findViewById(R.id.buttonSend4);
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
                            clientSocket = new Socket(homeIp, port);
                            PrintStream out = new PrintStream(clientSocket.getOutputStream());
                            out.println("4");
                        } catch (UnknownHostException e)
                        {
                            //Log.e("aaa","Don't know about host: "+homeIp+"."+e.getMessage());
                            System.out.println("Don't know about host: "+homeIp+"."+e.getMessage());

                            //System.exit(1);
                        } catch (IOException e)
                        {
                            //Log.e("aaa","Couldn't get I/O for the connection to:         "+homeIp+"."+e.getMessage());
                            System.out.println("Couldn't get I/O for the connection to: "+homeIp+"."+e.getMessage());

                            //System.exit(1);
                        }

                        return null;
                    }
                }.execute(1);
            }
        });
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
