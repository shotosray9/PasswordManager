package com.example.shoto.passwordmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//TODO: test master password working

public class MasterPassword extends AppCompatActivity {

    EditText editText;
    Button btnMP;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_password);
        editText = findViewById(R.id.etMasterPassword);
        btnMP = findViewById(R.id.btnSaveMP);
        mydb = new DBHelper(this);

        if(mydb.numberOfRows()==0){
            btnMP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editText.getText().toString().length()!=6)
                        editText.setError("Master password must be 6 characters");
                    else {
                        saveMasterPassword("MASTER", editText.getText().toString());
                        Intent i = new Intent(MasterPassword.this, MainActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
        else{
            btnMP.setText("ENTER");
            btnMP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editText.getText().toString().equals(loadMasterPassword())){
                        Intent i = new Intent(MasterPassword.this, MainActivity.class);
                        startActivity(i);
                    }
                    else{
                        editText.setError("Incorrect Password");
                    }
                }
            });
        }
    }

    private void saveMasterPassword(String key, String value) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        //editor.apply();
    }

    private String loadMasterPassword() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String masterPassword = sharedPreferences.getString("MASTER", "");
        return masterPassword;
    }

}
