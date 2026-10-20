
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Proyeccion {
    private String codigoProyeccion;
    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFin;
    private Pelicula pelicula;

    private final int NUMEROFILAS = 10;
    private final int NUMEROCOLUMNAS = 20;
    private ArrayList<ArrayList<Butaca>> matrizButacas;

    private static ArrayList<String> codigosProyecciones = new ArrayList<>();
    public final static String PREFIJO = "P-";
    public static int contador = 1;
    public static int capacidadMaximaAsientos = 200;

    public static final ArrayList<String> LETRASBUTACAS = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"));

    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";

    public Proyeccion(Pelicula pelicula, LocalDateTime horarioInicio) {
        this.pelicula = pelicula;
        setHorarioInicio(horarioInicio);
        this.codigoProyeccion = generarCodigo();
        inicializarMatrizButacas();
        codigosProyecciones.add(this.codigoProyeccion);
    }

    public String getCodigoProyeccion() {
        return codigoProyeccion;
    }

    public boolean setCodigoProyeccion(String codigo) {
        boolean correcto = true;
        if (!codigo.startsWith(PREFIJO)) {
            codigo = PREFIJO + codigo;
        }
        if (codigosProyecciones.contains(codigo)) {
            correcto = false;
        } else {
            codigosProyecciones.remove(this.codigoProyeccion);
            this.codigoProyeccion = codigo;
            codigosProyecciones.add(codigo);
        }
        return correcto;
    }

    private static String generarCodigo() {
        String nuevoCodigo = PREFIJO + contador;
        while (codigosProyecciones.contains(nuevoCodigo)) {
            contador++;
            nuevoCodigo = PREFIJO + contador;
        }
        contador++;
        return nuevoCodigo;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
        if (pelicula != null) {
            this.horarioFin = horarioInicio.plusMinutes(pelicula.getDuracionMinutos());
        }
    }

    public LocalDateTime getHorarioFin() {
        return horarioFin;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        if (horarioInicio != null) {
            this.horarioFin = horarioInicio.plusMinutes(pelicula.getDuracionMinutos());
        }
    }

    public boolean estaEnCurso(LocalDateTime actual) {
        boolean proyeccionEnCurso = false;
        if (horarioInicio.isBefore(actual) && horarioFin.isAfter(actual)) {
            proyeccionEnCurso = true;
        }
        return proyeccionEnCurso;
    }

    public boolean seSolapa(Proyeccion proyeccionComprobar) {
        boolean enCurso = false;
        if (this.horarioInicio.isBefore(proyeccionComprobar.horarioFin)
                && this.horarioFin.isAfter(proyeccionComprobar.horarioInicio)) {
            enCurso = true;
        }
        return enCurso;
    }

    public ArrayList<Cliente> obtenerClientesEnProyeccion() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        Butaca butaca;

        for (int i = 0; i < NUMEROFILAS; i++) {
            for (int j = 0; j < NUMEROCOLUMNAS; j++) {
                butaca = matrizButacas.get(i).get(j);
                if (!butaca.getEstado() && butaca.getClienteAsociado() != null) {
                    clientes.add(butaca.getClienteAsociado());
                }
            }
        }
        return clientes;
    }

    public Cliente buscarClienteEnProyeccion(String dni) {
        Cliente clienteBuscar = null;
        ArrayList<Cliente> clientesEnProyeccion = obtenerClientesEnProyeccion();
        for (Cliente cliente : clientesEnProyeccion) {
            if (cliente.getDni().equalsIgnoreCase(dni)) {
                clienteBuscar = cliente;
            }
        }
        return clienteBuscar;
    }

    public boolean clienteDuplicadoEnProyeccion(String dni) {
        boolean duplicado = false;
        ArrayList<Cliente> clientesEnProyeccion = obtenerClientesEnProyeccion();
        for (Cliente cliente : clientesEnProyeccion) {
            if (cliente.getDni().equalsIgnoreCase(dni)) {
                duplicado = true;
            }
        }
        return duplicado;
    }

    public boolean cancelarAsistenciaAProyeccion(String dni) {
        boolean cancelada = false;
        Cliente clienteCancelar = buscarClienteEnProyeccion(dni);

        if (clienteCancelar != null) {
            if (liberarButaca(clienteCancelar)) {
                cancelada = true;
            }
        }
        return cancelada;
    }

    public void inicializarMatrizButacas() {
        String identificador;
        this.matrizButacas = new ArrayList<>();
        for (int i = 0; i < this.NUMEROFILAS; i++) {
            ArrayList<Butaca> filas = new ArrayList<>();
            for (int j = 0; j < this.NUMEROCOLUMNAS; j++) {
                identificador = (i + 1) + LETRASBUTACAS.get(j);
                Butaca butaca = new Butaca(identificador);
                filas.add(butaca);
            }
            matrizButacas.add(filas);
        }
    }

    public String mostrarMatrizButacas() {
        Butaca butaca;
        String resultado = "\n";
        resultado += "   ";
        for (int j = 0; j < NUMEROCOLUMNAS; j++) {
            resultado += " " + LETRASBUTACAS.get(j) + " ";
        }
        resultado += "\n";

        for (int i = 0; i < NUMEROFILAS; i++) {
            if (i < 9) {
                resultado += (i + 1) + "  ";
            } else {
                resultado += (i + 1) + " ";
            }

            for (int j = 0; j < NUMEROCOLUMNAS; j++) {
                butaca = matrizButacas.get(i).get(j);
                if (!butaca.getEstado()) {
                    resultado += " " + ROJO + "X" + RESET + " ";
                } else {
                    resultado += " O ";
                }
            }
            resultado += "\n";
        }
        return resultado;
    }

    public boolean asignarButaca(int fila, int columna, Cliente cliente) {
        boolean asignada = false;
        Butaca butacaAsignar;
        if (fila >= 0 && fila < NUMEROFILAS && columna >= 0 && columna < NUMEROCOLUMNAS) {
            if (cliente.getButacaAsignada() == null) {
                butacaAsignar = matrizButacas.get(fila).get(columna);
                if (butacaAsignar.getEstado()) {
                    butacaAsignar.setEstado(false);
                    butacaAsignar.setClienteAsociado(cliente);
                    cliente.setButacaAsignada(butacaAsignar.getIdentificador());
                    asignada = true;
                }
            }
        }
        return asignada;
    }

    public boolean liberarButaca(Cliente cliente) {
        boolean liberada = false;
        String parteNumero, parteLetra, codigoButaca;
        int fila, columna;
        Butaca butacaCLiente;

        if (cliente.getButacaAsignada() != null) {
            codigoButaca = cliente.getButacaAsignada().toUpperCase();
            parteNumero = codigoButaca.substring(0, codigoButaca.length() - 1);
            parteLetra = codigoButaca.substring(codigoButaca.length() - 1);
            fila = Integer.parseInt(parteNumero) - 1;
            columna = LETRASBUTACAS.indexOf(parteLetra);

            if (fila >= 0 && fila < NUMEROFILAS && columna >= 0 && columna < NUMEROCOLUMNAS) {
                butacaCLiente = matrizButacas.get(fila).get(columna);
                butacaCLiente.setEstado(true);
                butacaCLiente.setClienteAsociado(null);
                cliente.setButacaAsignada(null);
                liberada = true;
            }
        }
        return liberada;
    }

    public int asientosOcupados() {
        int totalCliente = obtenerClientesEnProyeccion().size();
        return totalCliente;
    }

    public int asientosDisponibles() {
        int disponibles = (this.NUMEROFILAS * this.NUMEROCOLUMNAS) - asientosOcupados();
        return disponibles;
    }

    @Override
    public String toString() {
        return codigoProyeccion + ": tiene programada la pelicula " + pelicula.getTitulo() + ".\n Hora de inicio -> "
                + horarioInicio + "\n Hora de fin -> " + horarioFin + "\n";
    }
}
