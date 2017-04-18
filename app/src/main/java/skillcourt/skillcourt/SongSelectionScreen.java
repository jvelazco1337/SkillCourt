package skillcourt.skillcourt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by jvelazco1337 on 4/17/17.
 */

public class SongSelectionScreen extends AppCompatActivity
{
    Button myButton;
    EditText myEditText;
    String song;
    String artist;

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
                        System.out.println("Setting up");
                        try
                        {
                            Socket soc = new Socket("172.20.10.12", 9999);
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
                        }
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
