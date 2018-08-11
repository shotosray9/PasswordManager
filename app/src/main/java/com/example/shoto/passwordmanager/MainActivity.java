package com.example.shoto.passwordmanager;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ListView listView;
    PassAdapter passAdapter;
    DBHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();

        mydb = new DBHelper(this);
        if(mydb.numberOfRows()==0) {

            arrayList.add("Email");
            arrayList.add("School account");
            arrayList.add("Work account");
            arrayList.add("Facebook");
            arrayList.add("Instagram");
            arrayList.add("Twitter");

            for (int i = 0; i < arrayList.size(); i++) {
                mydb.insertPassword(arrayList.get(i), null);
            }
        }

        listView = findViewById(R.id.list_pass);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddAccount.class);
                startActivity(i);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        arrayList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList = mydb.getAllAccounts();
        passAdapter = new PassAdapter(this, arrayList);
        listView.setAdapter(passAdapter);
        passAdapter.notifyDataSetChanged();

        if(getIntent().getStringExtra("from")!=null){
            if(getIntent().getStringExtra("from").equalsIgnoreCase("added")){
                Snackbar.make(findViewById(R.id.mainLayout), "Added new item!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }

    }

    void setMasterPassword(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Welcome to Password Manager! Please set up a master password. You need to use it every time you open the app.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();
    }

    void requestMasterPassword(){

    }

}



//TODO:
// a. Password validations - rethink validations because everywhere has differnt rules - for now just checking for empty - thats ok i thinnk
// b. edit/delete passwords [improve store password UI] - check what to do for delete... - for now, it deletes password (aka resets to "" in database)
// c. change FAB icon to an add icon - l8r
// d. Delete accounts - done
// e. figure out how to have an overall master password
// f. Toasts indicating password added/changed/deleted - done
// g. Snackbar for new category - done
// h. Are you sure before deleting anything
// i. duplicate checking while adding category


/*
 * new TODO:
 *           - improve UI for add/display password
 *           - master password for whole app
 *           - change star to plus of FAB
 *
 * */




//        else{
//        }



//        arrayList.clear();
//        arrayList = mydb.getAllPasswords();

//        passAdapter = new PassAdapter(this, arrayList);
//        listView.setAdapter(passAdapter);
//        passAdapter.notifyDataSetChanged();



/*
*                 Snackbar.make(view, "Added new item!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
* */
