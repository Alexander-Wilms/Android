package com.example.awilms.flashlightlite;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean buttonsHidden;
    boolean textHidden;
    int currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonsHidden = false;
        textHidden = false;
        currentColor = Color.RED;

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(myHandler);

        // recovering the instance state
        if (savedInstanceState != null) {
            buttonsHidden = savedInstanceState.getBoolean("buttonsHidden");
            LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
            if(buttonsHidden) {
                buttonLayout.setVisibility(View.GONE);
            }

            currentColor = savedInstanceState.getInt("currentColor");
            TextView mytextView = (TextView) findViewById(R.id.textView);
            mytextView.setBackgroundColor(currentColor);

            textHidden = savedInstanceState.getBoolean("textHidden");
            if(textHidden) {
                mytextView.setText("");
            }
        }
    }

    public void onClick(View v) {
        TextView mytextView = (TextView) findViewById(R.id.textView);
        switch(v.getId()) {
            case R.id.W:
                mytextView.setBackgroundColor(Color.WHITE);
                currentColor = Color.WHITE;
                break;
            case R.id.B:
                mytextView.setBackgroundColor(Color.BLACK);
                currentColor = Color.BLACK;
                break;
            case R.id.R:
                mytextView.setBackgroundColor(Color.RED);
                currentColor = Color.RED;
                break;
            case R.id.Y:
                mytextView.setBackgroundColor(Color.YELLOW);
                currentColor = Color.YELLOW;
                break;
            case R.id.G:
                mytextView.setBackgroundColor(Color.GREEN);
                currentColor = Color.GREEN;
        }
        LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        buttonLayout.setVisibility(View.GONE);
        buttonsHidden = true;
    }

    View.OnClickListener myHandler = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            TextView mytextView = (TextView) findViewById(R.id.textView);
            LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

            if(buttonsHidden) {
                buttonLayout.setVisibility(View.VISIBLE);
                buttonsHidden = false;
            } else {
                buttonLayout.setVisibility(View.GONE);
                buttonsHidden = true;
            }

            if(!textHidden) {
                mytextView.setText("");
                textHidden = true;
            }
        }
    };

    //  without PersistableBundle outPersistentState parameter!
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("buttonsHidden", buttonsHidden);
        outState.putBoolean("textHidden", textHidden);
        outState.putInt("currentColor", currentColor);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
