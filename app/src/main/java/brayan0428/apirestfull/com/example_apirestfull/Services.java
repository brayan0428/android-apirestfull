package brayan0428.apirestfull.com.example_apirestfull;

import java.util.List;

import brayan0428.apirestfull.com.example_apirestfull.POJOS.Client;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Services {
   @GET("clients")
   Call<List<Client>> getClientsGet();

   @POST("clients")
   Call<Client> saveClient(@Body Client client);

   @PUT("clients/{id}")
   Call<Client> updateClient(@Path("id") int id, @Body Client client);

   @DELETE("clients/{id}")
   Call<Client> deleteClient(@Path("id") int id);
}
