import java.util.GregorianCalendar;

public abstract class AltitudeActivity extends DistanceActivity{
    int altitude;
    
    //constructors
    public AltitudeActivity(){
       super();
       this.altitude = 0;
   }
   
    public AltitudeActivity(GregorianCalendar date, long duration, int distance, int altitude){
        super(date, duration, distance);
        this.altitude = altitude;
    }
   
    public AltitudeActivity(AltitudeActivity act) {
       super(act);
       this.altitude = act.getAltitude();
   }
   
    //setters
    void setAltitude(int a) {
        this.altitude = a;
    }
    
    //getters
    int getAltitude() {
        return this.altitude;
    }
    
    public String toString() {
        StringBuilder understring = new StringBuilder();
        understring.append("\nAltitude: ");
        understring.append(this.altitude);
        
        return super.toString() + understring.toString();
    }
    
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass() ) return false;
       
        AltitudeActivity act = (AltitudeActivity) o;
       
       return ( super.equals(o) && this.altitude == act.getAltitude() );
    }
}

/*
import java.util.GregorianCalendar;

public class AltitudeActivity extends DistanceActivity{
    int altitude;
    
    //constructors
    public AltitudeActivity(){
       super();
       this.altitude = 0;
   }
   
    public AltitudeActivity(String name, int weather, GregorianCalendar date, GregorianCalendar duration, int calories, int distance, int altitude){
        super(name, weather, date, duration, calories, distance);
        this.altitude = altitude;
    }
   
    public AltitudeActivity(AltitudeActivity aa) {
       super(aa);
       this.altitude = aa.getAltitude();
   }
   
    //setters
    void setAltitude(int a) {
        this.altitude = a;
    }
    
    //getters
    int getAltitude() {
        return this.altitude;
    }
    
    //essentials
    public AltitudeActivity clone() {
       return new AltitudeActivity(this);
    }
    
    public String toString() {
        StringBuilder understring = new StringBuilder();
        understring.append("\nAltitude: ");
        understring.append(this.altitude);
        
        return super.toString() + understring.toString();
    }
    
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass() ) return false;
       
        AltitudeActivity aa = (AltitudeActivity) o;
       
       return ( super.equals(o) && this.altitude == aa.getAltitude() );
    }
}
*/