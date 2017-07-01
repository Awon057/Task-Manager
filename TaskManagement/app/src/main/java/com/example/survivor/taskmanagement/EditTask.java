package com.example.survivor.taskmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTask extends AppCompatActivity {

    private DatabaseHelper myHelper;
    private SQLiteDatabase sqliteDB;
    private EditText text;
    private EditText name;
    private EditText description;
    private String formattedDate;
    private String ids;
    private String names;
    private String descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);


        myHelper = new DatabaseHelper(EditTask.this,"STUDDB",null,1);
        sqliteDB = myHelper.getWritableDatabase();



        Button Update =(Button)findViewById(R.id.button8);
        text = (EditText) findViewById(R.id.editText2);
        name = (EditText) findViewById(R.id.editText7);
        description = (EditText) findViewById(R.id.editText8);

        if( text.getText().toString().length() == 0 )
            text.setError( "Id is required" );
        if( name.getText().toString().length() == 0 )
            name.setError( "Name is required" );
        if( description.getText().toString().length() == 0 )
            description.setError( "Description is required" );

        ids = text.getText().toString();
        names = name.getText().toString();
        descriptions = description.getText().toString();


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(text.getText().toString().length() == 0  || name.getText().toString().length() == 0 || description.getText().toString().length() == 0)
                        Toast.makeText(EditTask.this, "Please fill all fields" , Toast.LENGTH_LONG).show();
                    else
                    {
                        int idnumber = Integer.parseInt(text.getText().toString());
                        Cursor cusror;
                        cusror = sqliteDB.rawQuery("SELECT Id FROM Task WHERE Id = " +idnumber,null);
                        if(cusror.moveToFirst()){
                            ContentValues cv = new ContentValues();
                            cv.put("Name",name.getText().toString());
                            cv.put("Description",description.getText().toString());
                            cv.put("DateModified",formattedDate);
                            sqliteDB.update("Task", cv, "Id = "+idnumber, null);
                            Toast.makeText(EditTask.this, "Data Updated", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(EditTask.this, "Data of ID= "+idnumber+" is not available in database.Please create first", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }

        });
    }
}
