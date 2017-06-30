package com.example.survivor.taskmanagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends AppCompatActivity {

    private SQLiteDatabase sqliteDB;
    private DatabaseHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        myHelper = new DatabaseHelper(TaskList.this,"STUDDB",null,1);
        sqliteDB = myHelper.getReadableDatabase();

        TableLayout tableLayout = (TableLayout)findViewById(R.id.tablelayout);


        TableRow rowHeader = new TableRow(this);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"ID","NAME","DESCRIPTION","DATE CREATED","DATE MODIFIED"};

        for(String c:headerText){
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5,5,5,5);
           // tableLayout.setColumnShrinkable(1,true);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);
        sqliteDB.beginTransaction();

        try{
            String selectQuery = "SELECT * FROM Task";

            Cursor cursor = sqliteDB.rawQuery(selectQuery,null);
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    int id = cursor.getInt(cursor.getColumnIndex("Id"));
                    String name = cursor.getString(cursor.getColumnIndex("Name"));
                    String description = cursor.getString(cursor.getColumnIndex("Description"));
                    String currdate = cursor.getString(cursor.getColumnIndex("DateCreated"));
                    String moddate = cursor.getString(cursor.getColumnIndex("DateModified"));


                    TableRow row =new TableRow(this);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {id+"",name,description,currdate,moddate};

                    for (String text:colText){
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                      //  tableLayout.setColumnShrinkable(1,true);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);
                }
            }
            sqliteDB.setTransactionSuccessful();
        }
        catch (SQLiteException e){
            e.printStackTrace();
        }
        finally {
            sqliteDB.endTransaction();
            sqliteDB.close();
        }


    }
}
