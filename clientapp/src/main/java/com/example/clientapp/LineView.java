package com.example.clientapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.VectorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.groupryan.shared.utils;

/**
 * Created by Daniel on 3/3/2018.
 */

public class LineView extends View {

    private Paint foregroundPaint = new Paint();

    private Paint backgroundPaint = new Paint();

    private PointF pointA = new PointF();

    private PointF pointB = new PointF();

    private int routeId = 0;

    public LineView(Context context) {
        super(context);
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        foregroundPaint.setStrokeWidth(16);
        canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, backgroundPaint);
        foregroundPaint.setStrokeWidth(10);
        canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, foregroundPaint);
        super.onDraw(canvas);
    }

    public void setxCoordinateA(float val) {
        this.pointA.x = val;
    }

    public void setyCoordinateA(float val) {
        this.pointA.y = val;
    }

    public void setxCoordinateB(float val) {
        this.pointB.x = val;
    }

    public void setyCoordinateB(float val) {
        this.pointB.y = val;
    }

    public void setPointA(PointF pointA) {
        this.pointA = pointA;
    }

    public PointF getPointA() {
        return pointA;
    }

    public void setPointB(PointF pointB) {
        this.pointB = pointB;
    }

    public PointF getPointB() {
        return pointB;
    }

    public void draw() {
        invalidate();
        requestLayout();
    }

    public void setColor(String color) {
        switch (color) {
            case utils.BLACK:
                foregroundPaint.setColor(Color.BLACK);
                backgroundPaint.setColor(Color.WHITE);
                break;
            case utils.BLUE:
                foregroundPaint.setColor(Color.BLUE);
                backgroundPaint.setColor(Color.BLACK);
                break;
            case utils.RED:
                foregroundPaint.setColor(Color.RED);
                backgroundPaint.setColor(Color.BLACK);
                break;
            case utils.YELLOW:
                foregroundPaint.setColor(Color.YELLOW);
                backgroundPaint.setColor(Color.BLACK);
                break;
            case utils.GREEN:
                foregroundPaint.setColor(Color.GREEN);
                backgroundPaint.setColor(Color.BLACK);
                break;
        }
    }
}
