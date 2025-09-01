package co.com.crediya.autenticacion.model.sesion;

/**
 * Enum que define las URLs permitidas para usuarios con rol ADMINISTRADOR.
 * Los administradores tienen acceso completo a todas las funcionalidades del sistema.
 */
public enum UrlsAdministrador {
    
    // Gestión de usuarios
    USUARIO_TEST("/api/usecase/path"),
    USUARIOS1_USUARIOS("/api/api/v1/usuarios"),
    USUARIOS_USUARIOS("/api/v1/usuarios"),
    USUARIOS_LOGIN("/api/v1/login"),
    USUARIOS_AUTORIZACION("/api/v1/autorizacion"),

    SOLICITUDES_SOLICITUD("/api/v1/solicitud");


    private final String url;
    
    UrlsAdministrador(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return url;
    }
    
    /**
     * Verifica si una URL está permitida para administradores.
     */
    public static boolean isUrlPermitida(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        for (UrlsAdministrador urlPermitida : values()) {
            if (urlPermitida.url.equals(url) || url.startsWith(urlPermitida.url.replace("{id}", ""))) {
                return true;
            }
        }
        return false;
    }
}
