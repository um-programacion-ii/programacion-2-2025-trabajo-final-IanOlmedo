package ar.edu.um.gestioneventos.service.mapper;

import ar.edu.um.gestioneventos.domain.Asiento;
import ar.edu.um.gestioneventos.domain.Evento;
import ar.edu.um.gestioneventos.domain.User;
import ar.edu.um.gestioneventos.domain.Venta;
import ar.edu.um.gestioneventos.service.dto.AsientoDTO;
import ar.edu.um.gestioneventos.service.dto.EventoDTO;
import ar.edu.um.gestioneventos.service.dto.UserDTO;
import ar.edu.um.gestioneventos.service.dto.VentaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venta} and its DTO {@link VentaDTO}.
 */
@Mapper(componentModel = "spring")
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {
    @Mapping(target = "evento", source = "evento", qualifiedByName = "eventoTitulo")
    @Mapping(target = "asientos", source = "asientos", qualifiedByName = "asientoNumeroSet")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "userLogin")
    VentaDTO toDto(Venta s);

    @Mapping(target = "removeAsiento", ignore = true)
    Venta toEntity(VentaDTO ventaDTO);

    @Named("eventoTitulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    EventoDTO toDtoEventoTitulo(Evento evento);

    @Named("asientoNumero")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numero", source = "numero")
    AsientoDTO toDtoAsientoNumero(Asiento asiento);

    @Named("asientoNumeroSet")
    default Set<AsientoDTO> toDtoAsientoNumeroSet(Set<Asiento> asiento) {
        return asiento.stream().map(this::toDtoAsientoNumero).collect(Collectors.toSet());
    }

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
