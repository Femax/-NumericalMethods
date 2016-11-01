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
    Paint mPaintNumberic;
    Paint mPaintReal;

    public MainDrawPanel(Context context, ArrayList<Point> numbericResults, ArrayList<Point> realResults) {
        super(context);
        this.numbericResults = numbericResults;
        this.realResults = realResults;
        mPaintNumberic = new Paint();
        mPaintNumberic.setStrokeWidth(2);
        mPaintNumberic.setColor(Color.RED);
        mPaintReal = new Paint();
        mPaintReal.setStrokeWidth(2);
        mPaintReal.setColor(Color.BLUE);
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
            float startX = (float) numbericResults.get(i).getX()*canvas.getHeight()/3 + canvas.getWidth() / 2;
            float startY = canvas.getHeight() / 2 - (float) numbericResults.get(i).getY()*canvas.getWidth()/3;
            float endX = (float) numbericResults.get(i + 1).getX()*canvas.getHeight()/3 + canvas.getWidth() / 2;
            float endY = canvas.getHeight() / 2 - (float) numbericResults.get(i + 1).getY()*canvas.getWidth()/3;
            canvas.drawLine(startX, startY, endX, endY, mPaintNumberic);
        }

        for (int i = 0; i < realResults.size() - 1; i++) {
            float startX = (float) realResults.get(i).getX()*canvas.getHeight()/3 + canvas.getWidth() / 2;
            float startY = canvas.getHeight() / 2 - (float) realResults.get(i).getY()*canvas.getWidth()/3;
            float endX = (float) realResults.get(i + 1).getX()*canvas.getHeight()/3 + canvas.getWidth() / 2;
            float endY = canvas.getHeight() / 2 - (float) realResults.get(i + 1).getY()*canvas.getWidth()/3;
            canvas.drawLine(startX, startY, endX, endY, mPaintReal);
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
