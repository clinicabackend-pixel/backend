package clinica_juridica.backend.dto.response;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resumen detallado del caso")
public class CasoResponse {

        @Schema(description = "Número de caso")
        private String numCaso;
        @Schema(description = "Fecha de recepción")
        private LocalDate fechaRecepcion;
        @Schema(description = "Síntesis")
        private String sintesis;
        @Schema(description = "Trámite")
        private String tramite;
        @Schema(description = "Cantidad de beneficiarios")
        private Integer cantBeneficiarios;
        @Schema(description = "Estatus")
        private String estatus;
        @Schema(description = "Código Tribunal")
        private String codCasoTribunal;
        @Schema(description = "Fecha Resolución Tribunal")
        private LocalDate fechaResCasoTri;
        @Schema(description = "Fecha Creación Tribunal")
        private LocalDate fechaCreaCasoTri;
        @Schema(description = "ID Tribunal")
        private Integer idTribunal;
        @Schema(description = "Nombre del Tribunal")
        private String nombreTribunal;
        @Schema(description = "Término")
        private String termino;
        @Schema(description = "ID Centro")
        private Integer idCentro;
        @Schema(description = "Cédula Solicitante")
        private String cedula;
        @Schema(description = "Usuario Asignado")
        private String username;
        @Schema(description = "Competencia Ámbito Legal")
        private Integer comAmbLegal;

        public CasoResponse() {
        }

        public CasoResponse(String numCaso, LocalDate fechaRecepcion, String sintesis, String tramite,
                        Integer cantBeneficiarios, String estatus, String codCasoTribunal, LocalDate fechaResCasoTri,
                        LocalDate fechaCreaCasoTri, Integer idTribunal, String nombreTribunal, String termino,
                        Integer idCentro, String cedula,
                        String username, Integer comAmbLegal) {
                this.numCaso = numCaso;
                this.fechaRecepcion = fechaRecepcion;
                this.sintesis = sintesis;
                this.tramite = tramite;
                this.cantBeneficiarios = cantBeneficiarios;
                this.estatus = estatus;
                this.codCasoTribunal = codCasoTribunal;
                this.fechaResCasoTri = fechaResCasoTri;
                this.fechaCreaCasoTri = fechaCreaCasoTri;
                this.idTribunal = idTribunal;
                this.nombreTribunal = nombreTribunal;
                this.termino = termino;
                this.idCentro = idCentro;
                this.cedula = cedula;
                this.username = username;
                this.comAmbLegal = comAmbLegal;
        }

        // Getters and Setters
        public String getNumCaso() {
                return numCaso;
        }

        public void setNumCaso(String numCaso) {
                this.numCaso = numCaso;
        }

        public LocalDate getFechaRecepcion() {
                return fechaRecepcion;
        }

        public void setFechaRecepcion(LocalDate fechaRecepcion) {
                this.fechaRecepcion = fechaRecepcion;
        }

        public String getSintesis() {
                return sintesis;
        }

        public void setSintesis(String sintesis) {
                this.sintesis = sintesis;
        }

        public String getTramite() {
                return tramite;
        }

        public void setTramite(String tramite) {
                this.tramite = tramite;
        }

        public Integer getCantBeneficiarios() {
                return cantBeneficiarios;
        }

        public void setCantBeneficiarios(Integer cantBeneficiarios) {
                this.cantBeneficiarios = cantBeneficiarios;
        }

        public String getEstatus() {
                return estatus;
        }

        public void setEstatus(String estatus) {
                this.estatus = estatus;
        }

        public String getCodCasoTribunal() {
                return codCasoTribunal;
        }

        public void setCodCasoTribunal(String codCasoTribunal) {
                this.codCasoTribunal = codCasoTribunal;
        }

        public LocalDate getFechaResCasoTri() {
                return fechaResCasoTri;
        }

        public void setFechaResCasoTri(LocalDate fechaResCasoTri) {
                this.fechaResCasoTri = fechaResCasoTri;
        }

        public LocalDate getFechaCreaCasoTri() {
                return fechaCreaCasoTri;
        }

        public void setFechaCreaCasoTri(LocalDate fechaCreaCasoTri) {
                this.fechaCreaCasoTri = fechaCreaCasoTri;
        }

        public Integer getIdTribunal() {
                return idTribunal;
        }

        public void setIdTribunal(Integer idTribunal) {
                this.idTribunal = idTribunal;
        }

        public String getNombreTribunal() {
                return nombreTribunal;
        }

        public void setNombreTribunal(String nombreTribunal) {
                this.nombreTribunal = nombreTribunal;
        }

        public String getTermino() {
                return termino;
        }

        public void setTermino(String termino) {
                this.termino = termino;
        }

        public Integer getIdCentro() {
                return idCentro;
        }

        public void setIdCentro(Integer idCentro) {
                this.idCentro = idCentro;
        }

        public String getCedula() {
                return cedula;
        }

        public void setCedula(String cedula) {
                this.cedula = cedula;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public Integer getComAmbLegal() {
                return comAmbLegal;
        }

        public void setComAmbLegal(Integer comAmbLegal) {
                this.comAmbLegal = comAmbLegal;
        }
}
