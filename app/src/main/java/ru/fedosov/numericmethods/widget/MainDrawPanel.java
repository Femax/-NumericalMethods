package ru.fedosov.numericmethods.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import ru.fedosov.numericmethods.model.Point;

/**
 * Created by fedosov on 10/27/16.
 */

public class MainDrawPanel extends View {

    ArrayList<Point> numbericResults;
    ArrayList<Point> realResults;
    Paint mPaint;

    public MainDrawPanel(Context context, ArrayList<Point> numbericResults, ArrayList<Point> realResults) {
        super(context);
        this.numbericResults = numbericResults;
        this.realResults = realResults;
        mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.RED);
    }

    public MainDrawPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainDrawPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOs(canvas);
        for (int i = 0; i < numbericResults.size() - 1; i++) {
            canvas.drawLine((float) numbericResults.get(i).getX() + canvas.getWidth() / 2
                    , canvas.getHeight() / 2 - (float) numbericResults.get(i).getY()
                    , (float) numbericResults.get(i + 1).getX() + canvas.getWidth() / 2
                    , canvas.getHeight() / 2 - (float) numbericResults.get(i + 1).getY()
                    , mPaint);
        }

    }

    private void drawOs(Canvas canvas) {
        Paint osPaint = new Paint();
        osPaint.setColor(Color.BLACK);
        osPaint.setStrokeWidth(2f);
        canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight(), osPaint);
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, osPaint);
    }

}
