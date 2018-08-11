package com.example.shoto.passwordmanager;

import android.content.DialogInterface;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;


public class PassStoreActivity extends AppCompatActivity {

    TextView password;
    EditText editText;
    Button button, btnEdit, btnDelete, btnDelCat;
    DBHelper mydb;
    RelativeLayout rlButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_store);
        password = findViewById(R.id.tvPassword);
        editText = findViewById(R.id.etPassword);
        button = findViewById(R.id.btnSave);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        rlButtons = findViewById(R.id.rlButtons);
        btnDelCat = findViewById(R.id.btnDelCat);

        mydb = new DBHelper(this);

        final String from = getIntent().getStringExtra("from");

        if(TextUtils.isEmpty(mydb.getPassword(from))){
            password.setVisibility(View.GONE);
            rlButtons.setVisibility(View.GONE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validate(editText.getText().toString())) {
                        mydb.updatePassword(from, editText.getText().toString());
                        Toast.makeText(PassStoreActivity.this, "Password added!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            });
        }
        else{
            button.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            //  password.setText(x);
            password.setText(mydb.getPassword(from));
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    password.setVisibility(View.GONE);
                    rlButtons.setVisibility(View.GONE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(validate(editText.getText().toString())) {
                                mydb.updatePassword(from, editText.getText().toString());
                                Toast.makeText(PassStoreActivity.this, "Password changed!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                    });
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog alertDialog = new AlertDialog.Builder(PassStoreActivity.this).create();
                    alertDialog.setTitle("Are you sure you want to delete this password?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mydb.updatePassword(from, "");
                            Toast.makeText(PassStoreActivity.this, "Password deleted!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //do nothing
                        }
                    });
                    alertDialog.show();
                }
            });

        }

        btnDelCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(PassStoreActivity.this).create();
                alertDialog.setTitle("Are you sure you want to delete this account?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mydb.deleteAccount(from);
                        Toast.makeText(PassStoreActivity.this, "Category deleted!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                alertDialog.show();

            }
        });

    }

    boolean validate(String password){
        AlertDialog alertDialog = new AlertDialog.Builder(PassStoreActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if(TextUtils.isEmpty(password)) {
            alertDialog.setMessage("Password cannot be empty");
            alertDialog.show();
            return false;
        }
        return true;
    }


}


