package com.example.mytodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ToDoList");

        Button btnPrev, btnNext, btnAdd, startTime, endTime, btnRead, btnWrite, btnClear;
        final ViewFlipper vFlipper;
        TextView startH, startM, endH, endM, DBText;
        TimePicker tPicker;

        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        startTime = (Button) findViewById(R.id.startTime);
        endTime = (Button) findViewById(R.id.endTime);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnClear = (Button) findViewById(R.id.btnClear);
        vFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        startH = (TextView) findViewById(R.id.startH);
        startM = (TextView) findViewById(R.id.startM);
        endH = (TextView) findViewById(R.id.endH);
        endM = (TextView) findViewById(R.id.endM);
        DBText = (TextView) findViewById(R.id.DBText);
        tPicker = (TimePicker) findViewById(R.id.tPicker);
        final String[] fileName = new String[1];

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.showPrevious();
            }
        }); //??? ??????

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.showNext();
            }
        }); //??? ??????

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startH.setText(Integer.toString(tPicker.getCurrentHour()));
                startM.setText(Integer.toString(tPicker.getCurrentMinute()));
            }
        }); //?????? ??????

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endH.setText(Integer.toString(tPicker.getCurrentHour()));
                endM.setText(Integer.toString(tPicker.getCurrentMinute()));
            }
        }); //?????? ??????

        final ArrayList<String> Todolist = new ArrayList<String>();
        ListView list = (ListView) findViewById(R.id.listview);

        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,Todolist);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setAdapter(adapter);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        final EditText inputToDo = (EditText) findViewById(R.id.inputToDo);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = openFileOutput("data.txt", Context.MODE_PRIVATE);
                    outFs.close();
                }catch(IOException e){ }
            }
        }); //??? ?????? ?????? ????????????, ?????? ???????????? ???

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addedTodo = inputToDo.getText().toString();
                Todolist.add(addedTodo + System.lineSeparator() +
                        startH.getText().toString() + "??? " + startM.getText().toString() +"??? ?????? " +
                        endH.getText().toString() + "???" + endM.getText().toString() +"??? ??????");
                adapter.notifyDataSetChanged();
                //?????????????????? ????????????

                File file = new File("/data/data/com.example.mytodolist/files/data.txt");
                try {
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter writer = new BufferedWriter(fw); //?????? ??????

                    writer.write(addedTodo);
                    writer.newLine();
                    writer.close();
                } catch (IOException e) {

                }//?????? ????????? ????????? ????????????

                Toast.makeText(MainActivity.this, "?????????????????????.", Toast.LENGTH_SHORT).show();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer buffer= new StringBuffer();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("/data/data/com.example.mytodolist/files/data.txt"));

                    String str = bufferedReader.readLine();
                    while (str!=null){
                        buffer.append(str + System.lineSeparator() );
                        str = bufferedReader.readLine();
                    }

                    DBText.setText(buffer.toString());

                } catch (IOException e) {

                }

            }
        }); //???????????? ?????? ????????????

/*        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scanner scanner = null;
                try {
                    scanner = new Scanner(new File("/data/data/com.example.mytodolist/files/data.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                while (scanner.hasNext()){
                    String str = scanner.next();
                    //System.out.println(str);
                    DBText.setText(str);

                }
            }
        }); //???????????? ?????? ???????????? */

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Files.delete(Paths.get("/data/data/com.example.mytodolist/files/data.txt"));
                } catch (IOException e) {

                }
            }
        });



        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Todolist.remove(position);
                adapter.notifyDataSetChanged();

                return false;
            }
        });

    }
}