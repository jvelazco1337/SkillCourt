package skillcourt.skillcourt;


// Imports being used
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


/**
 * Main Screen class has 4 buttons, Start, Song Selection, Shutdown and Reboot.
 * This is the main screen seen on the app after the splash screen comes up.
 */
public class MainScreen extends AppCompatActivity
{
    // Button and Image Button declaration to be used later
    Button myButton;
    ImageButton myImageButton;

    // Variables to store the Raspberry Pi information
    // for SSH and/or Socket communication
    String username = "pi";                 // Default username for Pi for SSH
    String password = "raspberry";          // Default password for Pi for SSH
    String raspIP = "172.20.10.12";         // Raspberry Pi IP


    /**
     * Built in method from Android Studio.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        // Buttons to be used in the APP
        startButton();
        songButton();
        shutdownPiButton();
        rebootPiButton();
    }

    /**
     * The startButton method will be used to automatically call the C program (audioFinal)
     * via SSH and let the C program wait for a connection from the APP. The start button will
     * also navigate from the Main Screen to the Start Screen in the APP.
     */
    public void startButton()
    {
        myButton = (Button) findViewById(R.id.startButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Intent to move from Main to Start Screen
                Intent intent = new Intent(MainScreen.this, StartScreen.class);
                startActivity(intent);

                // Async method to be able to run Networking in the background
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        // Try statement that will connect to the Pi via SSH and send the
                        // command to start the C program. To SSH we use the JSch Library
                        // that we added manually, the link to the library will also be on Github
                        try
                        {
                            System.out.println("Setting up");                           // Print to show the start of the SSH connection
                            JSch jsch = new JSch();                                     // New JSch object
                            Session session = jsch.getSession(username, raspIP, 22);    // New Session object, this connects to the Pi using the shown settings
                            session.setPassword(password);                              // Sets the password for the session created above

                            // Avoid asking for key confirmation
                            Properties prop = new Properties();
                            prop.put("StrictHostKeyChecking", "no");
                            session.setConfig(prop);

                            session.connect();                                          // Connect to Pi
                            System.out.println("Connected");                            // Print to show the connection was completed

                            // SSH Channel
                            ChannelExec channelssh = (ChannelExec)                      // New ChannelExec object, this runs the terminal within the Pi
                                     session.openChannel("exec");
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();   // Output stream object to be able to write to the Pi
                            channelssh.setOutputStream(baos);

                            // Execute command
                            channelssh.setCommand("sudo /home/pi/SCFS/./audioFinal");   // The exact command to be written in the Pi's terminal via SSH
                            System.out.println("Command Sent");                         // Print to show that the command was sent
                            channelssh.connect();                                       // Opening of the channel
                            channelssh.disconnect();                                    // Closing of the channel
                            System.out.println("Closing");                              // Print to show progress of closing
                            session.disconnect();                                       // Closing of the session
                            System.out.println("Closed");                               // Print to show the session closed

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

    /**
     * The songButton method will navigate from the main screen to the song selection screen
     */
    public void songButton()
    {
        myButton = (Button) findViewById(R.id.songButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Intent to move from Main to Song Selection Screen
                Intent intent = new Intent(MainScreen.this, SongSelectionScreen.class);
                startActivity(intent);
            }
        });


    }

    /**
     * The shutdownPiButton method will be use to shutdown the Pi via SSH
     */
    public void shutdownPiButton()
    {
        myImageButton = (ImageButton) findViewById(R.id.shutdownPiButton);
        myImageButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Async method to be able to run Networking in the background
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        // Try statement to shutdown the Pi via SSH
                        try
                        {
                            System.out.println("Setting up");                           // Print to show the start of the SSH connection
                            JSch jsch = new JSch();                                     // New JSch object
                            Session session = jsch.getSession(username, raspIP, 22);    // New Session object, this connects to the Pi using the shown settings
                            session.setPassword(password);                              // Sets the password for the session created above

                            // Avoid asking for key confirmation
                            Properties prop = new Properties();
                            prop.put("StrictHostKeyChecking", "no");
                            session.setConfig(prop);

                            session.connect();                                          // Connect to Pi
                            System.out.println("Connected");                            // Print to show the connection was completed

                            // SSH Channel
                            ChannelExec channelssh = (ChannelExec)                      // New ChannelExec object, this runs the terminal within the Pi
                                    session.openChannel("exec");
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();   // Output stream object to be able to write to the Pi
                            channelssh.setOutputStream(baos);

                            // Execute command
                            channelssh.setCommand("sudo shutdown -h now");              // The exact command to be written in the Pi's terminal via SSH
                            System.out.println("Command Sent");                         // Print to show that the command was sent
                            channelssh.connect();                                       // Opening of the channel
                            channelssh.disconnect();                                    // Closing of the channel
                            System.out.println("Closing");                              // Print to show progress of closing
                            session.disconnect();                                       // Closing of the session
                            System.out.println("Closed");                               // Print to show the session closed
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
                // Async method to be able to run Networking in the background
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        try
                        {
                            System.out.println("Setting up");                           // Print to show the start of the SSH connection
                            JSch jsch = new JSch();                                     // New JSch object
                            Session session = jsch.getSession(username, raspIP, 22);    // New Session object, this connects to the Pi using the shown settings
                            session.setPassword(password);                              // Sets the password for the session created above

                            // Avoid asking for key confirmation
                            Properties prop = new Properties();
                            prop.put("StrictHostKeyChecking", "no");
                            session.setConfig(prop);

                            session.connect();                                          // Connect to Pi
                            System.out.println("Connected");                            // Print to show the connection was completed

                            // SSH Channel
                            ChannelExec channelssh = (ChannelExec)                      // New ChannelExec object, this runs the terminal within the Pi
                                    session.openChannel("exec");
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();   // Output stream object to be able to write to the Pi
                            channelssh.setOutputStream(baos);

                            // Execute command
                            channelssh.setCommand("sudo reboot");                       // The exact command to be written in the Pi's terminal via SSH
                            System.out.println("Command Sent");                         // Print to show that the command was sent
                            channelssh.connect();                                       // Opening of the channel
                            channelssh.disconnect();                                    // Closing of the channel
                            System.out.println("Closing");                              // Print to show progress of closing
                            session.disconnect();                                       // Closing of the session
                            System.out.println("Closed");                               // Print to show the session closed

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
