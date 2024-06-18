package com.example.hw2_domenicofasano_721hhhinginfor;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumber1, editTextNumber2;
    Button buttonAdd, buttonSubtract, buttonMultiply, buttonDivide,
            buttonStartTimer, buttonDownloadPhoto, buttonInfo;
    TextView textViewCalculationResult, textTimerResult, textImageResult;
    ImageView imageViewPhoto;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonStartTimer = findViewById(R.id.buttonStartTimer);
        buttonDownloadPhoto = findViewById(R.id.buttonDownloadPhoto);
        buttonInfo = findViewById(R.id.buttonInfo);
        textViewCalculationResult = findViewById(R.id.textViewCalculationResult);
        textTimerResult = findViewById(R.id.textTimerResult);
        textImageResult = findViewById(R.id.textImageResult);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);

        //set number picker with maximum and minimum value
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(120);
        numberPicker.setValue(10);

        buttonInfo.setOnClickListener(v -> openInfoActivity());

        buttonAdd.setOnClickListener(v -> {
            try {
                textViewCalculationResult.setTextColor(Color.BLACK);
                textViewCalculationResult.setText("Doing addition...");
                double num1 = Double.parseDouble(editTextNumber1.getText().toString());
                double num2 = Double.parseDouble(editTextNumber2.getText().toString());
                doCalculation("Add", num1, num2);
            } catch (NumberFormatException e) {
                Log.e("ButtonADD", "onClick: in buttonAdd one or both the numbers are null or have an invalid format");
                textViewCalculationResult.setTextColor(Color.RED);
                textViewCalculationResult.setText("Invalid numbers");
            }
        });

        buttonSubtract.setOnClickListener(v -> {
            try {
                textViewCalculationResult.setTextColor(Color.BLACK);
                textViewCalculationResult.setText("Doing subtraction...");
                double num1 = Double.parseDouble(editTextNumber1.getText().toString());
                double num2 = Double.parseDouble(editTextNumber2.getText().toString());
                doCalculation("Subtract", num1, num2);
            } catch (NumberFormatException e) {
                Log.e("ButtonSUB", "onClick: in buttonSub one or both the numbers are null or have an invalid format");
                textViewCalculationResult.setTextColor(Color.RED);
                textViewCalculationResult.setText("Invalid numbers");
            }
        });

        buttonMultiply.setOnClickListener(v -> {
            try {
                textViewCalculationResult.setText("Doing multiplication...");
                textViewCalculationResult.setTextColor(Color.BLACK);
                double num1 = Double.parseDouble(editTextNumber1.getText().toString());
                double num2 = Double.parseDouble(editTextNumber2.getText().toString());
                doCalculation("Multiply", num1, num2);
            } catch (NumberFormatException e) {
                Log.e("ButtonMULT", "onClick: in buttonMult one or both the numbers are null or have an invalid format");
                textViewCalculationResult.setTextColor(Color.RED);
                textViewCalculationResult.setText("Invalid numbers");
            }
        });

        buttonDivide.setOnClickListener(v -> {
            try {
                textViewCalculationResult.setText("Doing division...");
                textViewCalculationResult.setTextColor(Color.BLACK);
                double num1 = Double.parseDouble(editTextNumber1.getText().toString());
                double num2 = Double.parseDouble(editTextNumber2.getText().toString());
                if (num2 == 0) {
                    textViewCalculationResult.setTextColor(Color.RED);
                    textViewCalculationResult.setText("Can't divide by 0");
                    return;
                }
                doCalculation("Divide", num1, num2);
            } catch (NumberFormatException e) {
                Log.e("ButtonDIV", "onClick: in buttonDiv one or both the numbers are null or have an invalid format");
                textViewCalculationResult.setTextColor(Color.RED);
                textViewCalculationResult.setText("Invalid numbers");
            }
        });

        buttonStartTimer.setOnClickListener(v -> {
            textTimerResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textTimerResult.setTextColor(Color.BLACK);
            textTimerResult.setText("Running timer...");
            int seconds = numberPicker.getValue();
            TimerTask timerTask = new TimerTask(textTimerResult);
            timerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, seconds);
        });

        buttonDownloadPhoto.setOnClickListener(v -> {
            imageViewPhoto.setImageBitmap(null);
            textImageResult.setTextColor(Color.BLACK);
            textImageResult.setText("Downloading image...");
            new DownloadImageTask()
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                            "https://www.uninettunouniversity.net/data/skins/uninettunonew/images/logo.png"
                    );
        });
    }

    private void openInfoActivity() {
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        startActivity(intent);
    }

    void doCalculation(String operation, double num1, double num2) {
        double result = 0;

        switch (operation) {
            case "Add":
                result = num1 + num2;
                break;
            case "Subtract":
                result = num1 - num2;
                break;
            case "Multiply":
                result = num1 * num2;
                break;
            case "Divide":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    // Handle division by zero
                    result = Double.NaN; // or throw an error, depending on your requirement
                }
                break;
        }
        if (!Double.isNaN(result)) {
            textViewCalculationResult.setTextColor(Color.GREEN);
            textViewCalculationResult.setGravity(Gravity.CENTER);
            textViewCalculationResult.setText(operation + " result:\n" + result);
        } else {
            textViewCalculationResult.setTextColor(Color.RED);
            textViewCalculationResult.setText("Error: Invalid input or division by zero");
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            textViewCalculationResult.setTextColor(Color.BLACK);
            String urlString = urls[0];
            Bitmap bitmap = null;
            try {
                sleep(3000);
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                // Resize bitmap to fit screen
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getResources().getDisplayMetrics().heightPixels;

                float bitmapRatio = (float) result.getWidth() / (float) result.getHeight();
                float screenRatio = (float) screenWidth / (float) screenHeight;

                int bitmapWidth, bitmapHeight;
                if (bitmapRatio > screenRatio) {
                    bitmapWidth = screenWidth;
                    bitmapHeight = (int) (bitmapWidth / bitmapRatio);
                } else {
                    bitmapHeight = screenHeight;
                    bitmapWidth = (int) (bitmapHeight * bitmapRatio);
                }

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(result, bitmapWidth, bitmapHeight, true);
                imageViewPhoto.setImageBitmap(resizedBitmap);
                textImageResult.setText("Image downloaded successfully");
                textImageResult.setTextColor(Color.GREEN);

            } else {
                textImageResult.setText("Failed to download image");
                textImageResult.setTextColor(Color.RED);
            }
        }
    }
}
