package com.example.survivor.taskmanagement;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTask extends AppCompatActivity {

    private EditText description;
    private EditText name;
    private DatabaseHelper myHelper;
    private SQLiteDatabase sqliteDB;
    private String formattedDate;
    private String sname;
    private String sdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        name = (EditText) findViewById(R.id.editText3);
        description = (EditText) findViewById(R.id.editText6);

        if( name.getText().toString().length() == 0 )
            name.setError( "Name is required" );
        if( description.getText().toString().length() == 0 )
            description.setError( "Description is required" );

        myHelper = new DatabaseHelper(CreateTask.this,"STUDDB",null,1);
        sqliteDB = myHelper.getWritableDatabase();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
    }


    public void insertData(View view) {
        try{
            if(name.getText().toString().length() == 0 || description.getText().toString().length() == 0)
                Toast.makeText(CreateTask.this, "Please fill all fields" , Toast.LENGTH_LONG).show();
            else {
                ContentValues cv = new ContentValues();
                cv.put("Name",name.getText().toString());
                cv.put("Description",description.getText().toString());
                cv.put("DateCreated",formattedDate);
                cv.put("DateModified",formattedDate);
                Long id = sqliteDB.insert("Task",null,cv);
                cv.put("Id",id);
                Toast.makeText(CreateTask.this, "Data Inserted", Toast.LENGTH_LONG).show();
                //  Toast.makeText(CreateTask.this,String.valueOf(id),Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
