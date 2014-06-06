/*
 * Exception to be thrown when user that is being accessed does not exist
 */

/**
 *
 * @author frmendes
 */
public class InexistingUserException extends Exception {

    
    public InexistingUserException() {
        super();
    }
    
    public InexistingUserException(String msg) {
        super(msg);
    }
}
