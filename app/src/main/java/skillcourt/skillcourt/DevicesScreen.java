package skillcourt.skillcourt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DevicesScreen extends AppCompatActivity
{
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_screen);

        addButton();
        removeButton();
        showButton();
        backButton();
    }

    public void addButton()
    {
        myButton = (Button) findViewById(R.id.addButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(DevicesScreen.this, AddScreen.class);
                startActivity(intent);
            }
        });
    }

    public void removeButton()
    {
        myButton = (Button) findViewById(R.id.removeButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(DevicesScreen.this, RemoveScreen.class);
                startActivity(intent);
            }
        });
    }

    public void showButton()
    {
        myButton = (Button) findViewById(R.id.showButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(DevicesScreen.this, ShowScreen.class);
                startActivity(intent);
            }
        });
    }

    public void backButton()
    {
        myButton = (Button) findViewById(R.id.backButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(DevicesScreen.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }
}
