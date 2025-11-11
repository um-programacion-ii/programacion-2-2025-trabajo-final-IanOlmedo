package ar.edu.um.gestioneventos.domain;

import ar.edu.um.gestioneventos.domain.enumeration.EstadoAsiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Asiento.
 */
@Entity
@Table(name = "asiento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Asiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fila", nullable = false)
    private String fila;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoAsiento estado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Evento evento_con_asientos;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asiento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFila() {
        return this.fila;
    }

    public Asiento fila(String fila) {
        this.setFila(fila);
        return this;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Asiento numero(Integer numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstado() {
        return this.estado;
    }

    public Asiento estado(EstadoAsiento estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    public Evento getEvento_con_asientos() {
        return this.evento_con_asientos;
    }

    public void setEvento_con_asientos(Evento evento) {
        this.evento_con_asientos = evento;
    }

    public Asiento evento_con_asientos(Evento evento) {
        this.setEvento_con_asientos(evento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asiento)) {
            return false;
        }
        return getId() != null && getId().equals(((Asiento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asiento{" +
            "id=" + getId() +
            ", fila='" + getFila() + "'" +
            ", numero=" + getNumero() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
