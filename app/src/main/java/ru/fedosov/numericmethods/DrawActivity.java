package ru.fedosov.numericmethods;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.util.Log;
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
            double[][] matrix = new double[n + 1][n + 2];
            double[] alpha = new double[n + 1];
            double[] betta = new double[n + 1];
            initializeMatrix(n, h, matrix);

            for (int i = 1; i < n; i++) {
                matrix[i][n] = h * h;
                if (i == 1) matrix[i][n] = matrix[i][n] - 1;
            }

            matrixToLog(matrix, n);

            calculateAlphaBetta(n, h, matrix, alpha, betta);
            numericPoints.add(new Point(0, 1));
            realPoints.add(new Point(0, 1));

            alphaBettaToLog(alpha,betta,n);
            for (int i = 1; i < n; i++) {
                double a = matrix[i][i - 1];
                double c = matrix[i][i];
                double b = i+1!=n?matrix[i][i + 1]:0;
                double f = matrix[i][n];
                numericPoints.add(new Point(i * h, (f-a*betta[i])/(c+a*alpha[i])));
                realPoints.add(new Point(i * h, function(i * h)));
            }
            numericPoints.add(new Point(1, 0));
            realPoints.add(new Point(1, 0));
            return new Pair<>(numericPoints, realPoints);
        }

        private void alphaBettaToLog(double[] alpha, double[] betta, int n) {
            StringBuilder sb = new StringBuilder();
            sb.append("alpha = ");
            for(int i=0;i<n;i++){
                sb.append(alpha[i]);
                sb.append(",");
            }
            sb.append("betta = ");
            for(int i=0;i<n;i++){
                sb.append(betta[i]);
                sb.append(",");
            }
            Log.d("alphabetta",sb.toString());
        }

        private void matrixToLog(double[][] matrix, int n) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    sb.append(matrix[i][j]);
                    sb.append(" ");
                }
                sb.append("\r\n");
            }
            Log.d("matrix", sb.toString());
        }

        private void calculateAlphaBetta(int n, double h, double[][] matrix, double[] alpha, double[] betta) {

            for (int i = n - 1; i > 0; i--) {
                double a = matrix[i][i - 1];
                double c = matrix[i][i];
                double b = i+1!=n?matrix[i][i + 1]:0;
                double f = matrix[i][n];
                alpha[i] = -b / (a * alpha[i+1] + c);
                betta[i] = (f - a * betta[i+1]) / (a * alpha[i+1]+c);
            }
        }

        private void initializeMatrix(int n, double h, double[][] matrix) {
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) matrix[i][j] = -2 - h * h;
                    else if (Math.abs(i - j) == 1) {
                        matrix[i][j] = 1;
                        if (i == 1 && j == 0) matrix[i][j] = 0;
                    }
                }
            }
        }

        private double function(double x) {
            return Math.exp(-1) * (Math.exp(x) - 2 * Math.exp(2 * x) - Math.exp(x + 2) + Math.exp(2 * x + 1) - Math.exp(1) + 2 * Math.exp(2)) / (Math.exp(2) - 1);
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
