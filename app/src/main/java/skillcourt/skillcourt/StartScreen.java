package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class StartScreen extends AppCompatActivity
{
    Button myButton;
    static Socket clientSocket;
    static String homeIp = "10.109.153.8"; // internal ip of server (aka Pi)
    static int port = 3024;
    ArrayList<Byte> sequence = new ArrayList<>();
    byte[] seq = new byte[5];
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        backButton();
        goButton();
        greenButton();
        redButton();

    }

    public void backButton()
    {
        myButton = (Button) findViewById(R.id.backButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(StartScreen.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

    public void goButton()
    {
        myButton = (Button) findViewById(R.id.goButton);
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
                        OutputStream outputstream = null;
                        try
                        {
                            outputstream = clientSocket.getOutputStream();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        //byte sequence = 0x00;

                        boolean loop = true;
                        while(loop)
                        {
                            try
                            {
                                for(int i = 0; i <= seq.length - 1; i++)
                                {
                                    byte b = seq[i];
                                    outputstream.write(b);
                                    System.out.println("Sending" + b);
                                }
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            //sequence ^= 0x01; //toggle between hit and miss
                            System.out.println("Sent sequence");

                            try
                            {
                                Thread.sleep(1500); //switch every 1.5 seconds
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            loop = false;

                        }


                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void greenButton()
    {
        myButton = (Button) findViewById(R.id.greenButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //sequence.add((byte)0);
                seq[counter] = 0;
                counter++;
            }
        });
    }

    public void redButton()
    {
        myButton = (Button) findViewById(R.id.redButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //sequence.add((byte)1);
                seq[counter] = 0;
                counter++;
            }
        });

    }


}
