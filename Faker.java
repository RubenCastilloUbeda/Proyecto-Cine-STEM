import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Faker {

    private static final String[] NOMBRES = {
            "Juan", "Pedro", "María", "Ana", "Luis", "Sofía", "Carlos", "Laura", "Fernando", "Elena",
            "Miguel", "Isabel", "Javier", "Beatriz", "Andrés", "Carmen", "Hugo", "Valeria", "Ricardo", "Paula",
            "Gabriel", "Alejandra", "Roberto", "Manuela", "Diego", "Victoria", "Daniel", "Natalia", "Álvaro", "Marta"
    };

    private static final String[] APELLIDOS = {
            "García", "Rodríguez", "Martínez", "López", "González", "Pérez", "Fernández", "Sánchez", "Ramírez", "Torres",
            "Díaz", "Jiménez", "Moreno", "Vargas", "Rojas", "Castro", "Mendoza", "Romero", "Herrera", "Ortega",
            "Navarro", "Delgado", "Cabrera", "Reyes", "Acosta", "Campos", "Peña", "Vega", "Guerrero", "Cruz"
    };

    private static final String[] TITULOS_PELICULAS = {
            "El Padrino", "El Caballero Oscuro", "Origen", "Interstellar", "Gladiator", "Titanic", "Avatar",
            "Parásitos", "Joker", "Forrest Gump", "Cadena Perpetua", "La Lista de Schindler",
            "El Señor de los Anillos: La Comunidad del Anillo", "Harry Potter y la Piedra Filosofal",
            "Vengadores: Endgame", "Matrix", "El Rey León", "Toy Story", "Buscando a Nemo", "Coco",
            "Del Revés", "Up", "Wall-E", "La La Land", "Bohemian Rhapsody", "Pulp Fiction", "Seven",
            "El Club de la Lucha", "Gran Torino", "Blade Runner", "Blade Runner 2049", "Jurassic Park",
            "El Show de Truman", "El Sexto Sentido", "Shrek", "Frozen: El Reino del Hielo",
            "Piratas del Caribe: La Maldición de la Perla Negra", "Iron Man", "Thor", "Deadpool", "Logan",
            "Rocky", "Rambo", "Terminator", "Terminator 2: El Juicio Final"
    };

    private static final String[] GENEROS = {
            "Acción", "Comedia", "Drama", "Ciencia ficción", "Terror", "Animación", "Aventura", "Thriller"
    };

    private static final String[] CLASIFICACIONES = {
            "7", "12", "16", "18"
    };

    private static final String[] TIPOS_ENTRADA = {
            "Normal", "Reducida", "VIP"
    };

    private Faker() {
    }

    public static int entero(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static boolean booleano() {
        return entero(0, 1) == 1;
    }

    public static String textoAleatorio(String[] opciones) {
        return opciones[entero(0, opciones.length - 1)];
    }

    public static String nombre() {
        return textoAleatorio(NOMBRES);
    }

    public static String apellido() {
        return textoAleatorio(APELLIDOS);
    }

    public static String nombreCompleto() {
        return nombre() + " " + apellido() + " " + apellido();
    }

    public static int edad() {
        return entero(6, 90);
    }

    public static String dni() {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = entero(10000000, 99999999);
        char letra = letras.charAt(numero % 23);
        return numero + String.valueOf(letra);
    }

    public static String genero() {
        return textoAleatorio(GENEROS);
    }

    public static String clasificacionEdad() {
        return textoAleatorio(CLASIFICACIONES);
    }

    public static int duracionPelicula() {
        return entero(80, 180);
    }

    public static String tituloPelicula() {
        return textoAleatorio(TITULOS_PELICULAS);
    }

    public static Cliente cliente() {
        Cliente cliente = new Cliente(nombreCompleto(), dni(), edad());
        cliente.setTipoEntrada(textoAleatorio(TIPOS_ENTRADA));
        cliente.setNumeroEntrada(entero(1000, 99999));
        return cliente;
    }

    public static ArrayList<Cliente> clientes(int cantidad) {
        ArrayList<Cliente> clientes = new ArrayList<>();

        while (clientes.size() < cantidad) {
            Cliente nuevoCliente = cliente();
            if (!dniDuplicado(clientes, nuevoCliente.getDni())) {
                clientes.add(nuevoCliente);
            }
        }

        return clientes;
    }

    public static Sala sala() {
        return new Sala();
    }

    public static ArrayList<Sala> salas(int cantidad) {
        ArrayList<Sala> salas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            salas.add(sala());
        }
        return salas;
    }

    public static Pelicula pelicula() {
        return new Pelicula(tituloPelicula(), clasificacionEdad(), duracionPelicula(), genero());
    }

    public static ArrayList<Pelicula> peliculas(int cantidad) {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            peliculas.add(pelicula());
        }
        return peliculas;
    }

    public static LocalDateTime horarioProyeccion(LocalDate fecha) {
        int[] horas = {16, 18, 20, 22};
        int hora = horas[entero(0, horas.length - 1)];
        return LocalDateTime.of(fecha, LocalTime.of(hora, 0));
    }

    public static Proyeccion proyeccion(Pelicula pelicula, LocalDate fecha) {
        return new Proyeccion(pelicula, horarioProyeccion(fecha));
    }

    public static Proyeccion proyeccion(Pelicula pelicula, LocalDate fecha, int hora) {
        return new Proyeccion(pelicula, LocalDateTime.of(fecha, LocalTime.of(hora, 0)));
    }

    public static ArrayList<Proyeccion> proyeccionesParaSala(Sala sala, ArrayList<Pelicula> peliculas, LocalDate fecha) {
        ArrayList<Proyeccion> proyecciones = new ArrayList<>();
        int[] horas = {16,17,18,19,20,21,22};
        int limite = Math.min(horas.length, peliculas.size());

        for (int i = 0; i < limite; i++) {
            Proyeccion proyeccion = proyeccion(peliculas.get(i), fecha, horas[i]);
            if (sala.programarProyeccion(proyeccion)) {
                proyecciones.add(proyeccion);
            }
        }

        return proyecciones;
    }

    public static void cargarClientes(Cine cine, int cantidad) {
        while (cine.getClientes().size() < cantidad) {
            Cliente cliente = cliente();
            if (!cine.clienteDuplicado(cliente.getDni())) {
                cine.agregarCliente(cliente);
            }
        }
    }

    public static void cargarSalas(Cine cine, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            cine.agregarSala(sala());
        }
    }

    public static void cargarPeliculas(Cine cine, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            cine.agregarPelicula(pelicula());
        }
    }

    /**
     * Genera la cantidad de clientes indicada, los añade al cine y los coloca de
     * forma aleatoria en las proyecciones que ya existan en las salas.
     *
     * Si no hay proyecciones o ya no quedan asientos libres, el método deja de
     * insertar clientes.
     *
     * @param cine     cine que ya debe tener salas y proyecciones creadas
     * @param cantidad cantidad de clientes a generar y repartir
     * @return número de clientes que se han podido crear y asignar realmente
     */
    public static int generarYAsignarClientesAProyecciones(Cine cine, int cantidad) {
        int clientesAsignados = 0;

        ArrayList<Proyeccion> proyeccionesDisponibles = obtenerProyeccionesConAsientos(cine);
        if (proyeccionesDisponibles.isEmpty()) {
            
        }

        while (clientesAsignados < cantidad && !proyeccionesDisponibles.isEmpty()) {
            Cliente nuevoCliente = cliente();

            while (cine.clienteDuplicado(nuevoCliente.getDni())) {
                nuevoCliente = cliente();
            }

            Proyeccion proyeccionAleatoria = proyeccionesDisponibles.get(entero(0, proyeccionesDisponibles.size() - 1));

            boolean asignado = asignarClienteEnButacaLibre(proyeccionAleatoria, nuevoCliente);

            if (asignado) {
                cine.agregarCliente(nuevoCliente);
                clientesAsignados++;

                if (proyeccionAleatoria.asientosDisponibles() == 0) {
                    proyeccionesDisponibles.remove(proyeccionAleatoria);
                }
            } else {
                proyeccionesDisponibles.remove(proyeccionAleatoria);
            }
        }

        return clientesAsignados;
    }

    private static boolean asignarClienteEnButacaLibre(Proyeccion proyeccion, Cliente cliente) {
        int intentosMaximos = 500;
        int intentos = 0;

        while (intentos < intentosMaximos) {
            int fila = entero(0, 9);
            int columna = entero(0, 19);

            if (proyeccion.asignarButaca(fila, columna, cliente)) {
                return true;
            }

            intentos++;
        }

        return false;
    }

    private static ArrayList<Proyeccion> obtenerProyeccionesConAsientos(Cine cine) {
        ArrayList<Proyeccion> proyeccionesDisponibles = new ArrayList<>();

        for (Sala sala : cine.getSalas()) {
            for (Proyeccion proyeccion : sala.getHistorialProyeccionesEnSala()) {
                if (proyeccion.asientosDisponibles() > 0) {
                    proyeccionesDisponibles.add(proyeccion);
                }
            }
        }

        return proyeccionesDisponibles;
    }

    private static boolean dniDuplicado(ArrayList<Cliente> clientes, String dni) {
        for (Cliente cliente : clientes) {
            if (cliente.getDni().equalsIgnoreCase(dni)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rellena el cine completo con datos de prueba.
     */
    public static void rellenarCine(Cine cine, int totalSalas, int totalPeliculas, LocalDate fecha) {
        cargarSalas(cine, totalSalas);
        cargarPeliculas(cine, totalPeliculas);
        cargarProyecciones(cine, fecha);
    }

    /**
     * Programa una proyección por sala usando películas del cine.
     */
    public static void cargarProyecciones(Cine cine, LocalDate fecha) {
        ArrayList<Pelicula> peliculas = cine.getPeliculas();
        ArrayList<Sala> salas = cine.getSalas();

        if (peliculas.isEmpty() || salas.isEmpty()) {
            return;
        }

        for (int i = 0; i < salas.size(); i++) {
            Pelicula pelicula = peliculas.get(i % peliculas.size());
            Proyeccion proyeccion = proyeccion(pelicula, fecha, 18 + (i % 3) * 2);
            salas.get(i).programarProyeccion(proyeccion);
        }
    }

}
