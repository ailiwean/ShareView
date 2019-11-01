package com.ailiwean.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AnimSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private boolean isDrawing;
    private SurfaceHolder mSurfaceHolder;

    private Bitmap downBitMap;
    private Bitmap upBitMap;
    private ExecutorService executor;
    private Paint paint;

    public AnimSurface(Context context) {
        super(context);
        init();
    }

    public AnimSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setLayoutParams(new FrameLayout.LayoutParams(-1, -1));

        //得到SurfaceHolder对象
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);//注册SurfaceHolder
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);//保持屏幕长亮

        //设置背景透明
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        executor = Executors.newFixedThreadPool(2);
        paint = new Paint();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public void run() {

        while (true)
            try {
                draw();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public AnimSurface bindBitmap(Bitmap down, Bitmap up) {
        this.downBitMap = down;
        this.upBitMap = up;
        return this;
    }

    public void start() {
        setVisibility(VISIBLE);
        executor.submit(this);
    }

    private void draw() {

        if (!isDrawing)
            return;

        Canvas canvas = mSurfaceHolder.lockCanvas();

        //画布清屏
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        if (downBitMap != null) {

            Rect rectSrc = new Rect();
            rectSrc.set(0, 0, downBitMap.getWidth(), downBitMap.getHeight());
            Rect rectSelf = new Rect();
            rectSelf.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawBitmap(downBitMap, rectSrc, rectSelf, paint);

        }

        if (upBitMap != null) {

            Rect rectSrc = new Rect();
            rectSrc.set(0, 0, downBitMap.getWidth(), downBitMap.getHeight());
            Rect rectSelf = new Rect();
            rectSelf.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawBitmap(downBitMap, rectSrc, rectSelf, paint);

        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        Rect rectSrc = new Rect();
        rectSrc.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rectSelf = new Rect();
        rectSelf.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawBitmap(bitmap, rectSrc, rectSelf, paint);

        mSurfaceHolder.unlockCanvasAndPost(canvas);

    }

}
