package co.com.crediya.autenticacion.model.sesion;

/**
 * Enum que define las URLs permitidas para usuarios con rol ASESOR.
 * Los asesores tienen acceso limitado a funcionalidades relacionadas con la gestión de clientes.
 */
public enum UrlsAsesor {
    
    // Gestión de usuarios
    USUARIO_TEST("/api/usecase/path"),
    USUARIOS_USUARIOS("/api/api/v1/usuarios"),
    USUARIOS_LOGIN("/api/v1/login"),

    SOLICITUDES_SOLICITUD("/api/v1/solicitud");


    private final String url;
    
    UrlsAsesor(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return url;
    }
    
    /**
     * Verifica si una URL está permitida para asesores.
     */
    public static boolean isUrlPermitida(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        for (UrlsAsesor urlPermitida : values()) {
            if (urlPermitida.url.equals(url) || url.startsWith(urlPermitida.url.replace("{id}", ""))) {
                return true;
            }
        }
        return false;
    }
}
