/**
 *
 * @author tiago
 */

import java.util.GregorianCalendar;

public class Skating extends Activity {
    
    public Skating() 
    {super();}
    
    public Skating(GregorianCalendar date, long duration){
        super(date, duration);
        this.setCalories( calculateCalories(duration) );
    }
    
    public Skating (Skating s)
    {super(s);}
    
    public Skating clone()
    {return new Skating(this);}
    
    public double calculateCalories(long duration)
    {return (double) duration*0.00006;}
}