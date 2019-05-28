package com.lj.bookpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BookPageView extends View {
    //    private Paint pointPaint;//绘制各标识点的画笔
    private Paint bgPaint;//背景画笔

    public static final int STYLE_TOP_RIGHT = 1;
    public static final int STYLE_BOTTOM_RIGHT = 3;
    private int style;

    private MyPoint a, f, g, e, h, c, j, b, k, d, i;

    private int defaultWidth;//默认宽度
    private int defaultHeight;//默认高度
    private int viewWidth;
    private int viewHeight;
    private Paint pathAPaint;
    private Path pathA;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private Paint pathBPaint;
    private Path pathB;
    private Paint pathCPaint;
    private Path pathC;
    private Paint textPaint;

    private Bitmap pathAContentBitmap;
    private Bitmap pathBContentBitmap;
    private Bitmap pathCContentBitmap;

    public BookPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        defaultWidth = 800;
        defaultHeight = 1300;

        viewWidth = defaultWidth;
        viewHeight = defaultHeight;

        a = new MyPoint();
        f = new MyPoint();

        g = new MyPoint();
        e = new MyPoint();
        h = new MyPoint();
        c = new MyPoint();
        j = new MyPoint();
        b = new MyPoint();
        k = new MyPoint();
        d = new MyPoint();
        i = new MyPoint();

        bgPaint = new Paint();
        bgPaint.setColor(Color.GREEN);

        pathAPaint = new Paint();
        pathAPaint.setColor(Color.GREEN);
        pathAPaint.setAntiAlias(true);
        pathA = new Path();

        pathB = new Path();
        pathBPaint = new Paint();
        pathBPaint.setColor(Color.BLUE);
        pathBPaint.setAntiAlias(true);
//        pathBPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        pathCPaint = new Paint();
        pathCPaint.setColor(Color.YELLOW);
        pathCPaint.setAntiAlias(true);
        pathC = new Path();
//        pathCPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        style = STYLE_BOTTOM_RIGHT;


        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setSubpixelText(true);
        textPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(0,0,viewWidth,viewHeight,bgPaint);
        canvas.drawColor(Color.YELLOW);
        if (a.x == -1 && a.y == -1) {
            drawPathAContent(canvas, getPathDefault());
        } else if (f.x == viewWidth && f.y == 0) {
            drawPathAContent(canvas, getPathAFromTopRight());
            drawPathCContent(canvas, getPathAFromTopRight());
            drawPathBContent(canvas, getPathAFromTopRight());
        } else if (f.x == viewWidth && f.y == viewHeight) {
            drawPathAContent(canvas, getPathAFromBottomRight());
            drawPathCContent(canvas, getPathAFromBottomRight());
            drawPathBContent(canvas, getPathAFromBottomRight());
        }
    }

    private void drawPathAContent(Canvas canvas, Path pathA) {
        canvas.save();
        canvas.clipPath(pathA);
        canvas.drawBitmap(pathAContentBitmap, 0, 0, null);

        canvas.restore();
    }

    private void drawPathBContent(Canvas canvas, Path pathA) {
        canvas.save();
        canvas.clipPath(pathA);
        canvas.clipPath(getPathC(), Region.Op.UNION);
        canvas.clipPath(getPathB(), Region.Op.REVERSE_DIFFERENCE);
        canvas.drawBitmap(pathBContentBitmap, 0, 0, null);

        canvas.restore();
    }

    private void drawPathCContent(Canvas canvas, Path pathA) {
        canvas.save();
        canvas.clipPath(pathA);
        canvas.clipPath(getPathC(), Region.Op.REVERSE_DIFFERENCE);
        canvas.drawBitmap(pathCContentBitmap, 0, 0, null);

        canvas.restore();
    }

    public void setDefaultPath() {
        a.x = -1;
        a.y = -1;
        postInvalidate();
    }

    private Path getPathDefault() {
        pathA.reset();
        pathA.lineTo(0, viewHeight);
        pathA.lineTo(viewWidth, viewHeight);
        pathA.lineTo(viewWidth, 0);
        pathA.close();
        return pathA;
    }

    private Path getPathAFromBottomRight() {
        pathA.reset();
        pathA.lineTo(0, viewHeight);
        pathA.lineTo(c.x, c.y);
        pathA.quadTo(e.x, e.y, b.x, b.y);
        pathA.lineTo(a.x, a.y);
        pathA.lineTo(k.x, k.y);
        pathA.quadTo(h.x, h.y, j.x, j.y);
        pathA.lineTo(viewWidth, 0);
        pathA.close();
        return pathA;
    }

    private Path getPathAFromTopRight() {
        pathA.reset();
        pathA.lineTo(c.x, c.y);
        pathA.quadTo(e.x, e.y, b.x, b.y);
        pathA.lineTo(a.x, a.y);
        pathA.lineTo(k.x, k.y);
        pathA.quadTo(h.x, h.y, j.x, j.y);
        pathA.lineTo(viewWidth, viewHeight);
        pathA.lineTo(0, viewHeight);
        pathA.close();
        return pathA;
    }

    private Path getPathC() {
        pathC.reset();
        pathC.moveTo(d.x, d.y);
        pathC.lineTo(i.x, i.y);
        pathC.lineTo(k.x, k.y);
        pathC.lineTo(a.x, a.y);
        pathC.lineTo(b.x, b.y);
        pathC.close();
        return pathC;
    }

    private Path getPathB() {
        pathB.reset();

        pathB.lineTo(0, viewHeight);
        pathB.lineTo(viewWidth, viewHeight);
        pathB.lineTo(viewWidth, 0);
        pathB.close();
        return pathB;
    }

    /**
     * 计算各点坐标
     *
     * @param a
     * @param f
     */
    private void calcPointsXY(MyPoint a, MyPoint f) {
        g.x = (a.x + f.x) / 2;
        g.y = (a.y + f.y) / 2;

        e.x = g.x - (f.y - g.y) * (f.y - g.y) / (f.x - g.x);
        e.y = f.y;

        h.x = f.x;
        h.y = g.y - (f.x - g.x) * (f.x - g.x) / (f.y - g.y);

        c.x = e.x - (f.x - e.x) / 2;
        c.y = f.y;

        j.x = f.x;
        j.y = h.y - (f.y - h.y) / 2;

        b = getIntersectionPoint(a, e, c, j);
        k = getIntersectionPoint(a, h, c, j);

        d.x = (c.x + 2 * e.x + b.x) / 4;
        d.y = (2 * e.y + c.y + b.y) / 4;

        i.x = (j.x + 2 * h.x + k.x) / 4;
        i.y = (2 * h.y + j.y + k.y) / 4;
    }

    /**
     * 计算两线段相交点坐标
     *
     * @return 返回该点
     */
    private MyPoint getIntersectionPoint(MyPoint lineOne_My_pointOne, MyPoint lineOne_My_pointTwo, MyPoint lineTwo_My_pointOne, MyPoint lineTwo_My_pointTwo) {
        float x1, y1, x2, y2, x3, y3, x4, y4;
        x1 = lineOne_My_pointOne.x;
        y1 = lineOne_My_pointOne.y;
        x2 = lineOne_My_pointTwo.x;
        y2 = lineOne_My_pointTwo.y;
        x3 = lineTwo_My_pointOne.x;
        y3 = lineTwo_My_pointOne.y;
        x4 = lineTwo_My_pointTwo.x;
        y4 = lineTwo_My_pointTwo.y;

        float pointX = ((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))
                / ((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4));
        float pointY = ((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))
                / ((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4));

        return new MyPoint(pointX, pointY);
    }

    static class MyPoint {
        float x, y;

        MyPoint() {
        }

        MyPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        setMeasuredDimension(width, height);
        viewWidth = width;
        viewHeight = height;
        a.x = -1;
        a.y = -1;
        pathAContentBitmap = Bitmap.createBitmap((int) viewWidth, (int) viewHeight, Bitmap.Config.RGB_565);
        drawPathAContentBitmap(pathAContentBitmap, pathAPaint);

        pathBContentBitmap = Bitmap.createBitmap((int) viewWidth, (int) viewHeight, Bitmap.Config.RGB_565);
        drawPathBContentBitmap(pathBContentBitmap, pathBPaint);
        pathCContentBitmap = Bitmap.createBitmap((int) viewWidth, (int) viewHeight, Bitmap.Config.RGB_565);
        drawPathAContentBitmap(pathCContentBitmap, pathCPaint);
//        calcPointsXY(a, f);

    }

    private void drawPathAContentBitmap(Bitmap bitmap, Paint pathPaint) {
        Canvas mCanvas = new Canvas(bitmap);
        mCanvas.drawPath(getPathDefault(), pathPaint);
        mCanvas.drawText("this is Content of A", viewWidth / 2, viewHeight / 2, textPaint);
    }

    private void drawPathBContentBitmap(Bitmap bitmap, Paint pathPaint) {
        Canvas mCanvas = new Canvas(bitmap);
        mCanvas.drawPath(getPathDefault(), pathPaint);
        mCanvas.drawText("this is content of B", viewWidth / 2, viewHeight / 2, textPaint);
    }


    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    public void setTouchPoint(float x, float y, int style) {
        MyPoint touchPoint;
        a.x = x;
        a.y = y;
        this.style = style;
        switch (style) {
            case STYLE_TOP_RIGHT:
                f.x = viewWidth;
                f.y = 0;
                calcPointsXY(a, f);
//                touchPoint = new MyPoint(x, y);
//                if (c.x < 0) {
//
//                }
                postInvalidate();
                break;
            case STYLE_BOTTOM_RIGHT:
                f.x = viewWidth;
                f.y = viewHeight;
                calcPointsXY(a, f);
//                touchPoint = new MyPoint(x, y);
                postInvalidate();
                break;
            default:
                break;

        }
    }

    public float getViewWidth() {
        return viewWidth;
    }


    public float getViewHeight() {
        return viewHeight;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                if (x > viewWidth / 3 && y <= viewHeight / 3) {
                    style = STYLE_TOP_RIGHT;
                    setTouchPoint(x, y, style);
                } else if (x > viewWidth / 3 && y > viewHeight * 2 / 3) {
                    style = STYLE_BOTTOM_RIGHT;
                    setTouchPoint(x, y, style);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                setTouchPoint(event.getX(), event.getY(), style);
                break;
            case MotionEvent.ACTION_UP:
//                startCancelAnim();
                break;
        }
        return true;
    }
}



