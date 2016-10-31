package ru.fedosov.numericmethods;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import ru.fedosov.numericmethods.model.Point;
import ru.fedosov.numericmethods.widget.MainDrawPanel;

public class DrawActivity extends Activity {
    private static final String EXTRA_H = "EXTRA_H";
    private CalculationTask mCalculationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_draw);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double value = extras.getDouble(EXTRA_H);
            mCalculationTask = (CalculationTask) new CalculationTask().execute(value);
        }
    }

    private class CalculationTask extends AsyncTask<Double, Void, Pair<ArrayList<Point>, ArrayList<Point>>> {

        @Override
        protected Pair<ArrayList<Point>, ArrayList<Point>> doInBackground(Double... params) {
            double h = params[0];
            int n = (int) (1 / h);
            ArrayList<Point> numericPoints = new ArrayList<>();
            ArrayList<Point> realPoints = new ArrayList<>();
            double[][] matrix = new double[n+1][n + 2];
            double[] alpha = new double[n+1];
            double[] betta = new double[n+1];
            initializeMatrix(n, h, matrix);

            for (int i = 1; i < n; i++) {
                matrix[i][n+1] = h * h;
            }
            calculateAlphaBetta(n, h, matrix, alpha, betta);
            for (int i = 1; i < n; i++) {
                numericPoints.add(new Point(i * h, (matrix[i][n] - matrix[i][i - 1] * betta[i]) / (matrix[i][i] + matrix[i][i - 1] * alpha[i])));
            }
            Pair<ArrayList<Point>, ArrayList<Point>> data = new Pair<>(numericPoints, realPoints);
            return data;
        }

        private void calculateAlphaBetta(int n, double h, double[][] matrix, double[] alpha, double[] betta) {
            alpha[n] = -matrix[n - 1][n] / matrix[n - 1][n - 1];
            betta[n] = -matrix[n - 1][n + 1] / matrix[n - 1][n - 1];
            for (int i = 1; i < n-1; i++) {
                alpha[n - i] = -matrix[n - i][n - i] / (matrix[n - i][n - i + 1] * alpha[i] + matrix[n - i][n - i]);
                betta[n - i] = (matrix[n - i][n + i] - matrix[n - i][n - i - 1]) / (matrix[n - i][n - i - 1] * alpha[i] + matrix[n - i][n - i]);

            }
        }

        private void initializeMatrix(int n, double h, double[][] matrix) {
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < n; j++) {
                    if (i == j) matrix[i][j] = 2 + h * h;
                    else if (Math.abs(i - j) == 1) {
                        matrix[i][j] = 1;
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(Pair<ArrayList<Point>, ArrayList<Point>> Data) {
            super.onPostExecute(Data);
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.layout);
            MainDrawPanel mainDrawPanel = new MainDrawPanel(getBaseContext(), Data.first, Data.second);
            viewGroup.addView(mainDrawPanel);
        }
    }
}
