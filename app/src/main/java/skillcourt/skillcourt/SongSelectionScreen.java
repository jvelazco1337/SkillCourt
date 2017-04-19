package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by jvelazco1337 on 4/17/17.
 */

public class SongSelectionScreen extends AppCompatActivity
{
    Button myButton;
    EditText myEditText;
    String song;
    String artist;
    String javiIphoneIp = "172.20.10.12";
    String javiHouseIp = "192.168.0.16";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_selection_screen);

        backButton();
        connectButton2();

    }


    public void backButton()
    {
        myButton = (Button) findViewById(R.id.backButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(SongSelectionScreen.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

    public void connectButton2()
    {
        myButton = (Button) findViewById(R.id.connectButton2);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                getSong();
                getArtist();
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {

                        /*try
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
                            channelssh.setCommand("python3 /home/pi/SkillCourtpi/skillpi/test2.py");
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

                        /*System.out.println("Setting up");
                        try
                        {
                            getSong();
                            getArtist();
                            Socket soc = new Socket(javiHouseIp, 9997);
                            DataOutputStream dout = new DataOutputStream(soc.getOutputStream());
                            dout.writeUTF(artist + " " + song);
                            dout.flush();
                            dout.close();
                            soc.close();
                            System.out.println("Sent song: " + song + " by " + artist);

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }*/
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void getSong()
    {
        myEditText = (EditText) findViewById(R.id.songEditText);
        song = myEditText.getText().toString();
    }

    public void getArtist()
    {
        myEditText = (EditText) findViewById(R.id.artistEditText);
        artist = myEditText.getText().toString();
    }
}
