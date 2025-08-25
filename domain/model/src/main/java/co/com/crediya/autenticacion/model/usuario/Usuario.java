package co.com.crediya.autenticacion.model.usuario;

import java.time.LocalDateTime;

import co.com.crediya.autenticacion.model.rol.RolId;
import co.com.crediya.autenticacion.model.valueobjects.ApellidoUsuario;
import co.com.crediya.autenticacion.model.valueobjects.DocumentoIdentidad;
import co.com.crediya.autenticacion.model.valueobjects.Email;
import co.com.crediya.autenticacion.model.valueobjects.NombreUsuario;
import co.com.crediya.autenticacion.model.valueobjects.SalarioBase;
import co.com.crediya.autenticacion.model.valueobjects.Telefono;
import co.com.crediya.autenticacion.model.valueobjects.UsuarioId;

/**
 * Entidad que representa un Usuario en el sistema de autenticación CrediYa.
 * Un usuario es una persona que puede registrarse, autenticarse y acceder
 * a las funcionalidades del sistema según su rol.
 * 
 * Esta entidad es inmutable y protege sus invariantes a través de Value Objects.
 * Según las especificaciones del microservicio, los usuarios pueden tener
 * diferentes roles: ADMIN, ASESOR, CLIENTE.
 */
public final class Usuario {
    
    private final UsuarioId id;
    private final NombreUsuario nombre;
    private final ApellidoUsuario apellido;
    private final Email email;
    private final DocumentoIdentidad documentoIdentidad;
    private final Telefono telefono;
    private final RolId rolId;
    private final SalarioBase salarioBase;
    private final EstadoUsuario estado;
    private final LocalDateTime fechaCreacion;
    private final long version;
    
