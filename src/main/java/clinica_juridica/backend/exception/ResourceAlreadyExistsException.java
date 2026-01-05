package clinica_juridica.backend.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    private final Object existingResource;

    public ResourceAlreadyExistsException(String message, Object existingResource) {
        super(message);
        this.existingResource = existingResource;
    }

    public Object getExistingResource() {
        return existingResource;
    }
}
