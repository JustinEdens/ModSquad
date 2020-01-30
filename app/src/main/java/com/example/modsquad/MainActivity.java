package com.example.modsquad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public static Socket socket;
    public String host = "52.33.26.31";
    public final int port = 31615;
    public static BufferedReader in= null;
    public static PrintWriter out= null;
    final Context context = this;

    //hash map for year make and models
    public static HashMap<String,HashMap<String, ArrayList<String>>> year = new HashMap<>();

    public static ArrayList<car_profile> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        communicate();

        profiles = new ArrayList<car_profile>();

        Fragment fr;
        fr = new home(); // your front page activity name
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main,fr);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fr=null;
        int id = item.getItemId();

        if (id == R.id.action_Search) {
            sendMessageToServer("current values");
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.action_Add) {
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
            fr = new add();
        }
        if (id == R.id.action_Home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            fr = new home();
        }

        if (fr!=null) {
            FragmentManager fm2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm2.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main, fr);
            fragmentTransaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    //to send
    public static void sendMessageToServer(final String str) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
                    if (!str.isEmpty()){
                        out.println(str);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //connect and receive
    public void communicate() {

        new Thread(new Runnable() {
            public void run() {

                try {
                    socket = new Socket(host, port);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    Log.d("", "unknown host*");
                } catch (IOException e) {
                    Log.d("", "io exception*");
                    e.printStackTrace();s
                }

                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                    //out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true) {
                    String msg = null;
                    try {
                        msg = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (msg == null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    else if(msg.startsWith("<")){

                        String year = msg.substring(msg.indexOf("<")+1,msg.indexOf("!"));
                        String make = msg.substring(msg.indexOf("!")+1,msg.indexOf("?"));
                        String model = msg.substring(msg.indexOf("?")+1,msg.indexOf("#"));
                        String name = msg.substring(msg.indexOf("#")+1,msg.indexOf("$"));
                        String insta = msg.substring(msg.indexOf("$")+1,msg.indexOf("%"));
                        String throttle = msg.substring(msg.indexOf("%")+1,msg.indexOf("^"));
                        String injectors = msg.substring(msg.indexOf("^")+1,msg.indexOf("&"));
                        String pulley = msg.substring(msg.indexOf("&")+1,msg.indexOf("*"));
                        String headers = msg.substring(msg.indexOf("*")+1,msg.indexOf("("));
                        String frontpipe = msg.substring(msg.indexOf("(")+1,msg.indexOf(")"));
                        String catback = msg.substring(msg.indexOf(")")+1,msg.indexOf("+"));
                        String diameter = msg.substring(msg.indexOf("+")+1,msg.indexOf(">"));

                        car_profile temp = new car_profile(year, make, model, name, insta, throttle, injectors, pulley, headers, frontpipe, catback, diameter);
                        profiles.add(temp);

                    }
                    else if (msg.equals("#1")) {
                        Fragment fr = new home();
                        FragmentManager fm2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm2.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_main, fr);
                        fragmentTransaction.commit();

                    }else if (msg.equals("[")) {
                        profiles = new ArrayList<>();

                    }else if (msg.equals("]")) {
                        Fragment pros = new profiles();
                        FragmentManager fm2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm2.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_main, pros);
                        fragmentTransaction.commit();

                    }else if (msg.equals("}")) {
                        Fragment pros = new search();
                        FragmentManager fm2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm2.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_main, pros);
                        fragmentTransaction.commit();

                    }else if (msg.startsWith("?")) {
                        String year = msg.substring(msg.indexOf("?")+1,msg.indexOf("!"));
                        String make = msg.substring(msg.indexOf("!")+1,msg.indexOf("&"));
                        String model = msg.substring(msg.indexOf("&")+1,msg.indexOf("~"));

                        addToNetwork(year,make,model);

                    } else {
                        // message is a shuffled word
                        String shuffledWord = msg;

                    }
                }  // end while

            }  // end run

        }).start();
    }

    public static void addToNetwork(String yr, String mk, String ml) {

        //if year exists
        if(year.containsKey(yr)) {
            //if make exists
            if(year.get(yr).containsKey(mk)) {
                //if model exists
                if(year.get(yr).get(mk).contains(ml)) {
                    //do nothing
                }
                //if model does not exist
                else {
                    //put array in hash map for models
                    year.get(yr).get(mk).add(ml);
                }
            }
            //if make does not exist
            else {
                //makes new hash map for new models
                ArrayList<String> modelTemp = new ArrayList();

                //put array in hash map for models
                modelTemp.add(ml);

                //put hash map in hash map for makes
                year.get(yr).put(mk, modelTemp);
            }
        }
        //else year does not exist yet
        else {
            HashMap<String,ArrayList<String>> makeTemp = new HashMap<>();

            ArrayList<String> modelTemp = new ArrayList<>();

            modelTemp.add(ml);

            makeTemp.put(mk,modelTemp);

            //puts hash map of makes into hash map of years
            year.put(yr, makeTemp);
        }
    }

}
