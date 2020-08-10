package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    //function to write to file
    private void writeToFile(String data, Context context, String filename) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Errore", "Errore nella scrittura del file: " + e.toString());
        }
    }
    //function to read from a file
    private String readFromFile(Context context, String filename) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                int i=0;
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    if(i!=0)
                        stringBuilder.append("\n");
                    stringBuilder.append(receiveString);
                    i++;
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            File file = new File("data.txt");
            try{
                file.createNewFile();
            }catch (IOException e1){
                Log.e("Errore", "Impossibile creare il file: " + e1.toString());
            }

        } catch (IOException e) {
            Log.e("Errore", "Impossibile leggere il file: " + e.toString());
        }

        return ret;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();

        View currentLayout = findViewById(R.id.textfield);
        if(currentLayout == null)
            currentLayout = findViewById(R.id.righe);
        if(currentLayout == null)
            currentLayout = findViewById(R.id.quadretti);

        EditText ed = (EditText) currentLayout.findViewById(R.id.textfield);
        String etxt = ed.getText().toString();
        writeToFile(etxt,this,"content.txt");
    }

    @Override
    protected void onResume() {
        super.onResume();
        View currentLayout = findViewById(R.id.textfield);
        if(currentLayout == null)
            currentLayout = findViewById(R.id.righe);
        if(currentLayout == null)
            currentLayout = findViewById(R.id.quadretti);

        EditText ed = (EditText) currentLayout.findViewById(R.id.textfield);
        ed.setText(readFromFile(this,"content.txt"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.help:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("This simple app is a text editor with supports plain paper, rowed paper and squared paper. Very useless (at least for now) since it doesn't even save the color or style chosen.");
                builder1.setCancelable(true);
                builder1.setNegativeButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            case R.id.blank:
                LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.blank_paper, null);

                // Find the ScrollView
                EditText sv = (EditText) v.findViewById(R.id.textfield);

                // Create a LinearLayout element
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);

                // Display the view
                setContentView(v);

                return true;
            case R.id.square:
                inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.square_paper, null);

                // Find the ScrollView
                sv = (EditText) v.findViewById(R.id.textfield);

                // Create a LinearLayout element
                ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);

                // Display the view
                setContentView(v);


                return true;
            case R.id.rows:
                inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.row_paper, null);

                // Find the ScrollView
                sv = (EditText) v.findViewById(R.id.textfield);

                // Create a LinearLayout element
                ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);

                // Display the view
                setContentView(v);

                return true;
            case R.id.bianco:
                //Set an id to the layout
                View currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);

                currentLayout.setBackgroundColor(Color.WHITE);
                return true;
            case R.id.giallo:
                //Set an id to the layout
                currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                currentLayout.setBackgroundColor(Color.parseColor("#feffbc"));
                return true;
            case R.id.verde:
                //Set an id to the layout
                currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                currentLayout.setBackgroundColor(Color.parseColor("#c3ffbc"));
                return true;
            case R.id.rosa:
                //Set an id to the layout
                currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                currentLayout.setBackgroundColor(Color.parseColor("#ffbcfc"));
                return true;
            case R.id.azzurro:
                //Set an id to the layout
                currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                currentLayout.setBackgroundColor(Color.parseColor("#bcfffc"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}