import java.util.ArrayList;

public class Pelicula {
    private String identificadorPelicula;
    private String titulo;
    private String clasificacionEdad;
    private int duracionMinutos;
    private String genero;

    public static int contador = 1;
    public final static String PREFIJO = "PLC-#";
    private static ArrayList<String> codigosPeliculas = new ArrayList<>();

    public Pelicula(String titulo, String clasificacionEdad, int duracionMinutos, String genero) {
        this.identificadorPelicula = generarCodigo();
        this.titulo = titulo;
        this.clasificacionEdad = clasificacionEdad;
        setDuracionMinutos(duracionMinutos);
        this.genero = genero;
    }

    private static String generarCodigo() {
        String nuevoCodigo = PREFIJO + contador;
        while (codigosPeliculas.contains(nuevoCodigo)) {
            contador++;
            nuevoCodigo = PREFIJO + contador;
        }
        contador++;
        return nuevoCodigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        if (duracionMinutos >= 0) {
            this.duracionMinutos = duracionMinutos;
        }
    }

    public String getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(String clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }

    public String getIdentificadorPelicula() {
        return identificadorPelicula;
    }

    public boolean setIdentificadorPelicula(String identificadorPelicula) {
        boolean correcto = true;
        if (!identificadorPelicula.startsWith(PREFIJO)) {
            identificadorPelicula = PREFIJO + identificadorPelicula;
        }
        if (codigosPeliculas.contains(identificadorPelicula)) {
            correcto = false;
        } else {
            codigosPeliculas.remove(this.identificadorPelicula);
            this.identificadorPelicula = identificadorPelicula;
            codigosPeliculas.add(identificadorPelicula);
        }
        return correcto;
    }

    @Override
    public String toString() {
        return identificadorPelicula + " - " + titulo + ". Genero: " + genero + ". Duracion " + duracionMinutos
                + " minutos. Clasificacion de edad: +" + clasificacionEdad + " años. \n";
    }

}
