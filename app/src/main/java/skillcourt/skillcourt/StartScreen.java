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


public class StartScreen extends AppCompatActivity
{
    Button myButton;
    static Socket clientSocket;
    static String homeIp = "10.109.153.8"; // internal ip of server (aka Pi)
    static int port = 9999;
    int[] seq = new int[6];
    int counter= 0, off = 5;
    boolean breakMyBones = false;
    OutputStream outputstream = null;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        backButton();
        connectButton();
        greenButton();
        yellowButton();
        blueButton();
        redButton();
        disconnectButton();



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

    public void redButton()
    {
        myButton = (Button) findViewById(R.id.redButton);
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
                                outputstream.write(off);
                                System.out.println("Sending " + off);


                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                            System.out.println("Sent sequence");

                            try
                            {
                                Thread.sleep(15000); //switch every 1.5 seconds
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }






                        return null;
                    }
                }.execute(1);

            }
        });
    }

    public void blueButton()
    {
        myButton = (Button) findViewById(R.id.blueButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                new AsyncTask<Integer, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Integer... params)
                    {

                        counter = 0;
                        breakMyBones = false;
                        while(counter <= seq.length - 1)
                        {

                            try
                            {
                                outputstream.write(seq[counter]);
                                System.out.println("Sending " + seq[counter]);


                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                            System.out.println("Sent sequence");

                            try
                            {
                                Thread.sleep(15000); //switch every 1.5 seconds
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }

                            if (counter == seq.length - 1)
                            {
                                counter = 0;
                            }
                            else if (breakMyBones == true)
                            {
                                break;
                            }
                            else
                            {
                                counter++;
                            }



                        }


                        return null;
                    }
                }.execute(1);

            }
        });
    }


    public void connectButton()
    {
        myButton = (Button) findViewById(R.id.connectButton);
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

    public void disconnectButton()
    {
        myButton = (Button) findViewById(R.id.disconnectButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                try
                {
                    clientSocket.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
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
                seq[0] = 16;
                seq[1] = 4;
                seq[2] = 0;
                seq[3] = 1;
                seq[4] = 0;
                seq[5] = 1;

                System.out.println("Sent sequence Green");
            }
        });
    }

    public void yellowButton()
    {
        myButton = (Button) findViewById(R.id.yellowButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                breakMyBones = true;


            }
        });

    }


}
