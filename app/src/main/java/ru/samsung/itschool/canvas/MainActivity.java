package ru.samsung.itschool.canvas;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.samsung.itschool.canvas.example1.ExampleActivity;
import ru.samsung.itschool.canvas.example2.DrawingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openExample1(View view) { openActivity(ExampleActivity.class); }

    public void openExample2(View view) { openActivity(DrawingActivity.class); }

    private void openActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}