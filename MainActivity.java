package com.example.bmicalculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bmicalculator.core.Health;

public class MainActivity extends AppCompatActivity {

    EditText heightText, weightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        heightText = findViewById(R.id.editTextHeight);
        weightText = findViewById(R.id.editTextWeight);
    }

    public void onCalculateButtonClick(View view) {

        double height = 0;
        double weight = 0;


        if(!heightText.getText().toString().equals("") && Double.parseDouble(heightText.getText().toString()) != 0){
            height = Double.parseDouble(heightText.getText().toString());
        }

        if(!weightText.getText().toString().equals("") && Double.parseDouble(weightText.getText().toString()) != 0){
            weight = Double.parseDouble(heightText.getText().toString());
        }

        Health health = new Health();
        double bmiResult = health.calculateBMI(height, weight);

        String resultText;

        if(bmiResult != -1){
            String bmiCat = health.determineCategory(bmiResult);
            resultText = "Your BMI index is " + String.format("%.2f",bmiResult) + "\nBMI category: "+bmiCat;


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("BMI");
            alertDialogBuilder.setMessage(resultText);
            alertDialogBuilder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    heightText.setText("");
                    weightText.setText("");
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }else{
            Toast.makeText(this, health.getErrorMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}