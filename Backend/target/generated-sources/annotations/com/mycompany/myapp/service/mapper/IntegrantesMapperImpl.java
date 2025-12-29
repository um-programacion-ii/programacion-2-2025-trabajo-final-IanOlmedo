package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.domain.Integrantes;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.dto.IntegrantesDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-27T18:52:19-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class IntegrantesMapperImpl implements IntegrantesMapper {

    @Override
    public Integrantes toEntity(IntegrantesDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Integrantes integrantes = new Integrantes();

        integrantes.setId( dto.getId() );
        integrantes.setNombre( dto.getNombre() );
        integrantes.setApellido( dto.getApellido() );
        integrantes.setIdentificacion( dto.getIdentificacion() );
        integrantes.evento( eventoDTOToEvento( dto.getEvento() ) );

        return integrantes;
    }

    @Override
    public List<Integrantes> toEntity(List<IntegrantesDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Integrantes> list = new ArrayList<Integrantes>( dtoList.size() );
        for ( IntegrantesDTO integrantesDTO : dtoList ) {
            list.add( toEntity( integrantesDTO ) );
        }

        return list;
    }

    @Override
    public List<IntegrantesDTO> toDto(List<Integrantes> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<IntegrantesDTO> list = new ArrayList<IntegrantesDTO>( entityList.size() );
        for ( Integrantes integrantes : entityList ) {
            list.add( toDto( integrantes ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Integrantes entity, IntegrantesDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNombre() != null ) {
            entity.setNombre( dto.getNombre() );
        }
        if ( dto.getApellido() != null ) {
            entity.setApellido( dto.getApellido() );
        }
        if ( dto.getIdentificacion() != null ) {
            entity.setIdentificacion( dto.getIdentificacion() );
        }
        if ( dto.getEvento() != null ) {
            if ( entity.getEvento() == null ) {
                entity.evento( new Evento() );
            }
            eventoDTOToEvento1( dto.getEvento(), entity.getEvento() );
        }
    }

    @Override
    public IntegrantesDTO toDto(Integrantes s) {
        if ( s == null ) {
            return null;
        }

        IntegrantesDTO integrantesDTO = new IntegrantesDTO();

        integrantesDTO.setEvento( toDtoEventoId( s.getEvento() ) );
        integrantesDTO.setId( s.getId() );
        integrantesDTO.setNombre( s.getNombre() );
        integrantesDTO.setApellido( s.getApellido() );
        integrantesDTO.setIdentificacion( s.getIdentificacion() );

        return integrantesDTO;
    }

    @Override
    public EventoDTO toDtoEventoId(Evento evento) {
        if ( evento == null ) {
            return null;
        }

        EventoDTO eventoDTO = new EventoDTO();

        eventoDTO.setId( evento.getId() );

        return eventoDTO;
    }

    protected Evento eventoDTOToEvento(EventoDTO eventoDTO) {
        if ( eventoDTO == null ) {
            return null;
        }

        Evento evento = new Evento();

        evento.setId( eventoDTO.getId() );
        evento.setTitulo( eventoDTO.getTitulo() );
        evento.setResumen( eventoDTO.getResumen() );
        evento.setDescripcion( eventoDTO.getDescripcion() );
        evento.setFecha( eventoDTO.getFecha() );
        evento.setDireccion( eventoDTO.getDireccion() );
        evento.setImagen( eventoDTO.getImagen() );
        evento.setFilaAsientos( eventoDTO.getFilaAsientos() );
        evento.setPrecioEntrada( eventoDTO.getPrecioEntrada() );
        evento.setEstado( eventoDTO.getEstado() );
        evento.setUltimaActualizacion( eventoDTO.getUltimaActualizacion() );

        return evento;
    }

    protected void eventoDTOToEvento1(EventoDTO eventoDTO, Evento mappingTarget) {
        if ( eventoDTO == null ) {
            return;
        }

        if ( eventoDTO.getId() != null ) {
            mappingTarget.setId( eventoDTO.getId() );
        }
        if ( eventoDTO.getTitulo() != null ) {
            mappingTarget.setTitulo( eventoDTO.getTitulo() );
        }
        if ( eventoDTO.getResumen() != null ) {
            mappingTarget.setResumen( eventoDTO.getResumen() );
        }
        if ( eventoDTO.getDescripcion() != null ) {
            mappingTarget.setDescripcion( eventoDTO.getDescripcion() );
        }
        if ( eventoDTO.getFecha() != null ) {
            mappingTarget.setFecha( eventoDTO.getFecha() );
        }
        if ( eventoDTO.getDireccion() != null ) {
            mappingTarget.setDireccion( eventoDTO.getDireccion() );
        }
        if ( eventoDTO.getImagen() != null ) {
            mappingTarget.setImagen( eventoDTO.getImagen() );
        }
        if ( eventoDTO.getFilaAsientos() != null ) {
            mappingTarget.setFilaAsientos( eventoDTO.getFilaAsientos() );
        }
        if ( eventoDTO.getPrecioEntrada() != null ) {
            mappingTarget.setPrecioEntrada( eventoDTO.getPrecioEntrada() );
        }
        if ( eventoDTO.getEstado() != null ) {
            mappingTarget.setEstado( eventoDTO.getEstado() );
        }
        if ( eventoDTO.getUltimaActualizacion() != null ) {
            mappingTarget.setUltimaActualizacion( eventoDTO.getUltimaActualizacion() );
        }
    }
}
