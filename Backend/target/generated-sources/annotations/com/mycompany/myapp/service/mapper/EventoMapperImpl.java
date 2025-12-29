package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.service.dto.EventoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-27T18:52:20-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class EventoMapperImpl implements EventoMapper {

    @Override
    public Evento toEntity(EventoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Evento evento = new Evento();

        evento.setId( dto.getId() );
        evento.setTitulo( dto.getTitulo() );
        evento.setResumen( dto.getResumen() );
        evento.setDescripcion( dto.getDescripcion() );
        evento.setFecha( dto.getFecha() );
        evento.setDireccion( dto.getDireccion() );
        evento.setImagen( dto.getImagen() );
        evento.setFilaAsientos( dto.getFilaAsientos() );
        evento.setPrecioEntrada( dto.getPrecioEntrada() );
        evento.setEstado( dto.getEstado() );
        evento.setUltimaActualizacion( dto.getUltimaActualizacion() );

        return evento;
    }

    @Override
    public EventoDTO toDto(Evento entity) {
        if ( entity == null ) {
            return null;
        }

        EventoDTO eventoDTO = new EventoDTO();

        eventoDTO.setId( entity.getId() );
        eventoDTO.setTitulo( entity.getTitulo() );
        eventoDTO.setResumen( entity.getResumen() );
        eventoDTO.setDescripcion( entity.getDescripcion() );
        eventoDTO.setFecha( entity.getFecha() );
        eventoDTO.setDireccion( entity.getDireccion() );
        eventoDTO.setImagen( entity.getImagen() );
        eventoDTO.setFilaAsientos( entity.getFilaAsientos() );
        eventoDTO.setPrecioEntrada( entity.getPrecioEntrada() );
        eventoDTO.setEstado( entity.getEstado() );
        eventoDTO.setUltimaActualizacion( entity.getUltimaActualizacion() );

        return eventoDTO;
    }

    @Override
    public List<Evento> toEntity(List<EventoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Evento> list = new ArrayList<Evento>( dtoList.size() );
        for ( EventoDTO eventoDTO : dtoList ) {
            list.add( toEntity( eventoDTO ) );
        }

        return list;
    }

    @Override
    public List<EventoDTO> toDto(List<Evento> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EventoDTO> list = new ArrayList<EventoDTO>( entityList.size() );
        for ( Evento evento : entityList ) {
            list.add( toDto( evento ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Evento entity, EventoDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTitulo() != null ) {
            entity.setTitulo( dto.getTitulo() );
        }
        if ( dto.getResumen() != null ) {
            entity.setResumen( dto.getResumen() );
        }
        if ( dto.getDescripcion() != null ) {
            entity.setDescripcion( dto.getDescripcion() );
        }
        if ( dto.getFecha() != null ) {
            entity.setFecha( dto.getFecha() );
        }
        if ( dto.getDireccion() != null ) {
            entity.setDireccion( dto.getDireccion() );
        }
        if ( dto.getImagen() != null ) {
            entity.setImagen( dto.getImagen() );
        }
        if ( dto.getFilaAsientos() != null ) {
            entity.setFilaAsientos( dto.getFilaAsientos() );
        }
        if ( dto.getPrecioEntrada() != null ) {
            entity.setPrecioEntrada( dto.getPrecioEntrada() );
        }
        if ( dto.getEstado() != null ) {
            entity.setEstado( dto.getEstado() );
        }
        if ( dto.getUltimaActualizacion() != null ) {
            entity.setUltimaActualizacion( dto.getUltimaActualizacion() );
        }
    }
}
