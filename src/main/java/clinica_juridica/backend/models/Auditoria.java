package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import com.fasterxml.jackson.databind.JsonNode; // <--- De Jackson

import java.time.LocalDateTime;

@Table("auditoria_sistema")
public class Auditoria {

    @Id
    @Column("id_auditoria")
    private Integer idAuditoria;

    @Column("nombre_tabla")
    private String nombreTabla;

    @Column("operacion")
    private String operacion;

    @Column("username_aplicacion")
    private String usernameAplicacion;

    @Column("fecha_evento")
    private LocalDateTime fechaEvento;

    @Column("datos_anteriores")
    private JsonNode datosAnteriores;

    @Column("datos_nuevos")
    private JsonNode datosNuevos;

    public Auditoria() {
    }

    public Integer getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Integer idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getUsernameAplicacion() {
        return usernameAplicacion;
    }

    public void setUsernameAplicacion(String usernameAplicacion) {
        this.usernameAplicacion = usernameAplicacion;
    }

    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public JsonNode getDatosAnteriores() {
        return datosAnteriores;
    }

    public void setDatosAnteriores(JsonNode datosAnteriores) {
        this.datosAnteriores = datosAnteriores;
    }

    public JsonNode getDatosNuevos() {
        return datosNuevos;
    }

    public void setDatosNuevos(JsonNode datosNuevos) {
        this.datosNuevos = datosNuevos;
    }

}
