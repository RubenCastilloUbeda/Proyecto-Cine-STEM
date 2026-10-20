public class Butaca {
    private String identificador;
    private boolean estado;
    private Cliente clienteAsociado;

    public Butaca(boolean estado, Cliente clienteAsociado) {
        this.identificador = null;
        this.estado = estado;
        this.clienteAsociado = clienteAsociado;
    }

    public Butaca(String identificador) {
        this.identificador = identificador;
        this.estado = true;
        this.clienteAsociado = null;
    }

    
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Cliente getClienteAsociado() {
        return clienteAsociado;
    }

    public void setClienteAsociado(Cliente clienteAsociado) {
        this.clienteAsociado = clienteAsociado;
    }

    @Override
    public String toString() {
        return "Butaca - " + identificador + ". Estado -> " + estado;
    }


}
