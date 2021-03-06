
/**Exception for when an activity doesn't exist
 *
 * @author joaorodrigues
 */
public class ActivityNotAvailableException extends Exception {

    /**
     *
     */
    public ActivityNotAvailableException() {
        super();
    }
    
    /**
     *
     * @param msg
     */
    public ActivityNotAvailableException(String msg) {
        super(msg);
    }
}
