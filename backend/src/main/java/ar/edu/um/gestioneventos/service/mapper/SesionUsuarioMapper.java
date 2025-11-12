package ar.edu.um.gestioneventos.service.mapper;

import ar.edu.um.gestioneventos.domain.SesionUsuario;
import ar.edu.um.gestioneventos.domain.User;
import ar.edu.um.gestioneventos.service.dto.SesionUsuarioDTO;
import ar.edu.um.gestioneventos.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SesionUsuario} and its DTO {@link SesionUsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface SesionUsuarioMapper extends EntityMapper<SesionUsuarioDTO, SesionUsuario> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    SesionUsuarioDTO toDto(SesionUsuario s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
