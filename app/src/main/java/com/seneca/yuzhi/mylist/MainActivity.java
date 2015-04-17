package com.seneca.yuzhi.mylist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {

    String[] colourNames;

    private String myState;

    public String getState(){
        return myState;
    }
    public void setSate(String str){
        myState = str;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colourNames = getResources().getStringArray(R.array.listArray);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.activity_listview,colourNames);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
          new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  String[] values;
                  String result;
                  values = getResources().getStringArray(R.array.listValues);
                  result = values[position];
                  RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.myayout);
                  char[] ary = result.toCharArray();
                   String a =  "#" + String.copyValueOf(ary,2 ,6);
                  relativeLayout.setBackgroundColor(Color.parseColor(a));
                  setSate(a);
                  Toast.makeText(getApplicationContext(), getState(), Toast.LENGTH_SHORT).show();
              }
          }
        );
        registerForContextMenu(listView);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle() == "write colour to SDCard") {
            try {
                File myFile = new File("/sdcard/mysdfile.txt");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                myOutWriter.append(getState());
                myOutWriter.close();
                fOut.close();
                Toast.makeText(getBaseContext(),
                        "Done writing SD 'mysdfile.txt'",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        } else if (item.getTitle() == "read colour from SDCard") {
            try {
                File myFile = new File("/sdcard/mysdfile.txt");
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer += aDataRow + "\n";
                }
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.myayout);
                relativeLayout.setBackgroundColor(Color.parseColor(aBuffer.trim()));
                Toast.makeText(getBaseContext(),
                        "Done reading SD 'mysdfile.txt'",
                        Toast.LENGTH_SHORT).show();
                myReader.close();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "write colour to SDCard");
        menu.add(0, v.getId(), 0, "read colour from SDCard");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
