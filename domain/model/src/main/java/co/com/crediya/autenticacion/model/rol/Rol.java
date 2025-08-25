package co.com.crediya.autenticacion.model.rol;

import co.com.crediya.autenticacion.model.valueobjects.DescripcionRol;
import co.com.crediya.autenticacion.model.valueobjects.NombreRol;

/**
 * Entidad que representa un Rol en el sistema de autenticación CrediYa.
 * Un rol define los permisos y responsabilidades que puede tener un usuario.
 * 
 * Esta entidad es inmutable y protege sus invariantes a través de Value Objects.
 * Según las especificaciones del microservicio, los roles principales son:
 * - ADMIN: Administrador del sistema
 * - ASESOR: Asesor financiero
 * - CLIENTE: Cliente del sistema
 */
public final class Rol {
    
    private final RolId id;
    private final NombreRol nombre;
    private final DescripcionRol descripcion;
    private final long version;
    
    /**
     * Constructor privado para crear una instancia de Rol.
     * 
     * @param id Identificador único del rol
     * @param nombre Nombre del rol
     * @param descripcion Descripción del rol
     * @param version Versión para control de concurrencia optimista
     */
    private Rol(RolId id, NombreRol nombre, DescripcionRol descripcion, long version) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.version = version;
        validateInvariants();
    }
    
    /**
     * Crea un nuevo rol con los datos proporcionados.
     * 
     * @param id Identificador único del rol
     * @param nombre Nombre del rol
     * @param descripcion Descripción del rol
     * @return Nueva instancia de Rol
     */
    public static Rol create(RolId id, NombreRol nombre, DescripcionRol descripcion) {
        return new Rol(id, nombre, descripcion, 0L);
    }
    
    /**
     * Crea un rol a partir de datos existentes (para reconstrucción desde persistencia).
     * 
     * @param id Identificador único del rol
     * @param nombre Nombre del rol
     * @param descripcion Descripción del rol
     * @param version Versión actual
     * @return Nueva instancia de Rol
     */
    public static Rol from(RolId id, NombreRol nombre, DescripcionRol descripcion, long version) {
        return new Rol(id, nombre, descripcion, version);
    }
    
    /**
     * Actualiza la descripción del rol.
     * 
     * @param nuevaDescripcion Nueva descripción del rol
     * @return Nueva instancia de Rol con la descripción actualizada
     */
    public Rol updateDescripcion(DescripcionRol nuevaDescripcion) {
        return new Rol(this.id, this.nombre, nuevaDescripcion, this.version);
    }
    
    /**
     * Actualiza el nombre del rol.
     * 
     * @param nuevoNombre Nuevo nombre del rol
     * @return Nueva instancia de Rol con el nombre actualizado
     */
    public Rol updateNombre(NombreRol nuevoNombre) {
        return new Rol(this.id, nuevoNombre, this.descripcion, this.version);
    }
    
    /**
     * Marca el rol como persistido con una nueva versión.
     * 
     * @param newVersion Nueva versión
     * @return Nueva instancia de Rol con la versión actualizada
     */
    public Rol markPersisted(long newVersion) {
        if (newVersion <= version) {
            throw new IllegalArgumentException("La nueva versión debe ser mayor que la actual");
        }
        return new Rol(this.id, this.nombre, this.descripcion, newVersion);
    }
    
    /**
     * Verifica si el rol tiene un nombre específico (ignorando mayúsculas/minúsculas).
     * 
     * @param nombreRol Nombre a verificar
     * @return true si el nombre coincide
     */
    public boolean hasNombre(NombreRol nombreRol) {
        return this.nombre.equalsIgnoreCase(nombreRol);
    }
    
    /**
     * Verifica si el rol es administrador.
     * 
     * @return true si es rol de administrador
     */
    public boolean isAdmin() {
        return this.nombre.equalsIgnoreCase(NombreRol.of("ADMIN"));
    }
    
    /**
     * Verifica si el rol es asesor.
     * 
     * @return true si es rol de asesor
     */
    public boolean isAsesor() {
        return this.nombre.equalsIgnoreCase(NombreRol.of("ASESOR"));
    }
    
    /**
     * Verifica si el rol es cliente.
     * 
     * @return true si es rol de cliente
     */
    public boolean isCliente() {
        return this.nombre.equalsIgnoreCase(NombreRol.of("CLIENTE"));
    }
    
    /**
     * Verifica si el rol tiene permisos administrativos.
     * 
     * @return true si el rol tiene permisos de administrador o asesor
     */
    public boolean tienePermisosAdministrativos() {
        return isAdmin() || isAsesor();
    }
    
    /**
     * Verifica si el rol puede registrar usuarios.
     * Según las especificaciones, solo ADMIN y ASESOR pueden registrar usuarios.
     * 
     * @return true si el rol puede registrar usuarios
     */
    public boolean puedeRegistrarUsuarios() {
        return tienePermisosAdministrativos();
    }
    
    /**
     * Valida los invariantes de la entidad.
     * 
     * @throws IllegalStateException si los invariantes no se cumplen
     */
    private void validateInvariants() {
        if (id == null) {
            throw new IllegalStateException("El identificador del rol no puede ser nulo");
        }
        if (nombre == null) {
            throw new IllegalStateException("El nombre del rol no puede ser nulo");
        }
        if (descripcion == null) {
            throw new IllegalStateException("La descripción del rol no puede ser nula");
        }
        if (version < 0) {
            throw new IllegalStateException("La versión del rol no puede ser negativa");
        }
    }
    
    // Getters intencionales (no exponer estado mutable)
    
    /**
     * Obtiene el identificador del rol.
     * 
     * @return Identificador del rol
     */
    public RolId id() {
        return id;
    }
    
    /**
     * Obtiene el nombre del rol.
     * 
     * @return Nombre del rol
     */
    public NombreRol nombre() {
        return nombre;
    }
    
    /**
     * Obtiene la descripción del rol.
     * 
     * @return Descripción del rol
     */
    public DescripcionRol descripcion() {
        return descripcion;
    }
    
    /**
     * Obtiene la versión del rol para control de concurrencia.
     * 
     * @return Versión del rol
     */
    public long version() {
        return version;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rol rol = (Rol) obj;
        return id.equals(rol.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id.value() +
                ", nombre=" + nombre.value() +
                ", descripcion=" + descripcion.value() +
                ", version=" + version +
                '}';
    }
}