    /**
     * Constructor privado para crear una instancia de Usuario.
     * 
     * @param id Identificador único del usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param email Email del usuario
     * @param documentoIdentidad Documento de identidad del usuario
     * @param telefono Teléfono del usuario
     * @param rolId Identificador del rol del usuario
     * @param salarioBase Salario base del usuario
     * @param estado Estado del usuario
     * @param fechaCreacion Fecha de creación del usuario
     * @param version Versión para control de concurrencia optimista
     */
    private Usuario(UsuarioId id, NombreUsuario nombre, ApellidoUsuario apellido, 
                   Email email, DocumentoIdentidad documentoIdentidad, Telefono telefono,
                   RolId rolId, SalarioBase salarioBase, EstadoUsuario estado,
                   LocalDateTime fechaCreacion, long version) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.rolId = rolId;
        this.salarioBase = salarioBase;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.version = version;
        validateInvariants();
    }
    
    /**
     * Crea un nuevo usuario con los datos proporcionados.
     * 
     * @param id Identificador único del usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param email Email del usuario
     * @param documentoIdentidad Documento de identidad del usuario
     * @param telefono Teléfono del usuario
     * @param rolId Identificador del rol del usuario
     * @param salarioBase Salario base del usuario
     * @return Nueva instancia de Usuario
     */
    public static Usuario create(UsuarioId id, NombreUsuario nombre, ApellidoUsuario apellido,
                               Email email, DocumentoIdentidad documentoIdentidad, Telefono telefono,
                               RolId rolId, SalarioBase salarioBase) {
        return new Usuario(id, nombre, apellido, email, documentoIdentidad, telefono,
                          rolId, salarioBase, EstadoUsuario.ACTIVO, LocalDateTime.now(), 0L);
    }
    
    /**
     * Crea un usuario a partir de datos existentes (para reconstrucción desde persistencia).
     * 
     * @param id Identificador único del usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param email Email del usuario
     * @param documentoIdentidad Documento de identidad del usuario
     * @param telefono Teléfono del usuario
     * @param rolId Identificador del rol del usuario
     * @param salarioBase Salario base del usuario
     * @param estado Estado del usuario
     * @param fechaCreacion Fecha de creación del usuario
     * @param version Versión actual
     * @return Nueva instancia de Usuario
     */
    public static Usuario from(UsuarioId id, NombreUsuario nombre, ApellidoUsuario apellido,
                             Email email, DocumentoIdentidad documentoIdentidad, Telefono telefono,
                             RolId rolId, SalarioBase salarioBase, EstadoUsuario estado,
                             LocalDateTime fechaCreacion, long version) {
        return new Usuario(id, nombre, apellido, email, documentoIdentidad, telefono,
                          rolId, salarioBase, estado, fechaCreacion, version);
    }
    
    /**
     * Actualiza el teléfono del usuario.
     * 
     * @param nuevoTelefono Nuevo teléfono del usuario
     * @return Nueva instancia de Usuario con el teléfono actualizado
     */
    public Usuario updateTelefono(Telefono nuevoTelefono) {
        return new Usuario(this.id, this.nombre, this.apellido, this.email,
                          this.documentoIdentidad, nuevoTelefono, this.rolId,
                          this.salarioBase, this.estado, this.fechaCreacion, this.version);
    }
    
    /**
     * Actualiza el salario base del usuario.
     * 
     * @param nuevoSalario Nuevo salario base del usuario
     * @return Nueva instancia de Usuario con el salario actualizado
     */
    public Usuario updateSalarioBase(SalarioBase nuevoSalario) {
        return new Usuario(this.id, this.nombre, this.apellido, this.email,
                          this.documentoIdentidad, this.telefono, this.rolId,
                          nuevoSalario, this.estado, this.fechaCreacion, this.version);
    }
    
    /**
     * Cambia el estado del usuario.
     * 
     * @param nuevoEstado Nuevo estado del usuario
     * @return Nueva instancia de Usuario con el estado actualizado
     */
    public Usuario cambiarEstado(EstadoUsuario nuevoEstado) {
        return new Usuario(this.id, this.nombre, this.apellido, this.email,
                          this.documentoIdentidad, this.telefono, this.rolId,
                          this.salarioBase, nuevoEstado, this.fechaCreacion, this.version);
    }
    
    /**
     * Marca el usuario como persistido con una nueva versión.
     * 
     * @param newVersion Nueva versión
     * @return Nueva instancia de Usuario con la versión actualizada
     */
    public Usuario markPersisted(long newVersion) {
        if (newVersion <= version) {
            throw new IllegalArgumentException("La nueva versión debe ser mayor que la actual");
        }
        return new Usuario(this.id, this.nombre, this.apellido, this.email,
                          this.documentoIdentidad, this.telefono, this.rolId,
                          this.salarioBase, this.estado, this.fechaCreacion, newVersion);
    }
    
    /**
     * Verifica si el usuario está activo.
     * 
     * @return true si el usuario está activo
     */
    public boolean isActivo() {
        return estado == EstadoUsuario.ACTIVO;
    }
    
    /**
     * Verifica si el usuario puede autenticarse.
     * 
     * @return true si el usuario puede autenticarse
     */
    public boolean puedeAutenticarse() {
        return isActivo();
    }
    
    /**
     * Obtiene el nombre completo del usuario.
     * 
     * @return Nombre completo (nombre + apellido)
     */
    public String nombreCompleto() {
        return nombre.value() + " " + apellido.value();
    }
    
    /**
     * Verifica si el usuario cumple con el salario mínimo requerido.
     * 
     * @param salarioMinimo Salario mínimo requerido
     * @return true si el usuario cumple con el salario mínimo
     */
    public boolean cumpleSalarioMinimo(SalarioBase salarioMinimo) {
        return salarioBase.cumpleMinimo(salarioMinimo);
    }
    
    /**
     * Valida los invariantes de la entidad.
     * 
     * @throws IllegalStateException si los invariantes no se cumplen
     */
    private void validateInvariants() {
        if (id == null) {
            throw new IllegalStateException("El identificador del usuario no puede ser nulo");
        }
        if (nombre == null) {
            throw new IllegalStateException("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null) {
            throw new IllegalStateException("El apellido del usuario no puede ser nulo");
        }
        if (email == null) {
            throw new IllegalStateException("El email del usuario no puede ser nulo");
        }
        if (documentoIdentidad == null) {
            throw new IllegalStateException("El documento de identidad del usuario no puede ser nulo");
        }
        if (telefono == null) {
            throw new IllegalStateException("El teléfono del usuario no puede ser nulo");
        }
        if (rolId == null) {
            throw new IllegalStateException("El rol del usuario no puede ser nulo");
        }
        if (salarioBase == null) {
            throw new IllegalStateException("El salario base del usuario no puede ser nulo");
        }
        if (estado == null) {
            throw new IllegalStateException("El estado del usuario no puede ser nulo");
        }
        if (fechaCreacion == null) {
            throw new IllegalStateException("La fecha de creación del usuario no puede ser nula");
        }
        if (version < 0) {
            throw new IllegalStateException("La versión del usuario no puede ser negativa");
        }
    }
    
    // Getters intencionales (no exponer estado mutable)
    
    /**
     * Obtiene el identificador del usuario.
     * 
     * @return Identificador del usuario
     */
    public UsuarioId id() {
        return id;
    }
    
    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario
     */
    public NombreUsuario nombre() {
        return nombre;
    }
    
    /**
     * Obtiene el apellido del usuario.
     * 
     * @return Apellido del usuario
     */
    public ApellidoUsuario apellido() {
        return apellido;
    }
    
    /**
     * Obtiene el email del usuario.
     * 
     * @return Email del usuario
     */
    public Email email() {
        return email;
    }
    
    /**
     * Obtiene el documento de identidad del usuario.
     * 
     * @return Documento de identidad del usuario
     */
    public DocumentoIdentidad documentoIdentidad() {
        return documentoIdentidad;
    }
    
    /**
     * Obtiene el teléfono del usuario.
     * 
     * @return Teléfono del usuario
     */
    public Telefono telefono() {
        return telefono;
    }
    
    /**
     * Obtiene el identificador del rol del usuario.
     * 
     * @return Identificador del rol del usuario
     */
    public RolId rolId() {
        return rolId;
    }
    
    /**
     * Obtiene el salario base del usuario.
     * 
     * @return Salario base del usuario
     */
    public SalarioBase salarioBase() {
        return salarioBase;
    }
    
    /**
     * Obtiene el estado del usuario.
     * 
     * @return Estado del usuario
     */
    public EstadoUsuario estado() {
        return estado;
    }
    
    /**
     * Obtiene la fecha de creación del usuario.
     * 
     * @return Fecha de creación del usuario
     */
    public LocalDateTime fechaCreacion() {
        return fechaCreacion;
    }
    
    /**
     * Obtiene la versión del usuario para control de concurrencia.
     * 
     * @return Versión del usuario
     */
    public long version() {
        return version;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return id.equals(usuario.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id.value() +
                ", nombre=" + nombre.value() +
                ", apellido=" + apellido.value() +
                ", email=" + email.value() +
                ", documentoIdentidad=" + documentoIdentidad.value() +
                ", telefono=" + telefono.value() +
                ", rolId=" + rolId.value() +
                ", salarioBase=" + salarioBase.value() +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", version=" + version +
                '}';
    }
}
