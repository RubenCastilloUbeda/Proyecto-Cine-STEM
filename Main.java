
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /**
     *
     * @param mensajeAMostrar
     */
    public static void imprimirMensaje(String mensajeAMostrar) {
        System.out.println(mensajeAMostrar);
    }

    /**
     * Lee un numero entero del usuario con control de errores
     *
     * @param mensaje Mensaje que se muestra al usuario
     * @param sc      Scanner ya creado en el main
     * @return numero entero valido introducido por el usuario Si esta funcion
     *         va con un control de errores de un parametro minimo y maximo debo
     *         pasarle
     *         los rangos como parametros
     */
    public static int leerInt(String mensaje, Scanner sc) {
        int numero = 0;
        boolean correcto = false;

        while (!correcto) {
            try {
                imprimirMensaje(mensaje);
                numero = Integer.parseInt(sc.nextLine());
                correcto = true;
            } catch (NumberFormatException e) {
                imprimirMensaje("Error: introduce un numero entero valido");
            }
        }
        return numero;
    }

    /**
     * Lee un numero decimal del usuario con control de errores
     *
     * @param mensaje Mensaje que se muestra al usuario
     * @param sc      Scanner ya creado en el main
     * @return numero double valido introducido por el usuario Si esta funcion
     *         va con un control de errores de un parametro minimo y maximo debo
     *         pasarle
     *         los rangos como parametros
     */
    public static double leerDouble(String mensaje, Scanner sc) {
        double numero = 0;
        boolean correcto = false;

        while (!correcto) {
            try {
                imprimirMensaje(mensaje);
                numero = Double.parseDouble(sc.nextLine());
                correcto = true;
            } catch (NumberFormatException e) {
                imprimirMensaje("Error: introduce un numero decimal valido");
            }
        }
        return numero;
    }

    /**
     * Lee una opcion valida de un menu
     *
     * @param mensaje Mensaje del menu a mostrar
     * @param min     Valor minimo permitido
     * @param max     Valor maximo permitido
     * @param sc      Scanner ya creado en el main
     * @return opcion valida elegida por el usuario
     */
    public static int leerOpcionMenu(String mensaje, int min, int max, Scanner sc) {
        int opcion;
        boolean correcta = false;

        do {
            imprimirMensaje(mensaje);
            opcion = leerInt("Elige una opcion:", sc);

            if (opcion >= min && opcion <= max) {
                correcta = true;
            } else {
                imprimirMensaje("Error: la opcion debe estar entre " + min + " y " + max);
            }
        } while (!correcta);
        return opcion;
    }

    /**
     * Muestra un ArrayList de Strings numerado por pantalla
     *
     * @param mensaje Texto que se muestra antes del listado
     * @param array   ArrayList de Strings a mostrar
     */
    public static void mostrarArrayString(String mensaje, ArrayList<?> array, String valor) {
        imprimirMensaje(mensaje);
        for (int i = 0; i < array.size(); i++) {
            System.out.println(valor + (i + 1) + " : " + array.get(i));
        }
    }

    /**
     * Se utiliza para saber si una sala esta disponible, solicitando un cine y
     * pasandole sc para preguntar al usuario. Busca una sala y usa
     * estaDisponible para ver la disponibilidad con la hora actual
     *
     * @param cine
     * @param sc
     * @return devuelve mensaje en funcion de lo que ocurra
     */
    public static String consultarDisponibilidadSala(Cine cine, Scanner sc) {
        String mensaje, codigoBuscar;
        Sala salaComprobar;

        mostrarArrayString("==== Salas ====", cine.mostrarSalas(), "Sala-> ");
        imprimirMensaje("Indica el codigo de una sala");
        codigoBuscar = sc.nextLine();
        salaComprobar = cine.buscarSala(codigoBuscar);
        if (salaComprobar != null) {
            if (salaComprobar.estaDisponible(LocalDateTime.now())) {
                mensaje = "Sala disponible ahora mismo";
            } else {
                mensaje = "Sala ocupada actualmente";
            }
        } else {
            mensaje = "Sala no encontrada";
        }
        return mensaje;
    }

    /**
     * Se utiliza para programa una proyeccion en sala, pidiendo una sala, una
     * pelicula y una fecha y hora. Comprueba si se sala y pelicula existen y al
     * programar la proyecion comprueba que no se solape con otra.
     *
     * @param cine
     * @param sc
     * @return mensaje en funcion del error o si todo es correcto
     */
    public static String programarProyeccionEnSala(Cine cine, Scanner sc) {
        String mensaje, codigoBuscar, tituloPeliculaBuscar;
        Sala salaComprobar;
        Pelicula peliculaBuscar;
        Proyeccion nuevaProyeccion;
        int hora, minuto, dia, mes, anio;
        LocalDateTime horaInicio;

        mostrarArrayString("==== Salas ====", cine.mostrarSalas(), "Sala-> ");
        imprimirMensaje("Indica el codigo de una sala");
        codigoBuscar = sc.nextLine();
        salaComprobar = cine.buscarSala(codigoBuscar);
        if (salaComprobar == null) {
            mensaje = "La sala no existe";
        } else {
            mostrarArrayString("==== Peliculas ====", cine.getPeliculas(), "Pelicula-> ");
            imprimirMensaje("Indique el titulo de la pelicula");
            tituloPeliculaBuscar = sc.nextLine();
            peliculaBuscar = cine.buscarPelicula(tituloPeliculaBuscar);
            if (peliculaBuscar == null) {
                mensaje = "La pelicula no existe";
            } else {
                // posible funcion para pedir la fecha de programacion y comprobar fecha y
                // minutos correctos dentro de unos limites (?)
                imprimirMensaje("Indique la hora de inicio");
                hora = leerInt("Hora", sc);
                imprimirMensaje("Indique el minuto de inicio");
                minuto = leerInt("Minuto", sc);
                imprimirMensaje("Indique el dia de inicio");
                dia = leerInt("Dia", sc);
                imprimirMensaje("Indique el mes de inicio");
                mes = leerInt("Mes", sc);
                imprimirMensaje("Indique el año de inicio");
                anio = leerInt("Año", sc);
                horaInicio = LocalDateTime.of(anio, mes, dia, hora, minuto);
                nuevaProyeccion = new Proyeccion(peliculaBuscar, horaInicio);
                if (salaComprobar.programarProyeccion(nuevaProyeccion)) {
                    mensaje = "Proyeccion programada correctamente.";
                } else {
                    mensaje = "No se puede programar porque el horario se solapa con otra";
                }
            }
        }
        return mensaje;
    }

    /**
     * Sirve para cancelar una proyeccion, pidieendo al usuario el codigo de la
     * proyeccion, comprobando que exista y buscando la sala en la que esta
     * programada.
     * 
     * @param cine
     * @param sc
     * @return devuelve un mensaje de error segun el error encontrado o de que todo
     *         ha ido bien.
     */
    public static String cancelarProyeccion(Cine cine, Scanner sc) {
        String mensaje, codigoBuscar;
        Sala salaComprobar;
        Proyeccion proyeccionBuscar;

        mostrarArrayString("===== Proyecciones =====", cine.mostrarTodasProyecciones(), "Proyeccion -> ");
        imprimirMensaje("Indica el codigo de la proyeccion a cancelar");
        codigoBuscar = sc.nextLine();
        salaComprobar = cine.buscarSalaDeProyeccion(codigoBuscar);
        if (salaComprobar == null) {
            mensaje = "Proyeccion no encontrada";
        } else {
            proyeccionBuscar = salaComprobar.buscarProyeccion(codigoBuscar);
            LocalDateTime horaActual = LocalDateTime.now();
            if (horaActual.isAfter(proyeccionBuscar.getHorarioFin())) {
                mensaje = "No se puede cancelar una proyeccion ya finalizada";
            } else {
                if (salaComprobar.cancelarProyeccion(codigoBuscar)) {
                    mensaje = "Proyeccion cancelada correctamente";
                } else {
                    mensaje = "No se ha podido cancelar la proyeccion";
                }
            }
        }
        return mensaje;
    }

    /**
     * Busca un objeto y devuelve su informacion en toString
     * 
     * @param cine
     * @param sc
     * @return devuelve la informacion del objeto si lo encuentra o mensaje error
     */
    public static String buscarObjetoYMostrarInformacion(Cine cine, Scanner sc) {
        String mensaje, tituloPeliculaBuscar;
        Pelicula peliculaBuscar;

        imprimirMensaje("Indica el titulo de la pelicula");
        tituloPeliculaBuscar = sc.nextLine();
        peliculaBuscar = cine.buscarPelicula(tituloPeliculaBuscar);
        if (peliculaBuscar != null) {
            mensaje = peliculaBuscar.toString();
        } else {
            mensaje = "La pelicula no existe en nuestra base de datos";
        }
        return mensaje;
    }

    /**
     * Pide un titulo de pelicula, busca que exista, comprueba las proyecciones en
     * las que este esa pelicula y devuelve un arrayList con
     * las proyecciones donde aparece.
     * 
     * @param cine
     * @param sc
     */
    public static void mostrarProyeccionesDePelicula(Cine cine, Scanner sc) {
        String tituloPeliculaBuscar;
        Pelicula peliculaBuscar;
        ArrayList<Proyeccion> peliculaEnProyecciones = new ArrayList<>();

        mostrarArrayString("==== Peliculas ====", cine.getPeliculas(), "Pelicula-> ");
        imprimirMensaje("Indica el titulo de la pelicula");
        tituloPeliculaBuscar = sc.nextLine();
        peliculaBuscar = cine.buscarPelicula(tituloPeliculaBuscar);
        if (peliculaBuscar == null) {
            imprimirMensaje("La pelicula no existe en nuestra base de datos");
        } else {
            for (Sala sala : cine.getSalas()) {
                for (int i = 0; i < sala.getHistorialProyeccionesEnSala().size(); i++) {
                    if (sala.getHistorialProyeccionesEnSala().get(i).getPelicula()
                            .equals(peliculaBuscar)) {
                        peliculaEnProyecciones.add(sala.getHistorialProyeccionesEnSala().get(i));
                    }
                }
            }
            if (peliculaEnProyecciones.isEmpty()) {
                imprimirMensaje("No hay proyecciones registradas para esta pelicula");
            } else {
                for (Proyeccion proyeccion : peliculaEnProyecciones) {
                    imprimirMensaje(proyeccion.toString());
                }
            }
        }
    }

    /**
     * Pide un codigo de proyeccion, busca que exista, pide un dni de cliente, busca
     * que exista y que no este registrado en la proyeccion, muestra la matriz de
     * butacas y pide una fila y columna para asignar la butaca al cliente.
     * Comprueba que la fila y columna sean validas y que la butaca este libre.
     * 
     * @param cine
     * @param sc
     * @return devuelve un mensaje de error segun el error encontrado o de que todo
     *         ha ido bien.
     */
    public static String registrarClienteEnProyeccionConButaca(Cine cine, Scanner sc) {
        String mensaje, codigoBuscar, dniCliente, letraButaca, nombreCliente;
        int edadCliente, filaButaca, fila, columna;

        Sala salaComprobar;
        Proyeccion proyeccionBuscar;
        Cliente clienteRegistrar;

        mostrarArrayString("===== Proyecciones =====", cine.mostrarTodasProyecciones(), "Proyeccion -> ");
        imprimirMensaje("Indique el codigo de la proyeccion");
        codigoBuscar = sc.nextLine();
        salaComprobar = cine.buscarSalaDeProyeccion(codigoBuscar);

        if (salaComprobar == null) {
            mensaje = "La proyeccion no existe";
        } else {
            proyeccionBuscar = salaComprobar.buscarProyeccion(codigoBuscar);
            if (proyeccionBuscar.getHorarioFin().isBefore(LocalDateTime.now())) {
                mensaje = "La proyeccion ya ha finalizado";
            } else {
                dniCliente=leerDNI("Indique el dni del cliente", sc);
                imprimirMensaje("Indique el nombre del cliente a registrar en la proyeccion");
                nombreCliente = sc.nextLine();
                edadCliente = leerInt("Indique la edad del cliente a registrar en la proyeccion", sc);
                clienteRegistrar = new Cliente(nombreCliente, dniCliente, edadCliente);

                if (proyeccionBuscar.clienteDuplicadoEnProyeccion(dniCliente)) {
                    mensaje = "El cliente ya esta registrado en esta proyeccion";
                } else if (proyeccionBuscar.asientosDisponibles() == 0) {
                    mensaje = "No hay asientos disponibles. Se ha alcanzado la capacidad maxima";
                } else {
                    imprimirMensaje(proyeccionBuscar.mostrarMatrizButacas());
                    filaButaca = leerInt("Indique el numero de fila de la butaca:", sc);
                    imprimirMensaje("Indique la letra de la butaca:");
                    letraButaca = sc.nextLine().toUpperCase();
                    fila = filaButaca - 1;
                    columna = Proyeccion.LETRASBUTACAS.indexOf(letraButaca);

                    if (fila < 0 || fila >= 10) {
                        mensaje = "Fila no valida";
                    } else if (columna == -1) { // si sale -1 es que no ha asignado un numero del array de LETRASBUTACAS
                        mensaje = "Letra de butaca no valida";
                    } else {
                        if (proyeccionBuscar.asignarButaca(fila, columna, clienteRegistrar)) {
                            mensaje = "Cliente registrado correctamente en la proyeccion. Butaca asignada: "
                                    + clienteRegistrar.getButacaAsignada();
                        } else {
                            mensaje = "La butaca seleccionada ya esta ocupada";
                        }
                    }
                }
            }
        }
        return mensaje;
    }

    /**
     * Pide un codigo de proyeccion, busca que exista, pide un dni de cliente, busca
     * que exista y que este registrado en la proyeccion, cancela la asistencia del
     * cliente a la proyeccion y libera la butaca.
     * Comprueba que la proyeccion no haya finalizado para permitir la cancelacion.
     * 
     * @param cine
     * @param sc
     * @return devuelve un mensaje de error segun el error encontrado o de que todo
     *         ha ido bien.
     */
    public static String cancelarAsistenciaDeClienteAProyeccion(Cine cine, Scanner sc) {
        String codigoProyeccion, dniCliente, mensaje;
        Sala salaComprobar;
        Proyeccion proyeccionBuscar;

        mostrarArrayString("===== Proyecciones =====", cine.mostrarTodasProyecciones(), "Proyeccion -> ");
        imprimirMensaje("Indique el codigo de la proyeccion");
        codigoProyeccion = sc.nextLine();
        salaComprobar = cine.buscarSalaDeProyeccion(codigoProyeccion);

        if (salaComprobar == null) {
            mensaje = "La proyeccion no existe";
        } else {
            proyeccionBuscar = salaComprobar.buscarProyeccion(codigoProyeccion);
            if (proyeccionBuscar.getHorarioFin().isBefore(LocalDateTime.now())) {
                mensaje = "No se puede cancelar la asistencia porque la proyeccion ya ha finalizado";
            } else {
                dniCliente=leerDNI("Indique el dni del cliente a cancelar", sc);
                if (!proyeccionBuscar.clienteDuplicadoEnProyeccion(dniCliente)) {
                    mensaje = "Cliente no registrado en la proyeccion";
                } else {
                    if (proyeccionBuscar.cancelarAsistenciaAProyeccion(dniCliente)) {
                        mensaje = "Asistencia cancelada correctamente. Butaca libre de nuevo.";
                    } else {
                        mensaje = "No se ha podido cancelar la asistencia";
                    }
                }
            }
        }
        return mensaje;
    }

    /**
     * Pide un codigo de proyeccion, busca que exista y muestra la lista de clientes
     * registrados en esa proyeccion. Comprueba que la proyeccion exista y que haya
     * clientes registrados para mostrarla.
     * 
     * @param cine
     * @param sc
     */
    public static void mostrarClientesEnProyeccion(Cine cine, Scanner sc) {
        String codigoBuscar;
        Sala salaComprobar;
        Proyeccion proyeccionBuscar;
        ArrayList<Cliente> clientesMostrar;

        mostrarArrayString("===== Proyecciones =====", cine.mostrarTodasProyecciones(), "Proyeccion -> ");
        imprimirMensaje("Indique el codigo de la proyeccion");
        codigoBuscar = sc.nextLine();
        salaComprobar = cine.buscarSalaDeProyeccion(codigoBuscar);
        if (salaComprobar == null) {
            imprimirMensaje("La proyeccion no existe");
        } else {
            proyeccionBuscar = salaComprobar.buscarProyeccion(codigoBuscar);
            clientesMostrar = proyeccionBuscar.obtenerClientesEnProyeccion();
            if (clientesMostrar.isEmpty()) {
                imprimirMensaje("No hay clientes registrados aún en la proyeccion");
            } else {
                imprimirMensaje("Listado de clientes en la proyeccion " + codigoBuscar + ":\n");
                for (Cliente cliente : clientesMostrar) {
                    imprimirMensaje(cliente.toString());
                }
            }
        }
    }

    /**
     * Muestra las proyecciones programadas en cada sala de un cine, recorriendo las
     * salas y mostrando su informacion y las proyecciones programadas en cada una.
     * @param cine
     */
    public static void  moostrarProyeccionesEnSala(Cine cine){
        System.out.println("\n Salas Disponibles-> \n");
        for (Sala sala : cine.getSalas()) {
            imprimirMensaje(sala.toString());
            for (Proyeccion proyeccion : sala.getHistorialProyeccionesEnSala()) {
                imprimirMensaje(proyeccion.toString());
            }
        }
    }

    /**
     * Valida que un dni tenga el formato correcto de 8 numeros y una letra al final.
     * @param dni
     * @return true si el formato es correcto, false si no lo es.
     */
    public static boolean validarDNI(String dni) {
        boolean correcto = true;

        if (dni.length() != 9) {
            correcto = false;
        } else {
            for (int i = 0; i < 8 && correcto; i++) {
                if (dni.charAt(i) < '0' || dni.charAt(i) > '9') {
                    correcto = false;
                }
            }
            char ultimaLetra = dni.charAt(8);
            if (!((ultimaLetra >= 'A' && ultimaLetra <= 'Z') || (ultimaLetra >= 'a' && ultimaLetra <= 'z'))) {
                correcto = false;
            }
        }
        return correcto;
    }
    
    /**
     *  Pide un dni al usuario, valida que tenga el formato correcto y lo devuelve en mayusculas.
     * @param mensaje
     * @param sc
     * @return dni valido introducido por el usuario en mayusculas
     */
    public static String leerDNI(String mensaje, Scanner sc){
        String dni;
        boolean correcto=false;

        do {
            imprimirMensaje(mensaje);
            dni=sc.nextLine();
            if (validarDNI(dni)) {
                correcto=true;
            } else {
                imprimirMensaje("Error, formato del dni incorrecto");
            }
        } while (!correcto);
        return dni.toUpperCase();
    }

    // =========================================================================================
    // MAIN
    // =============================================================================================
    public static void main(String[] args) {

        Cine nuevoCine = new Cine("Stemos Cinema", "Granada", "España");

        Faker.rellenarCine(nuevoCine, 3, 6, LocalDate.now());
        Faker.cargarProyecciones(nuevoCine, LocalDate.now());
        Faker.cargarProyecciones(nuevoCine, LocalDate.now());
        Faker.cargarProyecciones(nuevoCine, LocalDate.now());

        Faker.generarYAsignarClientesAProyecciones(nuevoCine, 50);

        Scanner sc = new Scanner(System.in);
        final String MENUPRINCIPAL = "=============== MENU PRINCIPAL =============== \n 1.Gestionar Cines \n 2.Gestionar Salas \n 3.Gestionar Peliculas \n 4.Gestionar Clientes \n 5.Salir";
        final String MENUCINES = "=============== MENU CINES =============== \n 1.Ver detalles del cine \n 2.Volver al menu principal";
        final String MENUSALAS = "=============== MENU SALAS =============== \n 1.Listar Salas \n 2.Consultar disponibilidad de una sala \n 3.Programar pelicula \n 4.Cancelar proyeccion \n 5.Volver al menu principal";
        final String MENUPELICULAS = "=============== MENU PELICULAS =============== \n 1.Listar Peliculas \n 2.Ver detalles de pelicula \n 3.Ver proyecciones de pelicula \n 4.Volver al menu principal";
        final String MENUCLIENTES = "=============== MENU CLIENTES =============== \n 1.Registrar cliente en proyeccion \n 2.Cancelar asistencia \n 3.Ver lista clientes en proyeccion \n 4.Ver total de asistentes \n 5.Volver al menu principal";

        boolean salir = false;
        int opcionUsuarioPrincipal = 0, opcionUsuarioSecundaria = 0;

        while (!salir) {
            imprimirMensaje("\n");
            opcionUsuarioPrincipal = leerOpcionMenu(MENUPRINCIPAL, 1, 5, sc);
            // ================ GESTION CINE =======================
            if (opcionUsuarioPrincipal == 1) {
                while (opcionUsuarioSecundaria != 2) {
                    opcionUsuarioSecundaria = leerOpcionMenu(MENUCINES, 1, 2, sc);
                    if (opcionUsuarioSecundaria == 1) {
                        imprimirMensaje(nuevoCine.toString());
                    }
                }
                // ================ GESTION SALAS =======================
            } else if (opcionUsuarioPrincipal == 2) {
                while (opcionUsuarioSecundaria != 5) {
                    imprimirMensaje("\n");
                    opcionUsuarioSecundaria = leerOpcionMenu(MENUSALAS, 1, 5, sc);
                    if (opcionUsuarioSecundaria == 1) {
                        moostrarProyeccionesEnSala(nuevoCine);
                    } else if (opcionUsuarioSecundaria == 2) {
                        imprimirMensaje(consultarDisponibilidadSala(nuevoCine, sc));
                    } else if (opcionUsuarioSecundaria == 3) {
                        imprimirMensaje(programarProyeccionEnSala(nuevoCine, sc));
                    } else if (opcionUsuarioSecundaria == 4) {
                        imprimirMensaje(cancelarProyeccion(nuevoCine, sc));
                    }
                }
                // ================ GESTION PELICULAS =======================
            } else if (opcionUsuarioPrincipal == 3) {
                while (opcionUsuarioSecundaria != 4) {
                    imprimirMensaje("\n");
                    opcionUsuarioSecundaria = leerOpcionMenu(MENUPELICULAS, 1, 4, sc);
                    if (opcionUsuarioSecundaria == 1) {
                        if (nuevoCine.getPeliculas().isEmpty()) {
                            imprimirMensaje("No hay peliculas registradas");
                        } else {
                            mostrarArrayString("Peliculas:", nuevoCine.getPeliculas(), "Pelicula");
                        }
                    } else if (opcionUsuarioSecundaria == 2) {
                        imprimirMensaje(buscarObjetoYMostrarInformacion(nuevoCine, sc));
                    } else if (opcionUsuarioSecundaria == 3) {
                        mostrarProyeccionesDePelicula(nuevoCine, sc);
                    }
                }
                // ================ GESTION CLIENTES =======================
            } else if (opcionUsuarioPrincipal == 4) {
                while (opcionUsuarioSecundaria != 5) {
                    imprimirMensaje("\n");
                    opcionUsuarioSecundaria = leerOpcionMenu(MENUCLIENTES, 1, 5, sc);
                    if (opcionUsuarioSecundaria == 1) {
                        imprimirMensaje(registrarClienteEnProyeccionConButaca(nuevoCine, sc));
                    } else if (opcionUsuarioSecundaria == 2) {
                        imprimirMensaje(cancelarAsistenciaDeClienteAProyeccion(nuevoCine, sc));
                    } else if (opcionUsuarioSecundaria == 3) {
                        mostrarClientesEnProyeccion(nuevoCine, sc);
                    } else if (opcionUsuarioSecundaria == 4) {
                        imprimirMensaje("Numero total de clientes registrados en todas las proyecciones: "
                                + nuevoCine.totalClientesEnProyecciones());
                    }
                }
                // ================ SALIR =======================
            } else {
                salir = true;
            }
        }
    }
}
