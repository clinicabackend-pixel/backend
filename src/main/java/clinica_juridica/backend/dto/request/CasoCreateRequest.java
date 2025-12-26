package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Solicitud para la creación de un nuevo caso")
public class CasoCreateRequest {
        @Schema(description = "Síntesis del caso", requiredMode = Schema.RequiredMode.REQUIRED)
        private String sintesis;

        @Schema(description = "Trámite legal")
        private String tramite;

        @Schema(description = "Cantidad de beneficiarios")
        private Integer cantBeneficiarios;

        @Schema(description = "ID del tribunal asociado")
        private Integer idTribunal;

        @Schema(description = "Término legal")
        private String termino;

        @Schema(description = "ID del centro")
        private Integer idCentro;

        @Schema(description = "Cédula del solicitante/cliente", requiredMode = Schema.RequiredMode.REQUIRED)
        private String cedula;

        @Schema(description = "Nombre de usuario del abogado/estudiante asignado", requiredMode = Schema.RequiredMode.REQUIRED)
        private String username;

        @Schema(description = "Competencia de ámbito legal")
        private Integer comAmbLegal;

        public CasoCreateRequest() {
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

        public Integer getIdTribunal() {
                return idTribunal;
        }

        public void setIdTribunal(Integer idTribunal) {
                this.idTribunal = idTribunal;
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
