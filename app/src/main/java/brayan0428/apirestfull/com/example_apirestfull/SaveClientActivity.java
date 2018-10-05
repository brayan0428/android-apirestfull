package brayan0428.apirestfull.com.example_apirestfull;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import brayan0428.apirestfull.com.example_apirestfull.POJOS.Client;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaveClientActivity extends AppCompatActivity {
    static EditText cedula,nombres,apellidos,email,celular;
    Button guardarCliente,cancelar;
    ProgressDialog progressDialog;
    Integer idUser = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_client);

        cedula = findViewById(R.id.cedula);
        nombres = findViewById(R.id.nombres);
        apellidos = findViewById(R.id.apellidos);
        email = findViewById(R.id.email);
        celular = findViewById(R.id.celular);
        guardarCliente = findViewById(R.id.guardarCliente);
        cancelar = findViewById(R.id.cancelarCliente);

        guardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClient();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retornar();
            }
        });

        Bundle datos = this.getIntent().getExtras();
        if (datos != null){
            idUser = datos.getInt("Id");
            cedula.setText(datos.getString("Cedula"));
            nombres.setText(datos.getString("Nombres"));
            apellidos.setText(datos.getString("Apellidos"));
            email.setText(datos.getString("Email"));
            celular.setText(datos.getString("Celular"));
        }
    }

    private void saveClient(){
        String URL = "https://nodejs-android-restfull.herokuapp.com";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services = retrofit.create(Services.class);
        String pCedula = cedula.getText().toString().trim();
        String pNombres = nombres.getText().toString().trim();
        String pApellidos = apellidos.getText().toString().trim();
        String pEmail = email.getText().toString().trim();
        String pCelular = celular.getText().toString().trim();
        if(pCedula.equals("")){
            this.MostrarMensaje("Debe ingresar el numero de Cedula");
            return;
        }
        if(pNombres.equals("")){
            this.MostrarMensaje("Debe ingresar los nombre");
            return;
        }
        if(pApellidos.equals("")){
            this.MostrarMensaje("Debe ingresar los apellido");
            return;
        }
        if(pEmail.equals("")){
            this.MostrarMensaje("Debe ingresar el email");
            return;
        }
        progressDialog = ProgressDialog.show(this,"Guardando","Espere un momento...",false,false);
        Client client = new Client((idUser.equals("")) ? 0: idUser,pCedula,pNombres,pApellidos,pEmail,pCelular);
        Call<Client> response;
        if (idUser == 0){
            response = services.saveClient(client);
        }else{
            response = services.updateClient(idUser,client);
        }
        response.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Guardado exitosamente",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Retornar();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                progressDialog.dismiss();
                MostrarMensaje(t.getMessage());
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void MostrarMensaje(String msn){
        Toast.makeText(getApplicationContext(),msn,Toast.LENGTH_LONG).show();
    }

    private void Retornar(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
