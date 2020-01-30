package com.example.modsquad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

public class car_profile extends Fragment {

    View view;
    Fragment pro;
    private String year;
    private String make;
    private String model;
    private String name;
    private String insta;
    private String throttle;
    private String injectors;
    private String pulley;
    private String headers;
    private String frontpipe;
    private String catback;
    private String diameter;

    public car_profile(String year, String make, String model, String name, String insta, String throttle, String injectors, String pulley, String headers, String frontpipe, String catback, String diameter){
        this.year = year;
        this.make = make;
        this.model = model;
        this.name = name;
        this.insta = insta;
        this.throttle = throttle;
        this.injectors = injectors;
        this.pulley = pulley;
        this.headers = headers;
        this.frontpipe = frontpipe;
        this.catback = catback;
        this.diameter = diameter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_car_profile, null);

        TextView carLabel,driverLabel,instaLabel,engineText,exhaustText;
        carLabel = view.findViewById(R.id.carLabel);
        driverLabel = view.findViewById(R.id.driverLabel);
        instaLabel = view.findViewById(R.id.instaLabel);
        engineText = view.findViewById(R.id.engineText);
        exhaustText = view.findViewById(R.id.exhaustText);

        String thr = "";
        String inj = "";
        String pul = "";
        String hea = "";
        String fro = "";
        String cat = "";
        String dia = "";

        carLabel.setText("Car: " +year+ " " +make+ " " +model);
        driverLabel.setText("Driver: " + name);
        instaLabel.setText("Instagram: "+ insta);
        if (!throttle.equals("Stock"))
            thr = "Throttle Body: "+throttle + "\n";
        if(!injectors.equals("Stock"))
            inj = "Injectors: "+injectors + "\n";
        if(!pulley.equals("Stock"))
            pul = "Pulley: "+pulley;
        if(!headers.equals("Stock"))
            hea = "Headers: "+headers + "\n";
        if(!frontpipe.equals("Stock"))
            fro = "Frontpipe: "+frontpipe + "\n";
        if(!catback.equals("Stock"))
            cat = "CatBack: "+catback + "\n";
        if(!diameter.equals("Stock"))
            dia = "Diameter: "+diameter;

        engineText.setText(thr+inj+pul);
        exhaustText.setText(hea+fro+cat+dia);


        return view;
    }
    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

}