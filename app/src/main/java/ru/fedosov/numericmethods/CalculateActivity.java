package ru.fedosov.numericmethods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by fedosov on 10/27/16.
 */

public class CalculateActivity extends Activity {
    private static final String EXTRA_H = "EXTRA_H";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DrawActivity.class);
                EditText editText = (EditText) findViewById(R.id.h);
                if (!editText.getText().toString().equals("")) {
                    double h = Double.parseDouble(String.valueOf(editText.getText()));
                    intent.putExtra(EXTRA_H, h);
                }
                startActivity(intent);
            }
        });
    }


}