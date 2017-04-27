package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
;


public class StartScreen extends AppCompatActivity
{
    // Button and Image Button declaration to be used later
    Button myButton;
    ImageButton myImageButton;

    // Socket and OutputStream declaration
    static Socket clientSocket;
    OutputStream myOutStream = null;

    // Extra variables needed for specific tasks
    final int OFF = 6;
    static int port = 3024;
    int[] seq = new int[6];
    int counter = 0;
    boolean pauseSequence = false;


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
        setContentView(R.layout.start_screen);

        // Buttons to be used in the APP
        backButton();
        connectButton();
        setSeqButton();
        pauseButton();
        sendSeqButton();
        disconnectButton();
    }

    /**
     * The backButton method will navigate from the start screen back into the
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
                Intent intent = new Intent(StartScreen.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }


    /**
     * The sendSeqButton method will send the assigned sequence in setSeqButton method
     * into the Pi. In our code we have alternating from 0 and 1 to be able to
     * demonstrate both versions of the sounds.
     */
    public void sendSeqButton()
    {
        myButton = (Button) findViewById(R.id.sendSeqButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Async method to be able to run Networking in the background
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        // Set counter and pauseSequence variables
                        counter = 0;
                        pauseSequence = false;

                        // While loop to keep alternating the array containing 0-1 to
                        // alternate in the Pi.
                        while(counter <= seq.length - 1)
                        {
                            // Try statement to send the array to the loop and catch exception
                            try
                            {
                                // Send array and print what is sending to screen
                                myOutStream.write(seq[counter]);
                                System.out.println("Sending " + seq[counter]);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                            System.out.println("Sent Sequence"); // Print showing what was sent

                            try
                            {
                                Thread.sleep(1500); // Switch every 1.5 seconds
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }

                            // If statement to reset array to keep going till the pauseSequence
                            // variable is triggered which causes loop to break
                            if (counter == seq.length - 1)
                            {
                                counter = 0; // Reset counter if array reaches end
                            }
                            else if (pauseSequence == true)
                            {
                                break; // Break loop if pauseSequence has been triggered
                            }
                            else
                            {
                                counter++; // Increment counter for array
                            }
                        }
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    /**
     * The connectButton initializes the session to connect to the Pi via sockets.
     */
    public void connectButton()
    {
        myButton = (Button) findViewById(R.id.connectButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Async method to be able to run Networking in the background
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        System.out.println("Setting up"); // Print to show that it is setting up

                        // Try statement that attepts to connect to the Pi with the specified
                        // IP and Port in the variables above
                        try
                        {
                            clientSocket = new Socket(raspIP, port);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        System.out.println("Connected to server"); // Print showing connection successful

                        // Assign socket to stream
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

    /**
     * The disconnectButton method will end the C program inside the Pi by first sending it the
     * value of OFF and then closes both the stream and the socket. If the sequence is being sent,
     * make sure to pause before you disconnect.
     */
    public void disconnectButton()
    {
        myButton = (Button) findViewById(R.id.disconnectButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Async method to be able to run Networking in the background
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {
                        // Close everything down, as long as sequence was paused first
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

                        System.out.println("Disconnected Successfully"); // Print to show we disconnected successfully
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    /**
     * The setSeqButton method will assign the specific sequence to the array to be sent to the Pi.
     */
    public void setSeqButton()
    {
        myButton = (Button) findViewById(R.id.setSeqButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // Assignment of sequence
                seq[0] = 1;
                seq[1] = 0;
                seq[2] = 1;
                seq[3] = 0;
                seq[4] = 1;
                seq[5] = 0;

                System.out.println("Sequence Assigned"); // Print to show sequence was assigned
            }
        });
    }

    /**
     * The pauseButton will pause the array from being sent to the Pi. It's main purpose to be
     * able to stop the sending of sequence and disconnect successfully
     */
    public void pauseButton()
    {
        myButton = (Button) findViewById(R.id.pauseButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                pauseSequence = true; // Set variable to break the loop
                System.out.println("Pausing Sequence"); // Print to show the sequence was paused
            }
        });

    }


}
