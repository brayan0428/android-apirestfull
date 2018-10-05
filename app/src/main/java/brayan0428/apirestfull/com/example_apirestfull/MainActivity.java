package brayan0428.apirestfull.com.example_apirestfull;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import brayan0428.apirestfull.com.example_apirestfull.Adapters.ClientsAdapter;
import brayan0428.apirestfull.com.example_apirestfull.POJOS.Client;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    static RecyclerView rvClients;
    FloatingActionButton addClient;
    static ArrayList<Client> clientsList;
    static ClientsAdapter clientsAdapter;
    static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = ProgressDialog.show(this,"Consultando","Espere un momento...",false,false);
        rvClients = findViewById(R.id.rvClients);
        addClient = findViewById(R.id.addClient);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvClients.setLayoutManager(linearLayoutManager);
        clientsList = new ArrayList<>();

        new Peticion(getApplicationContext()).execute();

        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SaveClientActivity.class);
                startActivity(intent);
            }
        });
    }

    public static class Peticion extends AsyncTask<Void,Void,Void>{
        private final Context mContext;
        public Peticion(final Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            final String URL = "https://nodejs-android-restfull.herokuapp.com";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Services services = retrofit.create(Services.class);
            Call<List<Client>> response = services.getClientsGet();
            try {
                for(Client client : response.execute().body()){
                    clientsList.add(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            clientsAdapter = new ClientsAdapter(this.mContext,clientsList);
            rvClients.setAdapter(clientsAdapter);
            progressDialog.dismiss();
        }
    }
}

