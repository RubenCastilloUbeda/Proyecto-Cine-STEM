public class Cliente {
    private String nombreApellidos;
    private String dni;
    private int edad;
    private String tipoEntrada;
    private int numeroEntrada;
    private String butacaAsignada;
    
    public Cliente(String nombreApellidos, String dni, int edad) {
        this.nombreApellidos = nombreApellidos;
        this.dni = dni;
        this.edad = edad;
    }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad>=6 && edad <= 100) {
            this.edad = edad;
        }
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public int getNumeroEntrada() {
        return numeroEntrada;
    }

    public void setNumeroEntrada(int numeroEntrada) {
        this.numeroEntrada = numeroEntrada;
    }

    public String getButacaAsignada() {
        return butacaAsignada;
    }

    public void setButacaAsignada(String butacaAsignada) {
        this.butacaAsignada = butacaAsignada;
    }

    @Override
    public String toString() {
        return nombreApellidos + " con dni " + dni + " y edad " + edad + " años. \n";
    }

}
