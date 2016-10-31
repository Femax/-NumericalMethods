package ru.advantum.fedosov.numericmethods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by fedosov on 10/27/16.
 */

public class CalculateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startCalculation();
                startActivity(new Intent(getBaseContext(), DrawActivity.class));
            }
        });
    }

    private void startCalculation() {
        EditText editText = (EditText) findViewById(R.id.h);
        double h = Double.parseDouble(String.valueOf(editText.getText()));
        int n = (int) (1 / h);
        double[][] matrix = new double[n][n];
        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        if (n > 1) {
            /**Предварительный расчет значений xj*/
            for (int i = 0; i < n; i++) {
                x.add((double) (i * h));
            }

            /**Инициализация матрицы производных А'*/
            for (int i = 0; i < n; i++) {
                for (int j = 0; i <= n; i++) {
                    switch (i - j) {
                        case 0:
                            matrix[i][j] = 1;
                            break;
                        case -1:
                            matrix[i][j] = 1;
                            break;
                        default:
                            matrix[i][j] = 0;
                            break;
                    }
                }
            }
            double[] alpha = new double[n];
            double[] betta = new double[n];
            alpha[0] = 0;
            betta[0] = 0;
            for (int i = 1; i < n; i++) {
                alpha[i] = -matrix[i][i + 1] / (matrix[i][i]);
                betta[i] = 1 / matrix[i][i];
            }
        }
    }
}