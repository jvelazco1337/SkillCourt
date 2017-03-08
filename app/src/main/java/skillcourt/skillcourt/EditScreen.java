package skillcourt.skillcourt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditScreen extends AppCompatActivity
{
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_screen);

        backButton();
    }

    public void backButton()
    {
        myButton = (Button) findViewById(R.id.backButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(EditScreen.this, ShowScreen.class);
                startActivity(intent);
            }
        });
    }
}
