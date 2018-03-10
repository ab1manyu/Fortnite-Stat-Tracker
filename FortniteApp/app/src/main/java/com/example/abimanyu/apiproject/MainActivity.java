        package com.example.abimanyu.apiproject;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.annotation.IdRes;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.Toast;

        import java.net.HttpURLConnection;
        import java.net.URL;

public class MainActivity extends Activity {

    Button button;
    RadioGroup radioGroup;
    EditText editText;
    String platform = "";
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.id_buttonLaunch);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        editText = (EditText) findViewById(R.id.id_editTextUSERNAME);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.radioButton)
                    platform = "xbl";
                if (i == R.id.radioButton2)
                    platform = "pc";
                if (i == R.id.radioButton3)
                    platform = "psn";
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroup.getCheckedRadioButtonId()!=-1) {
                    if (!editText.getText().equals("Fortnite UserName")) {
                        String str = platform + "/" + username;
                        //new AsyncThread().execute(str);
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("TEST", str);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Make sure you select a platform and put in your username.", Toast.LENGTH_LONG).show();
                 }


                }

        });

    }
}

