package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos del beneficiario asociado a un caso")
public class BeneficiarioResponse {

        @Schema(description = "Cédula del beneficiario")
        private String cedula;

        @Schema(description = "Número del caso")
        private String numCaso;

        @Schema(description = "Tipo de beneficiario")
        private String tipoBeneficiario;

        @Schema(description = "Parentesco")
        private String parentesco;

        @Schema(description = "Nombre del beneficiario")
        private String nombre;

        public BeneficiarioResponse(String cedula, String numCaso, String tipoBeneficiario, String parentesco,
                        String nombre) {
                this.cedula = cedula;
                this.numCaso = numCaso;
                this.tipoBeneficiario = tipoBeneficiario;
                this.parentesco = parentesco;
                this.nombre = nombre;
        }

        public String getCedula() {
                return cedula;
        }

        public String getNumCaso() {
                return numCaso;
        }

        public String getTipoBeneficiario() {
                return tipoBeneficiario;
        }

        public String getParentesco() {
                return parentesco;
        }

        public String getNombre() {
                return nombre;
        }
}
