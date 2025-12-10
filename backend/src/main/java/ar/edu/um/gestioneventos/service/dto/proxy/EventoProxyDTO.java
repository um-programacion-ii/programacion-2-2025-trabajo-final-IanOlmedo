package ar.edu.um.gestioneventos.service.dto.proxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO que representa un evento tal como lo expone el proxy.
 */
public class EventoProxyDTO implements Serializable {

    private Long id;
    private String titulo;
    private String resumen;
    private LocalDate fecha;
    private BigDecimal precioEntrada;

    public EventoProxyDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(BigDecimal precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    @Override
    public String toString() {
        return "EventoProxyDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", resumen='" + resumen + '\'' +
                ", fecha=" + fecha +
                ", precioEntrada=" + precioEntrada +
                '}';
    }
}
