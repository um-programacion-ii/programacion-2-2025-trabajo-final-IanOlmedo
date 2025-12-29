package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.service.dto.VentaDTO;
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
public class VentaMapperImpl implements VentaMapper {

    @Override
    public Venta toEntity(VentaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Venta venta = new Venta();

        venta.setId( dto.getId() );
        venta.setVentaIdCatedra( dto.getVentaIdCatedra() );
        venta.setFechaVenta( dto.getFechaVenta() );
        venta.setPrecioVenta( dto.getPrecioVenta() );
        venta.setResultado( dto.getResultado() );
        venta.setDescripcion( dto.getDescripcion() );
        venta.setCantidadAsientos( dto.getCantidadAsientos() );
        venta.setEstado( dto.getEstado() );

        return venta;
    }

    @Override
    public VentaDTO toDto(Venta entity) {
        if ( entity == null ) {
            return null;
        }

        VentaDTO ventaDTO = new VentaDTO();

        ventaDTO.setId( entity.getId() );
        ventaDTO.setVentaIdCatedra( entity.getVentaIdCatedra() );
        ventaDTO.setFechaVenta( entity.getFechaVenta() );
        ventaDTO.setPrecioVenta( entity.getPrecioVenta() );
        ventaDTO.setResultado( entity.getResultado() );
        ventaDTO.setDescripcion( entity.getDescripcion() );
        ventaDTO.setCantidadAsientos( entity.getCantidadAsientos() );
        ventaDTO.setEstado( entity.getEstado() );

        return ventaDTO;
    }

    @Override
    public List<Venta> toEntity(List<VentaDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Venta> list = new ArrayList<Venta>( dtoList.size() );
        for ( VentaDTO ventaDTO : dtoList ) {
            list.add( toEntity( ventaDTO ) );
        }

        return list;
    }

    @Override
    public List<VentaDTO> toDto(List<Venta> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VentaDTO> list = new ArrayList<VentaDTO>( entityList.size() );
        for ( Venta venta : entityList ) {
            list.add( toDto( venta ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Venta entity, VentaDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getVentaIdCatedra() != null ) {
            entity.setVentaIdCatedra( dto.getVentaIdCatedra() );
        }
        if ( dto.getFechaVenta() != null ) {
            entity.setFechaVenta( dto.getFechaVenta() );
        }
        if ( dto.getPrecioVenta() != null ) {
            entity.setPrecioVenta( dto.getPrecioVenta() );
        }
        if ( dto.getResultado() != null ) {
            entity.setResultado( dto.getResultado() );
        }
        if ( dto.getDescripcion() != null ) {
            entity.setDescripcion( dto.getDescripcion() );
        }
        if ( dto.getCantidadAsientos() != null ) {
            entity.setCantidadAsientos( dto.getCantidadAsientos() );
        }
        if ( dto.getEstado() != null ) {
            entity.setEstado( dto.getEstado() );
        }
    }
}
