package com.example.awilms.flashlightlite;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean buttonsHidden;
    boolean textHidden;
    boolean actionBarHidden;
    int currentColor;
    final int Colors[] = {Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN};

    /**
     *
     */
    View.OnClickListener myHandler = new View.OnClickListener() {

        /**
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            TextView mytextView = (TextView) findViewById(R.id.background);
            LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

            if (buttonsHidden) {
                buttonLayout.setVisibility(View.VISIBLE);
                buttonsHidden = false;
            } else {
                buttonLayout.setVisibility(View.GONE);
                buttonsHidden = true;
            }

            if (!textHidden) {
                mytextView.setText("");
                textHidden = true;
            }

            if (actionBarHidden) {
                getSupportActionBar().show();
                actionBarHidden = false;
            } else {
                getSupportActionBar().hide();
                actionBarHidden = true;
            }
        }
    };

    /**
     * Called near the beginning of the activity lifecycle
     *
     * @param savedInstanceState A bundle which contains saved key-value pairs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonsHidden = false;
        textHidden = false;
        currentColor = Color.RED;

        TextView textView = (TextView) findViewById(R.id.background);
        textView.setOnClickListener(myHandler);

        LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

        // recovering the instance state
        if (savedInstanceState != null) {
            buttonsHidden = savedInstanceState.getBoolean("buttonsHidden");
            if (buttonsHidden) {
                buttonLayout.setVisibility(View.GONE);
            }

            currentColor = savedInstanceState.getInt("currentColor");
            TextView background = (TextView) findViewById(R.id.background);
            background.setBackgroundColor(currentColor);

            textHidden = savedInstanceState.getBoolean("textHidden");
            if (textHidden) {
                background.setText("");
            }
        } else {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            Log.i("myLog", "get visible: " + prefs.getBoolean("visible", false));
            Log.i("myLog", "get fullscreen: " + prefs.getBoolean("fullscreen", false));

            if (prefs.getBoolean("visible", false) == false) {
                buttonLayout.setVisibility(View.GONE);
                buttonsHidden = true;
            }

            if (prefs.getBoolean("fullscreen", false) == true) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
                actionBarHidden = true;
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().show();
                actionBarHidden = false;
            }
        }

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if (prefs.getBoolean("everStarted", false) == false) {
            visibilityAlert();
        }

        int startupColorIndex = prefs.getInt("StartupColor", -1);
        if(startupColorIndex != -1) {
            TextView background = (TextView) findViewById(R.id.background);
            background.setBackgroundColor(Colors[startupColorIndex]);
        }

        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean("everStarted", true);
        ed.apply();
    }

    /**
     * Inflates the menu.xml into Java objects
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * The callback method to be called when tapping on a button to
     * choose a color
     *
     * @param v The view that was clicked
     */
    public void onColorButtonClick(View v) {
        TextView mytextView = (TextView) findViewById(R.id.background);
        switch (v.getId()) {
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

    /**
     * without PersistableBundle outPersistentState parameter!
     *
     * @param outState The bundle where key-value pairs are stored
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("buttonsHidden", buttonsHidden);
        outState.putBoolean("textHidden", textHidden);
        outState.putInt("currentColor", currentColor);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when one of the items in the options menu has been selected
     *
     * @param item The menu item
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.visibility:
                visibilityAlert();
                break;
            case R.id.fullscreen:
                fullscreenAlert();
                break;
            case R.id.color:
                colorAlert();
                break;
            default:
                Log.i("myLog", "menu switch case: default case");
        }

        return super.onOptionsItemSelected(item);
    }

    public void visibilityAlert() {
        final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor ed = prefs.edit();
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Startup Button Visibility:")
                .setPositiveButton("Hidden", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ed.putBoolean("visible", false);
                        ed.apply();
                    }
                })

                .setNegativeButton("Visible", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ed.putBoolean("visible", true);
                        ed.apply();
                    }
                })

                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
        Log.i("myLog", "set 'visible': " + prefs.getBoolean("visible", true));
    }

    public void fullscreenAlert() {
        final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor ed = prefs.edit();
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Startup fullscreen mode:")
                .setPositiveButton("Disabled", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ed.putBoolean("fullscreen", false);
                        ed.apply();
                    }
                })

                .setNegativeButton("Enabled", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ed.putBoolean("fullscreen", true);
                        ed.apply();
                    }
                })

                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
        Log.i("myLog", "set 'fullscreen': " + prefs.getBoolean("fullscreen", true));
    }

    public void colorAlert() {
        final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor ed = prefs.edit();

        final View toDisplayInDialog = getLayoutInflater().inflate(R.layout.radiogroup, null);

        int startupColor = prefs.getInt("StartupColor", -1);
        if(startupColor != -1) {
            RadioGroup myRadioGroup = (RadioGroup) toDisplayInDialog.findViewById(R.id.radioGroup);
            RadioButton myRadioButton = (RadioButton) myRadioGroup.getChildAt(startupColor);
            myRadioButton.setChecked(true);
        }

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Startup Color:")
                .setView(toDisplayInDialog)
                .setPositiveButton("Set Startup Color", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RadioGroup myRadioGroup = (RadioGroup) toDisplayInDialog.findViewById(R.id.radioGroup);
                        int radioGroupId = myRadioGroup.getCheckedRadioButtonId();
                        RadioButton myCheckedButton = (RadioButton) toDisplayInDialog.findViewById(radioGroupId);
                        int index = myRadioGroup.indexOfChild(myCheckedButton);
                        Log.i("myLog", "set 'color' int: " + index);

                        ed.putInt("StartupColor", index);
                        ed.apply();

                        Log.i("myLog", "set 'color': " + prefs.getInt("StartupColor", -1));
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }
}
