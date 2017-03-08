package skillcourt.skillcourt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jvela on 3/1/2017.
 */

public class SplashScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread timerThread = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                } finally
                {
                    Intent intent = new Intent(SplashScreen.this, MainScreen.class);
                    {
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
