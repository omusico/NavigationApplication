package com.onlylemi.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dr.navigationapplication.R;

/**
 * Created by only乐秘 on 2015-09-27.
 */
public class RouteSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final String TAG = "RouteSurface:";

    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;

    private int screenW, screenH;

    private boolean flag;
    private Thread thread;

    private float x, y;
    private boolean isTouch = false;

    private Bitmap bmp3DStraight;
    private Bitmap bmp3DStraightConvert;

    public RouteSurface(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);

        paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);
        setKeepScreenOn(true);

        bmp3DStraight = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow3d_straight2);
        bmp3DStraightConvert = convertBmp(bmp3DStraight);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();

        flag = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            canvas = holder.lockCanvas();
            if (canvas != null) {
                myDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                Log.e(TAG, e.toString());
                e.printStackTrace();
            }
        }
    }

    private void myDraw(Canvas canvas) {
        if (canvas != null && isTouch) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //清除画布

            drawArrowOnPortrait(canvas, paint);
//            drawArrowOnLandscape(canvas, paint);
            //canvas.drawCircle(x, y, 20, paint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouch = true;
        x = event.getX();
        y = event.getY();

        return super.onTouchEvent(event);
    }

    /**
     * 横屏时绘制
     *
     * @param canvas
     * @param paint
     */
    private void drawArrowOnLandscape(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.translate(0, screenH);
        canvas.rotate(-90);
        float xn = screenH / 2;
        float yn = screenW;

        float x1 = screenH - y;
        float y1 = x;

        canvas.save();
        canvas.translate(x1, y1);
        if (yn != y1) {
            float degree = (float) Math.toDegrees(Math.atan2(xn - x1, yn - y1));
            canvas.rotate(-degree);
            Log.i(TAG, "degree:" + degree);
        } else {
            canvas.rotate(90);
        }
        /*Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(-100, 100);
        path.lineTo(100, 100);
        path.moveTo(-50, 100);
        path.addRect(-50, 100, 50, 2 * screenH, Path.Direction.CCW);
        path.close();
        canvas.drawPath(path, paint);*/


        RectF rectF = new RectF(0, -200, 200, (screenW - (int) y1));
        if (x1 > xn) {
            canvas.drawBitmap(bmp3DStraight, null, rectF, paint);
        } else {
            canvas.drawBitmap(bmp3DStraightConvert, null, rectF, paint);
        }
        canvas.restore();
        canvas.restore();

    }

    /**
     * 竖屏时绘制
     *
     * @param canvas
     * @param paint
     */
    private void drawArrowOnPortrait(Canvas canvas, Paint paint) {
        float xn = screenW / 2;
        float yn = screenH;

        canvas.save();
        canvas.translate(x, y);
        if (yn != y) {
            float degree = (float) Math.toDegrees(Math.atan2(xn - x, yn - y));
            canvas.rotate(-degree);
            Log.i(TAG, "degree:" + degree);
        } else {
            canvas.rotate(90);
        }
        RectF rectF = new RectF(0, -200, 200, (screenH - (int) y));
        if (x > xn) {
            canvas.drawBitmap(bmp3DStraight, null, rectF, paint);
        } else {
            canvas.drawBitmap(bmp3DStraightConvert, null, rectF, paint);
        }
        canvas.restore();

    }

    public Bitmap convertBmp(Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 镜像水平翻转
        Bitmap convertBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);

        return convertBmp;
    }
}
