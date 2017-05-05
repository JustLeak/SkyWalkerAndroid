package app.skywalker.ru.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        final Button sightsButton = (Button)findViewById(R.id.sightsButton);
        sightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                try {
                    Intent intent = new Intent(MainMenu.this, Sights.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(MainMenu.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
