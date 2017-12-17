package com.github.alexander_wilms.primenumbers;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.mytextview);

        CharSequence output = "";

        for(int i = 2; i <= 100; i++) {
            if(prime(i)) {
                output = output + "\n" + i;
            }
        }

        tv.setText(output);
    }

    public static boolean prime(int number) {
        return checkPrime(number, number);
    }

    public static boolean checkPrime(int number, int div) {
        if(div == number) {
            return checkPrime(number, div-1);
        } else if(div == 1) {
            return true;
        } else {
            if(number % div == 0) {
                return false;
            } else {
                return checkPrime(number, div-1);
            }
        }
    }
}
