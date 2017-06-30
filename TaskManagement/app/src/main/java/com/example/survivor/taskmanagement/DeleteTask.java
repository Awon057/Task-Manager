package com.example.survivor.taskmanagement;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteTask extends AppCompatActivity {


    private SQLiteDatabase sqliteDB;
    private DatabaseHelper myHelper;
    public EditText text;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        myHelper = new DatabaseHelper(DeleteTask.this,"STUDDB",null,1);
        sqliteDB = myHelper.getWritableDatabase();

        Button deleteall = (Button)findViewById(R.id.button7);
        Button delete =(Button)findViewById(R.id.button5);
        text = (EditText)findViewById(R.id.editText);

        if( text.getText().toString().length() == 0 )
            text.setError( "Id is required" );

        sid = text.getText().toString();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if( text.getText().toString().length() == 0 )
                        Toast.makeText(DeleteTask.this, "Please fill all fields" , Toast.LENGTH_LONG).show();
                    else {
                        int idnumber = Integer.parseInt(text.getText().toString());
                        if(idnumber>0){
                            sqliteDB.delete("Task","Id = "+idnumber,null);
                            Toast.makeText(DeleteTask.this, "Data of ID= "+idnumber+" Deleted", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteDB.delete("Task", null, null);
                Toast.makeText(DeleteTask.this, "All Data Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }
}
