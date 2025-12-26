package clinica_juridica.backend.dto.response;

public class AuthLoginResponse {
    private String jwt;

    public AuthLoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
