package co.com.crediya.autenticacion.r2dbc.mappers;

import co.com.crediya.autenticacion.model.sesion.Sesion;
import co.com.crediya.autenticacion.r2dbc.entities.SesionEntity;

public final class SessionEntityMapper {
    private SessionEntityMapper() { }

    public static SesionEntity toEntity(Sesion model) {
        if (model == null) return null;
        return SesionEntity.builder()
                .sesionId(model.getSesionId())
                .usuarioId(model.getUsuarioId())
                .token(model.getToken())
                // .fechaExpiracion(model.getFechaExpiracion())
                .build();
    }

    public static Sesion toDomain(SesionEntity entity) {
        if (entity == null) return null;
        return Sesion.builder()
                .sesionId(entity.getSesionId())
                .usuarioId(entity.getUsuarioId())
                .token(entity.getToken())
                // .fechaExpiracion(entity.getFechaExpiracion())
                .build();
    }
}
