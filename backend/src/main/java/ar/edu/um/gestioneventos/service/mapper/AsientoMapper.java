package ar.edu.um.gestioneventos.service.mapper;

import ar.edu.um.gestioneventos.domain.Asiento;
import ar.edu.um.gestioneventos.domain.Evento;
import ar.edu.um.gestioneventos.domain.Venta;
import ar.edu.um.gestioneventos.service.dto.AsientoDTO;
import ar.edu.um.gestioneventos.service.dto.EventoDTO;
import ar.edu.um.gestioneventos.service.dto.VentaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asiento} and its DTO {@link AsientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AsientoMapper extends EntityMapper<AsientoDTO, Asiento> {
    @Mapping(target = "evento_con_asientos", source = "evento_con_asientos", qualifiedByName = "eventoTitulo")
    @Mapping(target = "ns", source = "ns", qualifiedByName = "ventaIdSet")
    AsientoDTO toDto(Asiento s);

    @Mapping(target = "ns", ignore = true)
    @Mapping(target = "removeN", ignore = true)
    Asiento toEntity(AsientoDTO asientoDTO);

    @Named("eventoTitulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    EventoDTO toDtoEventoTitulo(Evento evento);

    @Named("ventaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VentaDTO toDtoVentaId(Venta venta);

    @Named("ventaIdSet")
    default Set<VentaDTO> toDtoVentaIdSet(Set<Venta> venta) {
        return venta.stream().map(this::toDtoVentaId).collect(Collectors.toSet());
    }
}
