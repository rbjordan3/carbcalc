package com.example.rob.carbcalc;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}
//package com.carb_calc;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View.OnClickListener;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCalculate = (Button)findViewById(R.id.calculate);
        buttonCalculate.setOnClickListener(CalcListener); // Register the onClick listener with the implementation above
        RadioButton mRadioButton = (RadioButton) findViewById(R.id.breakfast_ratio);
        mRadioButton.setChecked(true);

    }

    private OnClickListener CalcListener = new OnClickListener() {
        public void onClick(View v) {
            int carbs_in = 0;
            int blood_sugar = 0;
            double insulin = 0.0;

            TextView mResult = (TextView)findViewById(R.id.insulin_out);

            mResult.setInputType(InputType.TYPE_NULL);
            mResult.setTextIsSelectable(true);
            mResult.setKeyListener(null);

            // Find the current carb ratio selected (Radio Button)
            EditText mCurrentRatio;
            RadioGroup mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
            RadioButton mRadioButton;

            int selectedRatioId = mRadioGroup.getCheckedRadioButtonId();
            mRadioButton = (RadioButton)findViewById(selectedRatioId);
            Log.d("carb-calc", "Selected Radio Button: " + mRadioButton.getText());
            if ( mRadioButton.getText().toString().equals("Breakfast Ratio"))  {
                mCurrentRatio = (EditText)findViewById(R.id.breakfast_ratio_value);

            } else if ( mRadioButton.getText().toString().equals("Lunch Ratio")) {
                mCurrentRatio = (EditText)findViewById(R.id.lunch_ratio_value);

            } else {
                mCurrentRatio = (EditText)findViewById(R.id.dinner_ratio_value);
            }
            Log.d("carb-calc", "Current Ratio: " + mCurrentRatio.getText().toString());
            int ratio = Integer.valueOf(mCurrentRatio.getText().toString());
            if ( ratio == 0 ) {
                Toast.makeText( MainActivity.this, "Ratio cannot be zero", Toast.LENGTH_LONG).show();
            }

            // Get the Carbs consumed value
            EditText mCarbsIn = (EditText)findViewById(R.id.carbs_in);
            int carbsConsumed = Integer.valueOf(mCarbsIn.getText().toString());
            Log.d("carb-calc", "Carbs Consumed: " + carbsConsumed);
            if ( carbsConsumed == 0 ) {
                Toast.makeText( MainActivity.this, "Please enter a value for Carbs Consumed", Toast.LENGTH_LONG).show();
            }
            // Get the current blood sugar
            EditText mBloodSugarIn = (EditText)findViewById(R.id.bloodsugarIn);
            int currentBloodSugar = Integer.valueOf(mBloodSugarIn.getText().toString());
            Log.d("carb-calc", "Current Blood Sugar: " + currentBloodSugar);
            if ( currentBloodSugar == 0 ) {
                Toast.makeText( MainActivity.this, "No Blood Sugar was entered", Toast.LENGTH_LONG).show();
            }

            // blood_sugar = Integer.valueOf(mBlood_sugar.getText().toString());
            EditText targetBSIn = (EditText)findViewById(R.id.target_blood_sugar);
            int targetBSValue = Integer.valueOf(targetBSIn.getText().toString());
            if ( targetBSValue == 0 ){
                Toast.makeText( MainActivity.this, "Target Blood Sugar cannot be zero", Toast.LENGTH_LONG).show();
            }

            EditText mCorrectionRatio = (EditText)findViewById(R.id.correction_ratio);
            int correctionFactorRatio = Integer.valueOf(mCorrectionRatio.getText().toString());
            if ( correctionFactorRatio == 0 ){
                Toast.makeText( MainActivity.this, "Correction Factor cannot be zero", Toast.LENGTH_LONG).show();
            }
            double correction = 0;
            if ( currentBloodSugar >= targetBSValue )
                correction = ((double)currentBloodSugar - targetBSValue)/(double)correctionFactorRatio;

            if (carbsConsumed > 0 ){
                insulin = ((double)carbsConsumed / (double)ratio);
            } else {
                insulin = 0;
            }

            Log.d("carb-calc", "insulin " + insulin);
            Log.d("carb-calc", "correction " + correction);

            double result = (double)correction + (double)insulin;
            result = roundtotenths(result);
            mResult.setText(Double.toString(result));
        }
    };
    public double roundtotenths(double input)
    {
        double result2 = Math.floor(input*10+0.5)/10;
        return result2;
    }
}

