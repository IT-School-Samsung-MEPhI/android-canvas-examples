package ru.samsung.itschool.canvas.example2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawingView extends View {
    static final float MIN_BRUSH_SIZE = 5f;
    private final Paint paint = new Paint();
    private final Path path = new Path();
    private Canvas canvas;
    private Bitmap bitmap;

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(MIN_BRUSH_SIZE);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.WHITE);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawPath(path, paint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path, paint);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }

    public void clearCanvas() {
        bitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public void setBrushSize(float size) {
        paint.setStrokeWidth(size);
    }

    public void setBrushColor(int color) {
        paint.setColor(color);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
