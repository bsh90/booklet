package library.booklet.exception;

public class NotValidEmailException extends RuntimeException{
    public NotValidEmailException(String message) {
        super(message);
    }
}
