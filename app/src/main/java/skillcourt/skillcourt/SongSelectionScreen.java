package skillcourt.skillcourt;

// Imports being used
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

/**
 * Song Selection Screen will handle the command to start the python file that takes
 * care of running mpsyt in the Pi.
 */
public class SongSelectionScreen extends AppCompatActivity
{
    // Image Button and EditText declaration to be used later
    ImageButton myImageButton;
    EditText myEditText;

    // Global string variables to hold the input from the edittexts
    String song;
    String artist;

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
        setContentView(R.layout.song_selection_screen);

        // Buttons to be used in the APP
        backButton();
        playSongButton();
        stopSongButton();
    }

    /**
     * The backButton method will navigate from the song selection screen back into the
     * main screen.
     */
    public void backButton()
    {
        myImageButton = (ImageButton) findViewById(R.id.backButton);
        myImageButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Intent to move from Main to Start Screen
                Intent intent = new Intent(SongSelectionScreen.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

    /**
     * The stopSongButton method will execute the command in the Pi's terminal that will
     * kill the omxplayer process along with the mpsyt process, therfore stoping the song.
     * All via SSH.
     */
    public void stopSongButton()
    {
        myImageButton = (ImageButton) findViewById(R.id.stopSongButton);
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
                        // Try statement that will connect to the Pi via SSH and send the
                        // command to kill omxplayer and mpsyt
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
                            channelssh.setCommand("killall mpsyt && killall omxplayer.bin");    // The exact command to be written in the Pi's terminal via SSH
                            System.out.println("Command Sent");                                 // Print to show that the command was sent
                            channelssh.connect();                                               // Opening of the channel
                            channelssh.disconnect();                                            // Closing of the channel
                            System.out.println("Closing");                                      // Print to show progress of closing
                            session.disconnect();                                               // Closing of the session
                            System.out.println("Closed");                                       // Print to show the session closed
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
     * The playSongButton method will execute the command in the Pi's terminal to run the python
     * file that will execute mpsyt. It uses the global artist and song variables to send to Pi
     * via SSH.
     */
    public void playSongButton()
    {
        myImageButton = (ImageButton) findViewById(R.id.playSongButton);
        myImageButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Call the these two methods to assign the values from the EditText to
                // the global variables
                getSong();
                getArtist();

                // Async method to be able to run Networking in the background
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        // Try statement that will connect to the Pi via SSH and send the
                        // command to play the song
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

                            // Execute command, using the global variables
                            channelssh.setCommand("/home/pi/SCFS/audioPlayer.py " + artist + " " + song);   // The exact command to be written in the Pi's terminal via SSH
                            System.out.println("Command Sent");                                             // Print to show that the command was sent
                            channelssh.connect();                                                           // Opening of the channel
                            channelssh.disconnect();                                                        // Closing of the channel
                            System.out.println("Closing");                                                  // Print to show progress of closing
                            session.disconnect();                                                           // Closing of the session
                            System.out.println("Closed");                                                   // Print to show the session closed
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
     * Assigns the text inputted int the songEditText by user to the global "song" variable.
     */
    public void getSong()
    {
        myEditText = (EditText) findViewById(R.id.songEditText);
        song = myEditText.getText().toString();
    }

    /**
     * Assigns the text inputted int the artistEditText by user to the global "artist" variable.
     */
    public void getArtist()
    {
        myEditText = (EditText) findViewById(R.id.artistEditText);
        artist = myEditText.getText().toString();
    }
}
