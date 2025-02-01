package ru.samsung.itschool.canvas.example1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import ru.samsung.itschool.canvas.R;

public class ExampleView extends View {
    private float screenWidth, screenHeight;
    private Paint rectPaint;
    private Paint circlePaint;
    private Paint linePaint;
    private Paint trianglePaint;
    private Paint textPaint;
    private Path trianglePath;
    private Bitmap xiaomiBitmap;

    public ExampleView(Context context) {
        super(context);
        init();
    }

    public ExampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(5f);

        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(5f);

        trianglePaint = new Paint();
        trianglePaint.setColor(Color.GREEN);
        trianglePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        trianglePath = new Path();

        xiaomiBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiaomi);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        for (int i = 0; i < 12; i++) {
            canvas.rotate(30f, screenWidth / 2, screenHeight / 2);
            float size = 250f;
            canvas.drawRect(screenWidth / 2 - size / 2, screenHeight / 2 - size / 2,
                    screenWidth / 2 + size / 2, screenHeight / 2 + size / 2, rectPaint);
        }

        float circleRadius = 100f;
        canvas.drawCircle(screenWidth / 2, screenHeight / 2, circleRadius, circlePaint);

        float lineStartX = screenWidth * (3f / 4f);
        float lineStartY = screenHeight * (1f / 4f);
        for (int i = 0; i < 12; i++) {
            float angle = (float) Math.toRadians(i * 30);
            float lineEndX = lineStartX + (float) Math.cos(angle) * 300;
            float lineEndY = lineStartY + (float) Math.sin(angle) * 300;
            canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, linePaint);
        }

        float diffX = 400f;
        trianglePath.moveTo(screenWidth / 2 + diffX, screenHeight / 2 - 350);
        trianglePath.lineTo(screenWidth / 2 - 100 + diffX, screenHeight / 2 - 150);
        trianglePath.lineTo(screenWidth / 2 + 100 + diffX, screenHeight / 2 - 150);
        canvas.drawPath(trianglePath, trianglePaint);

        canvas.save();
        float imageX = screenWidth - xiaomiBitmap.getWidth();
        float imageY = screenHeight - xiaomiBitmap.getHeight();
        canvas.rotate(30, imageX + (float) xiaomiBitmap.getWidth() / 2, imageY + (float) xiaomiBitmap.getHeight() / 2);
        canvas.drawBitmap(xiaomiBitmap, imageX, imageY, null);
        canvas.restore();

        canvas.drawText("Hello, Canvas!", screenWidth / 2, screenHeight - 50, textPaint);
    }
}
