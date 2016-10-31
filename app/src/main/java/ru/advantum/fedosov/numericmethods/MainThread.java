package ru.advantum.fedosov.numericmethods;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by fedosov on 10/27/16.
 */

public class MainThread extends  Thread {
    private static final String TAG = MainThread.class.getSimpleName();
    private SurfaceHolder surfaceHolder;
    private MainDrawPanel gamePanel;
    private Paint paintRed;
    private Paint paintBlue;
    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, MainDrawPanel gamePanel) {
        super();

        this.paintRed = new Paint();
        this.paintRed.setColor(Color.RED);
        this.paintRed.setStrokeWidth(1);

        this.paintBlue = new Paint();
        this.paintBlue.setColor(Color.BLUE);
        this.paintBlue.setStrokeWidth(1);

        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    class MeasuredAxis {
        public static final int X_GAP = 1;
        public static final int Y_GAP = 1;

        int _realHeight;
        int _realWidth;

        public MeasuredAxis(int width, int height) {
            _realWidth = width;
            _realHeight = height;
        }

        public int getXAxisNegLimit() {
            return (_realWidth / 2)*(-1);
        }

        public int getXAxisLimit() {
            return (_realWidth / 2);
        }

        public float getXMeasured(int x) {
            return ((_realWidth / 2) + x);
        }

        public float getYMeasured(float y) {
            return ((_realHeight / 2) - y);
        }
    }


    private float function(float x) {
        return Double.valueOf(Math.pow(x, 2)).floatValue()/100;
    }

    @Override
    public void run() {
        long fps = 0L;
        Log.d(TAG, "Starting game loop");
        Canvas canvas = surfaceHolder.lockCanvas();

        MeasuredAxis measured = new MeasuredAxis(canvas.getWidth(), canvas.getHeight());

        synchronized (canvas) {
            int x = measured.getXAxisNegLimit();
            //from -x to +x evaluate and plot the function
            while (x++ < measured.getXAxisLimit()) {
                canvas.drawLine(
                        measured.getXMeasured(x),
                        measured.getYMeasured(function(x)),
                        measured.getXMeasured(x+MeasuredAxis.X_GAP),
                        measured.getYMeasured(function(x+MeasuredAxis.Y_GAP)),
                        paintRed);
            }
            canvas.save();
            canvas.translate(0, 0);
            canvas.scale(canvas.getWidth(), canvas.getHeight());
            canvas.restore();

        }
        surfaceHolder.unlockCanvasAndPost(canvas);

    }

}
