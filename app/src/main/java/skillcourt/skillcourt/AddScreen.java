package skillcourt.skillcourt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddScreen extends AppCompatActivity
{
    Button myButton;
    EditText myEdit1, myEdit2, myEdit3, myEdit4, myEdit5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen);

        addPiButton();
        backButton();
    }

    public void addPiButton()
    {
        myButton = (Button) findViewById(R.id.addPiButton);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                myEdit1 = (EditText) findViewById(R.id.userEditText);
                myEdit2 = (EditText) findViewById(R.id.passEditText);
                myEdit3 = (EditText) findViewById(R.id.ipEditText);
                myEdit4 = (EditText) findViewById(R.id.portEditText);
                myEdit5 = (EditText) findViewById(R.id.descriptionEditText);

                String user = myEdit1.getText().toString();
                String pass = myEdit2.getText().toString();
                String ip = myEdit3.getText().toString();
                String port = myEdit4.getText().toString();
                int finalPort = Integer.parseInt(port);
                String decript = myEdit5.getText().toString();

                RaspberryPi rasp1 = new RaspberryPi(user, pass, ip, finalPort);

                RaspberryPiManager list = new RaspberryPiManager();

                list.addPi(rasp1);


                System.out.println(user);
                System.out.println(pass);
                System.out.println(ip);
                System.out.println(finalPort);
                System.out.println(decript);

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
                Intent intent = new Intent(AddScreen.this, DevicesScreen.class);
                startActivity(intent);
            }
        });
    }


}
