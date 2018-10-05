package brayan0428.apirestfull.com.example_apirestfull.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import brayan0428.apirestfull.com.example_apirestfull.MainActivity;
import brayan0428.apirestfull.com.example_apirestfull.POJOS.Client;
import brayan0428.apirestfull.com.example_apirestfull.R;
import brayan0428.apirestfull.com.example_apirestfull.SaveClientActivity;
import brayan0428.apirestfull.com.example_apirestfull.Services;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ViewHolder> {
    Context context;
    List<Client> clientList;

    public ClientsAdapter(Context context, List<Client> clientList){
        this.context = context;
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameClient.setText(clientList.get(position).getNombres());
        holder.emailClient.setText(clientList.get(position).getEmail());
        holder.cardClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SaveClientActivity.class);
                intent.putExtra("Id",clientList.get(position).getId());
                intent.putExtra("Cedula",clientList.get(position).getCedula());
                intent.putExtra("Nombres",clientList.get(position).getNombres());
                intent.putExtra("Apellidos",clientList.get(position).getApellidos());
                intent.putExtra("Email",clientList.get(position).getEmail());
                intent.putExtra("Celular",clientList.get(position).getCelular());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Esta seguro que desea eliminar el cliente?")
                        .setTitle("Confirmación")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final ProgressDialog progressDialog = ProgressDialog.show(v.getContext(),"Elimnando","Espere un momento...",false,false);
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
                                Call<Client> response = services.deleteClient(clientList.get(position).getId());
                                response.enqueue(new Callback<Client>() {
                                    @Override
                                    public void onResponse(Call<Client> call, Response<Client> response) {
                                        if(response.isSuccessful()){
                                            progressDialog.dismiss();
                                            clientList.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(context,"Eliminado exitosamente",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Client> call, Throwable t) {
                                        Log.e("ERROR: ", t.getMessage());
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardClients;
        TextView nameClient,emailClient;
        ImageView deleteCliente;
        public ViewHolder(View v){
            super(v);
            cardClients = v.findViewById(R.id.cardClients);
            nameClient = v.findViewById(R.id.nameClient);
            emailClient = v.findViewById(R.id.emailClient);
            deleteCliente = v.findViewById(R.id.deleteClient);
        }
    }

}
