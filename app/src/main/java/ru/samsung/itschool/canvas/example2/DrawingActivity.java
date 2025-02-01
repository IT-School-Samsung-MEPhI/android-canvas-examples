package ru.samsung.itschool.canvas.example2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.samsung.itschool.canvas.databinding.ActivityDrawingBinding;

public class DrawingActivity extends AppCompatActivity {

    private ActivityDrawingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrawingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawing.clearCanvas();
            }
        });

        binding.seekBrushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float brushSize = Math.max(progress, DrawingView.MIN_BRUSH_SIZE);
                binding.drawing.setBrushSize(brushSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });

        for (int i = 0; i < binding.colorPalette.getChildCount(); i++) {
            ImageButton btn = (ImageButton) binding.colorPalette.getChildAt(i);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int color = ((ColorDrawable) btn.getBackground()).getColor();
                    binding.drawing.setBrushColor(color);
                }
            });
        }
    }

    private void shareImage() {
        File imageFile = saveBitmapToCache(binding.drawing.getBitmap());
        if (imageFile == null) {
            return;
        }

        Uri imageUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imageFile);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share your masterpiece via"));
    }

    private File saveBitmapToCache(Bitmap bitmap) {
        File cachePath = new File(getCacheDir(), "images");
        if (!cachePath.exists()) {
            cachePath.mkdirs();
        }

        String fileName = "shared_image_" + System.currentTimeMillis() + ".png";
        File imageFile = new File(cachePath, fileName);

        try (FileOutputStream os = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}