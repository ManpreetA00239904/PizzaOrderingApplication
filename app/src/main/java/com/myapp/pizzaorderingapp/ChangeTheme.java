package com.myapp.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class ChangeTheme extends AppCompatActivity {

    RadioButton rblight, rbdark;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("PIZZAAPP", MODE_PRIVATE);
         editor = preferences.edit();
        if (preferences.getInt("theme", 0) != 0) {
            if (preferences.getInt("theme", 0) == R.style.AppTheme1) {
                setTheme(R.style.AppTheme1);
            }
            else {
                setTheme(R.style.AppTheme);
            }
        }
        else
        {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_theme);
        rblight = findViewById(R.id.rblight);
        rbdark = findViewById(R.id.rbdark);
        preferences = getSharedPreferences("PIZZAAPP", MODE_PRIVATE);
        editor = preferences.edit();
        if(preferences.getInt("theme",0)!=0)
        {
           if(preferences.getInt("theme",0)==R.style.AppTheme1)
           {
               rbdark.setChecked(true);
           }
           else
           {
               rblight.setChecked(true);
           }
        }
        else
        {
            rblight.setChecked(true);
        }

    }

    public void saveTheme(View view) {

        if (rbdark.isChecked()) {
            editor.putInt("theme", R.style.AppTheme1);

        } else if (rblight.isChecked()) {
            editor.putInt("theme", R.style.AppTheme);
        }
        editor.commit();
        Toast.makeText(this, "Theme Changed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
