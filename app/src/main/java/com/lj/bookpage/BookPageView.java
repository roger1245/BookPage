package com.lj.bookpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BookPageView extends View {
//    private Paint pointPaint;//绘制各标识点的画笔
    private Paint bgPaint;//背景画笔

    public static final int STYLE_TOP_LEFT = 0;
    public static final int STYLE_TOP_RIGHT = 1;
    public static final int STYLE_BOTTOM_LEFT = 2;
    public static final int STYLE_BOTTOM_RIGHT = 3;

    private MyPoint a,f,g,e,h,c,j,b,k,d,i;

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

    public BookPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){
        defaultWidth = 800;
        defaultHeight = 1300;

        viewWidth = defaultWidth;
        viewHeight = defaultHeight;

        a = new MyPoint();
//        f = new MyPoint(viewWidth,viewHeight);
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
//        calcPointsXY(a,f);

        bgPaint = new Paint();
        bgPaint.setColor(Color.GREEN);

        pathAPaint = new Paint();
        pathAPaint.setColor(Color.GREEN);
        pathAPaint.setAntiAlias(true);
        pathA = new Path();

        pathB = new Path();
        pathBPaint = new Paint();
        pathBPaint.setColor(Color.RED);
        pathBPaint.setAntiAlias(true);
        pathBPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        pathCPaint = new Paint();
        pathCPaint.setColor(Color.BLUE);
        pathCPaint.setAntiAlias(true);
        pathC = new Path();
        pathCPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,viewWidth,viewHeight,bgPaint);
        if (a.x == -1 && a.y == -1) {
            bitmapCanvas.drawPath(getPathDefault(), pathAPaint);
        } else {
            bitmapCanvas.drawPath(getPathA(), pathAPaint);
        }

        bitmapCanvas.drawPath(getPathB(), pathBPaint);
        bitmapCanvas.drawPath(getPathC(), pathCPaint);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    public void setDefaultPath(){
        a.x = -1;
        a.y = -1;
        postInvalidate();
    }

    private Path getPathDefault(){
        pathA.reset();
        pathA.lineTo(0, viewHeight);
        pathA.lineTo(viewWidth,viewHeight);
        pathA.lineTo(viewWidth,0);
        pathA.close();
        return pathA;
    }

    //根据f点的位置进行判断pathA路径
    private Path getPathA() {
        pathA.reset();

        pathA.moveTo(j.x == 0 ? getWidth() : 0, c.y == 0 ? getHeight() : 0);
        pathA.lineTo(f.x == 0 ? getWidth() : 0, 0 != f.y ? getHeight() : 0);

        pathA.lineTo(c.x,c.y);//移动到c点
        pathA.quadTo(e.x,e.y,b.x,b.y);//从c到b画贝塞尔曲线，控制点为e
        pathA.lineTo(a.x,a.y);//移动到a点
        pathA.lineTo(k.x,k.y);//移动到k点
        pathA.quadTo(h.x,h.y,j.x,j.y);//从k到j画贝塞尔曲线，控制点为h

        pathA.lineTo(0 != f.x ? getWidth() : 0, 0 == f.y ? getHeight() : 0);
        pathA.close();//闭合区域
        return pathA;
    }

    private Path getPathB() {
        pathB.reset();
        pathB.moveTo(d.x, d.y);
        pathB.lineTo(i.x, i.y);
        pathB.lineTo(k.x, k.y);
        pathB.lineTo(a.x, a.y);
        pathB.lineTo(b.x, b.y);
        pathB.close();
        return pathB;
    }

    private Path getPathC() {
        pathC.reset();

        pathC.lineTo(0, viewHeight);
        pathC.lineTo(viewWidth, viewHeight);
        pathC.lineTo(viewWidth, 0);
        pathC.close();
        return pathC;
    }

    /**
     * 计算各点坐标
     * @param a
     * @param f
     */
    private void calcPointsXY(MyPoint a, MyPoint f){
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

        b = getIntersectionPoint(a,e,c,j);
        k = getIntersectionPoint(a,h,c,j);

        d.x = (c.x + 2 * e.x + b.x) / 4;
        d.y = (2 * e.y + c.y + b.y) / 4;

        i.x = (j.x + 2 * h.x + k.x) / 4;
        i.y = (2 * h.y + j.y + k.y) / 4;
    }

    /**
     * 计算两线段相交点坐标
     * @return 返回该点
     */
    private MyPoint getIntersectionPoint(MyPoint lineOne_My_pointOne, MyPoint lineOne_My_pointTwo, MyPoint lineTwo_My_pointOne, MyPoint lineTwo_My_pointTwo){
        float x1,y1,x2,y2,x3,y3,x4,y4;
        x1 = lineOne_My_pointOne.x;
        y1 = lineOne_My_pointOne.y;
        x2 = lineOne_My_pointTwo.x;
        y2 = lineOne_My_pointTwo.y;
        x3 = lineTwo_My_pointOne.x;
        y3 = lineTwo_My_pointOne.y;
        x4 = lineTwo_My_pointTwo.x;
        y4 = lineTwo_My_pointTwo.y;

        float pointX =((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))
                / ((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4));
        float pointY =((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))
                / ((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4));

        return  new MyPoint(pointX,pointY);
    }
    class MyPoint {
        float x,y;
        MyPoint(){}
        MyPoint(float x, float y){
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
        a.x = 80;
        a.y = 140;
        f.x = 0;
        f.y = viewHeight;
        calcPointsXY(a, f);
//        a.x = -1;
//        a.y = -1;
        initBitmap();

    }
    private void initBitmap() {
        bitmap = Bitmap.createBitmap((int) viewWidth, (int) viewHeight, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
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
        switch (style) {
            case STYLE_TOP_LEFT:
                f.x = 0;
                f.y = 0;
            case STYLE_TOP_RIGHT:
                f.x = getWidth();
                f.y = 0;
            case STYLE_BOTTOM_LEFT :
                f.x = 0;
                f.y = getHeight();
            case STYLE_BOTTOM_RIGHT :
                f.x = getWidth();
                f.y = getHeight();
            default:
                break;
        }
        a.x = x;
        a.y = y;
        calcPointsXY(a, f);
        postInvalidate();
    }
    public float getViewWidth(){
        return viewWidth;
    }


    public float getViewHeight(){
        return viewHeight;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}



