package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.*;
import java.net.*;

public class MainScreen extends AppCompatActivity
{
    Button myButton;

    static Socket clientSocket;
    static String homeIp = "10.109.153.8"; // internal ip of server (aka Pi)
    static int port = 3024;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);



        buttonSend4();
        startButton();
        speedButton();
        songButton();




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


}
