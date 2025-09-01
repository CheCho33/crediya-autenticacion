package co.com.crediya.autenticacion.model.sesion;

/**
 * Enum que define las URLs permitidas para usuarios con rol CLIENTE.
 * Los clientes tienen acceso limitado a funcionalidades básicas del sistema.
 */
public enum UrlsCliente {
    
    // Gestión de usuarios
    USUARIO_TEST("/api/usecase/path"),
    USUARIOS_LOGIN("/api/v1/login"),

    SOLICITUDES_SOLICITUD("/api/v1/solicitud");

    
    private final String url;
    
    UrlsCliente(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return url;
    }
    
    /**
     * Verifica si una URL está permitida para clientes.
     */
    public static boolean isUrlPermitida(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        for (UrlsCliente urlPermitida : values()) {
            if (urlPermitida.url.equals(url) || url.startsWith(urlPermitida.url.replace("{id}", ""))) {
                return true;
            }
        }
        return false;
    }
}
