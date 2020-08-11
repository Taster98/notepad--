package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    private void writeToFile(String data,String colore, Context context, String filename) {
        //scrivo contenuto
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Errore", "Errore nella scrittura del file: " + e.toString());
        }
        //scrivo colore
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("color.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(colore);
            outputStreamWriter.close();
        }catch (IOException e){
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
            File file = new File(filename);
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
    //function to read color
    private String readColor(Context context){
        String ret ="";
        //File f = new File("color.txt");
        //leggo colore
        try {
            InputStream inputStream = context.openFileInput("color.txt");

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
            File file = new File("color.txt");
            try{
                file.createNewFile();
            }catch (IOException e1){
                Log.e("Errore", "Impossibile creare il file: " + e1.toString());
            }

        } catch (IOException e) {
            Log.e("Errore", "Impossibile leggere il file: " + e.toString());
        }
        //cancello file
        //f.delete();
        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        View v = findViewById(R.id.textfield);
        if(v == null){
            v = findViewById(R.id.quadretti);
        }
        if(v == null){
            v = findViewById(R.id.righe);
        }
        if(v == null){
            v = findViewById(R.id.plain);
        }
        String res = "";
        if(v != null){
            if(v instanceof EditText){
                res = ((EditText) v).getText().toString();
            }
        }
        int color = Color.WHITE;
        assert v != null;
        Drawable background = v.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        String strColor = String.format("#%06X", 0xFFFFFF & color);
        writeToFile(res,strColor,this,"content.txt");
        super.onPause();

    }

    @Override
    protected void onResume() {
        View v = findViewById(R.id.textfield);
        if(v == null){
            v = findViewById(R.id.quadretti);
        }
        if(v == null){
            v = findViewById(R.id.righe);
        }
        if(v == null){
            v = findViewById(R.id.plain);
        }
        String res = "";
        String color = "#FFFFFF";
        res = readFromFile(this,"content.txt");
        color = readColor(this);

        ((EditText)v).setText(res);
        ((EditText)v).setBackgroundColor(Color.parseColor(color));
        super.onResume();
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
                View oldV = findViewById(R.id.textfield);

                if(oldV == null)
                    oldV = findViewById(R.id.quadretti);
                if(oldV == null)
                    oldV = findViewById(R.id.righe);
                if(oldV == null)
                    oldV = findViewById(R.id.plain);
                if(oldV == null)
                    return true;
                ViewGroup par = (ViewGroup)oldV.getParent();
                View newV = getLayoutInflater().inflate(R.layout.blank_paper,par,false);
                int i1 = par.indexOfChild(oldV);
                par.removeViewAt(i1);
                par.addView(newV,i1);
                EditText ed = (EditText)newV.findViewById(R.id.plain);
                EditText oed = (EditText)oldV.findViewById(R.id.textfield);
                if(oed == null){
                    oed = (EditText)oldV.findViewById(R.id.plain);
                }
                if(oed==null){
                    oed = (EditText)oldV.findViewById(R.id.righe);
                }
                if(oed==null){
                    oed = (EditText)oldV.findViewById(R.id.quadretti);
                }
                String contenuto = oed.getText().toString();
                ed.setText(contenuto);
                return true;
            case R.id.square:
                oldV = findViewById(R.id.textfield);

                if(oldV == null)
                    oldV = findViewById(R.id.quadretti);
                if(oldV == null)
                    oldV = findViewById(R.id.righe);
                if(oldV == null)
                    oldV = findViewById(R.id.plain);
                if(oldV == null)
                    return true;

                par = (ViewGroup)oldV.getParent();
                newV = getLayoutInflater().inflate(R.layout.square_paper,par,false);
                i1 = par.indexOfChild(oldV);
                par.removeViewAt(i1);
                par.addView(newV,i1);
                ed = (EditText)newV.findViewById(R.id.quadretti);
                oed = (EditText)oldV.findViewById(R.id.textfield);
                if(oed == null){
                    oed = (EditText)oldV.findViewById(R.id.plain);
                }
                if(oed==null){
                    oed = (EditText)oldV.findViewById(R.id.righe);
                }
                if(oed==null){
                    oed = (EditText)oldV.findViewById(R.id.quadretti);
                }
                contenuto = oed.getText().toString();
                ed.setText(contenuto);

                return true;
            case R.id.rows:
                oldV = findViewById(R.id.textfield);

                if(oldV == null)
                    oldV = findViewById(R.id.quadretti);
                if(oldV == null)
                    oldV = findViewById(R.id.righe);
                if(oldV == null)
                    oldV = findViewById(R.id.plain);
                if(oldV == null)
                    return true;

                par = (ViewGroup)oldV.getParent();
                newV = getLayoutInflater().inflate(R.layout.row_paper,par,false);
                i1 = par.indexOfChild(oldV);
                par.removeViewAt(i1);
                par.addView(newV,i1);
                ed = (EditText)newV.findViewById(R.id.righe);
                oed = (EditText)oldV.findViewById(R.id.textfield);
                if(oed == null){
                    oed = (EditText)oldV.findViewById(R.id.plain);
                }
                if(oed==null){
                    oed = (EditText)oldV.findViewById(R.id.righe);
                }
                if(oed==null){
                    oed = (EditText)oldV.findViewById(R.id.quadretti);
                }
                contenuto = oed.getText().toString();
                ed.setText(contenuto);


                return true;
            case R.id.bianco:
                //Set an id to the layout
                View currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.plain);
                currentLayout.setBackgroundColor(Color.WHITE);
                return true;
            case R.id.giallo:
                //Set an id to the layout
                currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.plain);
                currentLayout.setBackgroundColor(Color.parseColor("#feffbc"));
                return true;
            case R.id.verde:
                //Set an id to the layout
                currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.plain);
                currentLayout.setBackgroundColor(Color.parseColor("#c3ffbc"));
                return true;
            case R.id.rosa:
                //Set an id to the layout
                currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.plain);
                currentLayout.setBackgroundColor(Color.parseColor("#ffbcfc"));
                return true;
            case R.id.azzurro:
                //Set an id to the layout
                currentLayout = findViewById(R.id.textfield);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.righe);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.quadretti);
                if(currentLayout == null)
                    currentLayout = findViewById(R.id.plain);
                currentLayout.setBackgroundColor(Color.parseColor("#bcfffc"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}