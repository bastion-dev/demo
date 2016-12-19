package rocks.bastion.error;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }
}
