package ar.edu.um.gestioneventos.service.mapper;

import ar.edu.um.gestioneventos.domain.Asiento;
import ar.edu.um.gestioneventos.domain.Evento;
import ar.edu.um.gestioneventos.service.dto.AsientoDTO;
import ar.edu.um.gestioneventos.service.dto.EventoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asiento} and its DTO {@link AsientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AsientoMapper extends EntityMapper<AsientoDTO, Asiento> {
    @Mapping(target = "evento_con_asientos", source = "evento_con_asientos", qualifiedByName = "eventoTitulo")
    AsientoDTO toDto(Asiento s);

    @Named("eventoTitulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    EventoDTO toDtoEventoTitulo(Evento evento);
}
