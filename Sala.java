
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sala {
    private String identificadorSala;
    private int capacidadMaxima;
    private ArrayList<Proyeccion> historialProyeccionesEnSala;

    private static ArrayList<String> identificadoresCreados = new ArrayList<>();
    public static final String PREFIJO = "S-";
    public static int contadorSalas = 1;

    public Sala() {
        this.capacidadMaxima = 200;
        this.identificadorSala = generarCodigo();
        this.historialProyeccionesEnSala = new ArrayList<>();

        identificadoresCreados.add(this.identificadorSala);
    }

    public String getIdentificadorSala() {
        return identificadorSala;
    }

    public boolean setIdentificadorSala(String identificador) {
        boolean correcto = true;
        if (!identificador.startsWith(PREFIJO)) {
            identificador = PREFIJO + identificador;
        }
        if (identificadoresCreados.contains(identificador)) {
            correcto = false;
        } else {
            identificadoresCreados.remove(this.identificadorSala);
            this.identificadorSala = identificador;
            identificadoresCreados.add(identificador);
        }
        return correcto;
    }

    private static String generarCodigo() {
        String nuevoCodigo = PREFIJO + contadorSalas;
        while (identificadoresCreados.contains(nuevoCodigo)) {
            contadorSalas++;
            nuevoCodigo = PREFIJO + contadorSalas;
        }
        contadorSalas++;
        return nuevoCodigo;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public ArrayList<Proyeccion> getHistorialProyeccionesEnSala() {
        return historialProyeccionesEnSala;
    }

    public boolean cancelarProyeccion(String codigo) {
        boolean cancelada = false;
        Proyeccion proyeccionCancelar = buscarProyeccion(codigo);
        if (proyeccionCancelar != null && historialProyeccionesEnSala.contains(proyeccionCancelar)) {
            historialProyeccionesEnSala.remove(proyeccionCancelar);
            cancelada = true;
        }
        return cancelada;
    }

    public boolean programarProyeccion(Proyeccion proyeccionProgramar) {
        boolean correcto = true;
        for (int i = 0; i < historialProyeccionesEnSala.size() && correcto; i++) {
            if (historialProyeccionesEnSala.get(i).seSolapa(proyeccionProgramar)) {
                correcto = false;
            }
        }
        if (correcto) {
            historialProyeccionesEnSala.add(proyeccionProgramar);
        }
        return correcto;
    }

    public ArrayList<Proyeccion> mostrarProyecciones() {
        return getHistorialProyeccionesEnSala();
    }

    public Proyeccion buscarProyeccion(String codigo) {
        Proyeccion proyeccionBuscar = null;
        for (Proyeccion proyeccion : historialProyeccionesEnSala) {
            if (proyeccion.getCodigoProyeccion().equalsIgnoreCase(codigo)) {
                proyeccionBuscar = proyeccion;
            }
        }
        return proyeccionBuscar;
    }

    public boolean estaDisponible(LocalDateTime actual) {
        boolean disponible = true;
        for (int i = 0; i < historialProyeccionesEnSala.size() && disponible; i++) {
            if (historialProyeccionesEnSala.get(i).estaEnCurso(actual)) {
                disponible = false;
            }
        }
        return disponible;
    }

    @Override
    public String toString() {
        return "Codigo sala: " + identificadorSala + ". Capacidad para " + capacidadMaxima + " personas.";
    }
}

