package com.example.lab1t3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

// Tähän on yhdistetty kaikki lab2 tehtävät, json haetaan tehtävän 1 async taskin kautta ja tehtävät 2 ja 3 on yhdistetty yhteen.
// Projektin nimi on lab1T3 koska sitä on jatkettu simple html tehtävään, älä siis välitä siitä.
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncClass.Face {

    ArrayList<String> list;
    ListView lista;
    ArrayAdapter<String> adapter;
    String url;
    int counter;
    String name;
    EditText getName;
    EditText getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        list = new ArrayList<String>();
        lista = findViewById(R.id.lista);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        lista.setAdapter(adapter);
        findViewById(R.id.addStock).setOnClickListener(this);

        getName= findViewById(R.id.stockName);
        getId = findViewById(R.id.stockId);

        counter = 1;
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addStock) {
            // Tarkistetaan ettei kumpikaan kenttä ole tyhjä ja haetaan sitten annettua id:tä vastaavan osakkeen tiedot
            if (getName.getText().length() != 0 && getId.getText().length() != 0) {
                String id = getId.getText().toString();
                name = getName.getText().toString();
                url = "https://financialmodelingprep.com/api/company/price/" + id + "/" + "?datatype=json";

                AsyncClass task = new AsyncClass();
                task.setCallBackInterface(this);
                task.execute(url);

                getName.setText("");
                getId.setText("");
            }
        }
    }
    // Tiedot on saatu ja kirjoitetaan ne listviewiin
    @Override
    public void done(String data) {
        try {
            JSONObject json = new JSONObject(data);
            Iterator it = json.keys();

            while (it.hasNext()){
            String key = (String) it.next();
            JSONObject stock = json.getJSONObject(key);
            double stockPrice = stock.getDouble("price");

            String price = Double.toString(stockPrice);

            list.add(name + "      id: " + key + ":  Price: " + price);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }
}

