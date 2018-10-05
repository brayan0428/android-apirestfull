package brayan0428.apirestfull.com.example_apirestfull.POJOS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {
    @SerializedName("id")
    @Expose
    int Id;
    @SerializedName("cedula")
    @Expose
    String Cedula;
    @SerializedName("nombres")
    @Expose
    String Nombres;
    @SerializedName("apellidos")
    @Expose
    String Apellidos;
    @SerializedName("email")
    @Expose
    String Email;
    @SerializedName("celular")
    @Expose
    String Celular;

    public Client(int id, String cedula, String nombres, String apellidos, String email, String celular) {
        this.Id = id;
        this.Cedula = cedula;
        this.Nombres = nombres;
        this.Apellidos = apellidos;
        this.Email = email;
        this.Celular = celular;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
