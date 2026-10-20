
import java.util.ArrayList;

public class Cine {

    private String nombre;
    private String ciudad;
    private String pais;
    private String codigoIdentificador;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Sala> salas;
    private ArrayList<Cliente> clientes;

    public static int contador = 1;
    public final static String PREFIJO = "Cine #";

    public Cine(String nombre, String ciudad, String pais) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
        this.codigoIdentificador = generarCodigo();
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    private static String generarCodigo() {
        String nuevoCodigo = PREFIJO + contador;
        contador++;
        return nuevoCodigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public boolean  setCodigoIdentificador(String codigoIdentificador) {
        boolean correcto = true;
        if (!codigoIdentificador.startsWith(PREFIJO)) {
            codigoIdentificador = PREFIJO + codigoIdentificador;
        }
        return correcto;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public void setSalas(ArrayList<Sala> salas) {
        this.salas = salas;
    }

    public ArrayList<Sala> mostrarSalas() {
        return getSalas();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public void agregarSala(Sala sala){
        salas.add(sala);
    }

    public void agregarPelicula(Pelicula pelicula){
        peliculas.add(pelicula);
    }

    public void agregarCliente(Cliente cliente){
        clientes.add(cliente);
    }

    public boolean clienteDuplicado(String dni) {
    boolean duplicado = false;
    for (Cliente cliente : clientes) {
        if (cliente.getDni().equals(dni)) {
            duplicado = true;
        }
    }
    return duplicado;
}

    public Cliente buscarCliente(String dni) {
        Cliente clienteBuscar = null;
        for (Cliente cliente : clientes) {
            if (cliente.getDni().equalsIgnoreCase(dni)) {
                clienteBuscar = cliente;
            }
        }
        return clienteBuscar;
    }

    public Sala buscarSala(String codigo) {
        Sala salaBuscar=null;
        for (Sala sala : salas) {
            if (sala.getIdentificadorSala().equalsIgnoreCase(codigo)) {
                salaBuscar=sala;
            }
        }
        return salaBuscar;
    }

    public Pelicula buscarPelicula(String titulo) {
        Pelicula peliculaBuscar = null;
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                peliculaBuscar=pelicula;
            }
        }
        return peliculaBuscar;
    }

    public Sala buscarSalaDeProyeccion(String codigoProyeccion) {
    Sala salaEncontrada = null;
    for (Sala sala : salas) {
        if (sala.buscarProyeccion(codigoProyeccion) != null) {
            salaEncontrada = sala;
        }
    }
    return salaEncontrada;
}

    public int totalClientesEnProyecciones() {
        int totalClientes = 0;
        for (Sala sala : salas) {
            for (Proyeccion proyeccion : sala.getHistorialProyeccionesEnSala()) {
                totalClientes += proyeccion.obtenerClientesEnProyeccion().size();
            }
        }
        return totalClientes;
    }

    public ArrayList<Proyeccion> mostrarTodasProyecciones() {
        ArrayList<Proyeccion> totalProyecciones = new ArrayList<>();
        
        for (Sala sala : salas) {
            totalProyecciones.addAll(sala.getHistorialProyeccionesEnSala());
        }
        return totalProyecciones;
    }
    @Override
    public String toString() {
        return nombre + "-" + codigoIdentificador + ". Ubicacion:" + ciudad + "-"+ pais;
    }

}