package com.example.shoto.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddAccount extends AppCompatActivity {

    DBHelper mydb;
    TextInputEditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        mydb = new DBHelper(this);

        editText = findViewById(R.id.etAccount);
        button = findViewById(R.id.btnAddAccount);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mydb.getAllAccounts().contains(editText.getText().toString())) {
                    mydb.insertPassword(editText.getText().toString(), "");
                    Intent i = new Intent(AddAccount.this, MainActivity.class);
                    i.putExtra("from", "added");
                    startActivity(i);
                }

                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(AddAccount.this).create();
                    alertDialog.setTitle("This account already exists.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
//                onBackPressed();
            }
        });

    }
}
