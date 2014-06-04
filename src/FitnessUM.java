
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author frmendes
 */

public class FitnessUM {

    private boolean active;
    private UserController userController;


   private static final String[] startOptions = { "Exit", "Register", "Login" };

   private static final String[] mainOptions = {

       "Logout", "My Profile", "Friend Requests", "Friend List", "Friends Feed", "Search User", "My Activity Log",
       "Add New Activity Session", "Show My Statistics", "Update Settings"
   };

    private static final String[] activityCategories = {
       "Go Back", "Simple Activities", "Distance Activities", "Altitude Activities"
   };

   private static final String[] statsOptions = {
       "Go Back", "Check all the statistics", "Check statistics for one activity"
   };

   private static final String[] activities = {
       "Go Back", "Cycling", "Kayaking", "Kendo", "Running", "Skating", "Swimming"
   };

   private static final String[] adminOptions = {
       "Logout", "Add Admin", "Delete User", "Add Event", "Update Event", "Delete Event"
   };



    /** Empty constructor
     */
    public FitnessUM() {
        this.userController = new UserController();
    }

    /** Parameterized constructor
     * @param users existing UserDatabase
     */
    public FitnessUM(UserController userController) {
        this.userController = userController.clone();
    }

    /** Copy constructor
     * @param fit existing FitnessUM app
     */
    public FitnessUM(FitnessUM fit) {
        this.userController = fit.getUserController();
    }

    /** Getter for logged in user ID
     * @return logged in user ID
     */
    public User getUserCurrentUser() {
        return this.userController.getCurrentUser();
    }

    public UserController getUserController() {
        return this.userController.clone();
    }


    public void setUserController(UserController uc) {
        this.userController = uc.clone();
    }



    /** Getter for active variable
     * @return active variable
     */
    public boolean isActive() {
        return this.active;
    }

    /** Setter for active variable
     */
    public void startup() {
        this.active = true;
    }

    /** Setter for active variable
     */
    public void shutdown() {
        try{
            this.active = false;
            this.userController.writeToFile("dataFile");
        }
        catch(IOException e){System.out.println("Write error");}
    }

    /** Scans for information and saves the user into the database
      */
    public void registerUser() {
        String name = Scan.name("\nFirst name: ") + " " + Scan.name("\nLast name: ");
        String email = Scan.email();

        while ( ! this.userController.validUserEmail(email) ) {
            System.out.println("Email is already taken");
            email = Scan.email();
        }

        String password = Scan.password();
        UserInfo info = readUserInfo();

        this.userController.registerUser(name, email, password, info);
        this.userController.loginUser(email, password);
    }

    public void registerAdmin() {
        String name = Scan.name("\nAdmin name: ");
        String email = Scan.email();

        while( ! this.userController.validAdminEmail(email) ) {
            System.out.println("Invalid email");
            email = Scan.email();
        }

        String password = Scan.password();

        this.userController.registerAdmin(name, password, email);
    }

    public void updateUser() {
        System.out.println("You are about to update your settings.\nIf you do not wish to update a particular field, simply press Enter or input 0 in numeric fields.");

        String name = Scan.updateName("\nFirst name: ") + " " + Scan.updateName("\nLast name: ");
        String email = Scan.updateEmail();

        while ( email.length() > 0 && ! this.userController.validUserEmail(email) ) {
            System.out.println("Email is already taken");
            email = Scan.updateEmail();
        }

        String password = Scan.updatePassword();
        UserInfo info = readUpdateInfo();

        try {
            this.userController.updateUser(name, email, password, info);
        } catch (InexistingUserException e) {
            System.out.println("Fatal error, inexisting user");
            this.shutdown();
        }
    }

    private void searchUserByName() {
        String name = Scan.name("Enter a name:");
        new SearchUserNavigator( this.userController.nameSearch(name), this ).navigate();
    }

    private void searchUserByEmail() {
        String email = Scan.email();
        new SearchUserNavigator( this.userController.emailSearch(email), this ).navigate();
    }

    public void searchUser() {
        final FitnessUM app = this;

        Prompt[] p = new Prompt[] {
            new Prompt() { public void exec() { return; } },
            new Prompt() { public void exec() { app.searchUserByName(); } },
            new Prompt() { public void exec() { app.searchUserByEmail(); } }
         };

        System.out.println("\n0. Go Back\n1. By Name\n2. By Email\n");

        int option = Scan.menuOption(0, 2);
         p[option].exec();
    }

    public void logoutUser() {
        if ( this.userController.isAdminLogin() )
            this.userController.logoutAdmin();
    }

    /** Scans for valid login info and sets the current_user
     */
    public void loginUser() {
        int nrAttempts = 0;
        boolean logged = false;

        while (nrAttempts < 3 && !logged) {
            String email = Scan.scanString("Enter email:");

            while ( !this.userController.existsEmail(email) ) {
               System.out.println("We have no record of that email...");
               email = Scan.scanString("Enter email:");
            }

            String pw = Scan.scanString("Enter password:");

            if ( this.userController.loginUser(email, pw) )
                logged = true;
            else
                System.out.println("Password and email don't match. " + (3 - ++nrAttempts) + " attempt(s) remaining.");
        }

        if (! logged) {
            System.out.println("Too many failed attempts. We called the cops.\nBye bye.");
            this.shutdown();
        }
        else greet();
    }

    public void deleteUser() {
        // missing delete from events
        String email = Scan.email();

        while( !this.userController.existsUser(email) ) {
            System.out.println("User does not exist");
            email = Scan.email();
        }

        String answer = Scan.yesNo("Are you sure you want to delete user with given email?");
        if ( answer.equals("yes") || answer.equals("y") )
            try {
                this.userController.deleteUser(email);
            } catch (InexistingUserException e) {
                System.out.println("User does not exist");
            }
    }

    private void greet() {
        if( this.userController.getCurrentUser() != null ) {
            System.out.println("\nWelcome "+ this.userController.getCurrentUser().getName() );
            if ( this.userController.hasFriendRequests() )
                System.out.println("You have friend requests!");
        }
    }

    public void friendsFeed(){
        Set<Tuple<String, Activity>> feed = this.userController.getFriendsFeed();
        for(Tuple<String, Activity> t : feed)
            System.out.println("\t###\nUser: " + t.getKey() + "\n" + t.getValue() + "\t###\n" );
    }

    public void userProfile() {
        System.out.println( this.userController.currentUserProfile() );
        Scan.pressEnterToContinue();
    }

    public void listFriends() {
        new FriendListNavigator( this.userController.getFriendList(), this ).navigate();
    }

    public void addFriend(User u) {
        this.userController.sendFriendRequest(u);
    }

    public void deleteFriend(User u) {
        this.userController.deleteFriend(u);
    }

    public void acceptFriend(User u) {
        this.userController.acceptFriendRequest(u);
    }

    public void rejectFriend(User u) {
        this.userController.rejectFriendRequest(u);
    }
    
    public boolean currentUserHasFriend(User u) {
        return this.userController.getCurrentUser().hasFriend(u);
    }

    private void viewFriendRequests() {
        new FriendRequestsNavigator( this.userController.getFriendRequests(), this ).navigate();
    }

    public void showStatsOverview(){
        System.out.println(userController.showStatsOverview());
    }

    public void showAnnualStats(){
        int year = Scan.scanInt("Insert the year you want to check.");
        try{
        System.out.println( userController.showAnnualStats(year) );
        }
        catch(StatsNotAvailable s){System.out.println("No Stats Available");}

    }

    public void showMonthlyStats(){
        int year = Scan.intInRange("Insert the year you want to check.", 0, (new GregorianCalendar()).get(Calendar.YEAR) );
        int month = Scan.intInRange("Insert the month (number).", 1, 12);
        try{
           System.out.println( userController.showMonthlyStats(year, month) );
        }
        catch(StatsNotAvailable s){System.out.println("No Stats Available");}

    }

    public void removeActivity(Activity act){
        this.userController.removeActivity(act);
    }

    public void getAddActivityOption(){
        System.out.println("Choose one of the following options.");
        FitnessUM.printActivities();
        int option = Scan.menuOption(0, 6);
        this.getAddActivityPrompt()[option].exec();
    }

    public void getStatsTypeOption(){
        System.out.println("Choose one of the following options.");
        System.out.println("0.Go Back");
        System.out.println("1.Statistics Overview");
        System.out.println("2.Statistics for a given year");
        System.out.println("3.Statistics for a given year and month");
        int option = Scan.menuOption(0,3);
        this.getStatsTypePrompt()[option].exec();
    }

    public static String listWeatherOptions(){
        String[] list = Weather.weatherStates;
        StringBuilder result = new StringBuilder();
        result.append("How was the weather?\n");
        for( String w: list){
            result.append(Weather.getIndexOf(w)).append(".").append(w).append("\n");
        }
        return result.toString() ;
    }

    public void myActivityLog(){
        ArrayList<Activity> list = userController.getMostRecentActivities();

        new ActivityNavigator(list).navigate();
    }

    private Prompt[] getAddActivityPrompt(){
        final FitnessUM app = this;

        return new Prompt[]{
            new Prompt() { public void exec() { return;} },
            new Prompt() { public void exec() { app.addCycling();} },
            new Prompt() { public void exec() { app.addKayaking();} },
            new Prompt() { public void exec() { app.addKendo();} },
            new Prompt() { public void exec() { app.addRunning();} },
            new Prompt() { public void exec() { app.addSkating();} },
            new Prompt() { public void exec() { app.addSwimming();} }
        };
    }
    
    public boolean beforeBirth(GregorianCalendar date){
        long userBirth = userController.getCurrentUser().getInfo().getBirthDate().getTimeInMillis();
        if (date.getTimeInMillis() < userBirth) return true;
        else return false;
        
    }
    
    public GregorianCalendar getStartDate(){
        GregorianCalendar date = Scan.dateWithHours("When did you practice this activity?(dd-mm-yyyy)", "When did you start (hh:mm:ss)");
        if( beforeBirth(date) ) 
            this.getStartDate();
        return date;
    }

    public long getDuration(GregorianCalendar startDate){
        GregorianCalendar endDate = Scan.time("When did you finish? (hh:mm:ss)");
        endDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
        return endDate.getTimeInMillis() - startDate.getTimeInMillis();
    }

    public void addCycling(){
        GregorianCalendar startDate = new GregorianCalendar();
        long duration = 0;

        while(duration <= 0){
            startDate = getStartDate();
            duration = getDuration(startDate);
            if (duration <= 0)
                System.out.println("Invalid finish time\n");
        }

        int distance = Scan.scanInt("What was the distance? (meters)");
        int altitude = Scan.scanInt("What was the altitude? (meters)");
        this.listWeatherOptions();
        int weather = Scan.scanInt(this.listWeatherOptions());

        this.userController.addActivity( new Cycling(startDate, duration, distance, altitude, weather));
    }

    public void addKayaking(){
        GregorianCalendar startDate = new GregorianCalendar();
        long duration = 0;

        while(duration <= 0){
            startDate = getStartDate();
            duration = getDuration(startDate);
            if (duration <= 0)
                System.out.println("Invalid finish time\n");
        }

        int distance = Scan.scanInt("What was the distance? (meters)");
        this.listWeatherOptions();
        int weather = Scan.scanInt(this.listWeatherOptions());

        this.userController.addActivity( new Kayaking(startDate, duration, distance, weather));
    }

    public void addKendo(){
        GregorianCalendar startDate = new GregorianCalendar();
        long duration = 0;

        while(duration <= 0){
            startDate = getStartDate();
            duration = getDuration(startDate);
            if (duration <= 0)
                System.out.println("Invalid finish time\n");
        }

        this.userController.addActivity( new Kendo(startDate, duration));
    }

    public void addRunning(){
        GregorianCalendar startDate = new GregorianCalendar();
        long duration = 0;

        while(duration <= 0){
            startDate = getStartDate();
            duration = getDuration(startDate);
            if (duration <= 0)
                System.out.println("Invalid finish time\n");
        }

        int distance = Scan.scanInt("What was the distance? (meters)");
        int altitude = Scan.scanInt("What was the altitude? (meters)");
        this.listWeatherOptions();
        int weather = Scan.scanInt(this.listWeatherOptions());

        this.userController.addActivity( new Running(startDate, duration, distance, altitude, weather));
    }

    public void addSkating(){
        GregorianCalendar startDate = new GregorianCalendar();
        long duration = 0;

        while(duration <= 0){
            startDate = getStartDate();
            duration = getDuration(startDate);
            if (duration <= 0)
                System.out.println("Invalid finish time\n");
        }

        this.userController.addActivity( new Skating(startDate, duration));
    }

    public void addSwimming(){
        GregorianCalendar startDate = new GregorianCalendar();
        long duration = 0;

        while(duration <= 0){
            startDate = getStartDate();
            duration = getDuration(startDate);
            if (duration <= 0)
                System.out.println("\nINVALID FINISH TIME\n");
        }

        int distance = Scan.scanInt("What was the distance? (meters)");

        this.userController.addActivity( new Swimming(startDate, duration, distance));
    }

	/** Scans the admin for event details, saving the event in the event controller
	 */
	public void addEvent() {
		System.out.println("Yet to be implemented");
	}

	/** Scans the admin for event name, prompting for new event details and updating it
	 */
	public void updateEvent() {
		System.out.println("Yet to be implemented");
	}

	/** Scans the admin for event name, deleting the event
	 */
	public void deleteEvent() {
		System.out.println("Yet to be implemented");
	}

    /** Scans the user for gender, height, weight, birth date and favorite sport
     * @return u UserInfo containing scanned information
     */
    public static UserInfo readUserInfo(){
        UserInfo u = new UserInfo();
        u.setGender( Scan.gender() );
        u.setHeight( Scan.height() );
        u.setWeight( Scan.weight() );
        u.setBirthDate( Scan.date("When were you born? (dd-mm-yyyy)") );
        u.setFavoriteSport( Scan.sport() );

        return u;
    }

    public static void importData(){
        FitnessUM app = new FitnessUM();
        UserController uc = new UserController();
        try{
            uc.readFromFile("dataFile");
            app.setUserController(uc);
            app.run();
        }
        catch(Exception s){System.out.println("Loading error\n");}
    }

    /** Scans the user for up to date height, weight and favorite sport
     * @return User Info containing scanned info
     */
    public static UserInfo readUpdateInfo() {
        UserInfo u = new UserInfo();
        u.setHeight( Scan.updateHeight() );
        u.setWeight( Scan.updateWeight() );
        u.setFavoriteSport( Scan.updateSport() );
        return u;
    }

    /** Reads an integer from the user input and starts up or shuts down the app accordingly
     */
    public void getStartOption() {
        this.startup();
        System.out.println("Choose one of the following options.");
        FitnessUM.printStartOptions();
        int option = Scan.menuOption(0, 2);
        this.getStartPrompt()[option].exec();
    }

    /** Shows the main options for regular users, reading the input for the options
     *  and launching the corresponding events accordingly
     */
    public void userInterpreter() {
        System.out.println( "Choose one of the following options.");
	    FitnessUM.printMainOptions();
        int option = Scan.menuOption(0, 9);
        this.getMainPrompt()[option].exec();
    }

    /** Shows the main options for admin users, reading the input and launching events
     * Beware as these options include creating and destroying events as well destroying users
     */
    public void adminInterpreter() {
        System.out.println("You are on an admin account. We trust you know what you are doing.\nWith great power comes great responsability.\n");
        FitnessUM.printAdminOptions();
        int option = Scan.menuOption(0, 5);
        this.getAdminPrompt()[option].exec();
    }


    /** Controls the main flow of events.
     */
    public void run() {
        System.out.println("\nWelcome to FitnessUM");

        this.getStartOption();
        if( this.userController.isAdminLogin() )
            while( this.isActive() )
                this.adminInterpreter();

        else
            while( this.isActive() )
              this.userInterpreter();
    }

    private Prompt[] getStartPrompt() {
        final FitnessUM app = this;
        return new Prompt[] {
            new Prompt() { public void exec() { FitnessUM.devPrompt(); app.shutdown(); } },
            new Prompt() { public void exec() { app.registerUser();} },
            new Prompt() { public void exec() { app.loginUser();} }
        };
    }

    private Prompt[] getAdminPrompt() {
        final FitnessUM app = this;
        return new Prompt[] {
            new Prompt() { public void exec() { app.logoutUser(); app.run(); } },
            new Prompt() { public void exec() { app.registerAdmin(); } },
            new Prompt() { public void exec() { app.deleteUser(); } },
            new Prompt() { public void exec() { app.addEvent(); } },
            new Prompt() { public void exec() { app.updateEvent(); } },
            new Prompt() { public void exec() { app.deleteEvent(); } }
        };
    }

    private Prompt[] getMainPrompt() {
        final FitnessUM app = this;
        return new Prompt[] {
            new Prompt() { public void exec() { app.logoutUser(); app.run(); } },
            new Prompt() { public void exec() { app.userProfile(); } },
            new Prompt() { public void exec() { app.viewFriendRequests(); } },
            new Prompt() { public void exec() { app.listFriends(); }},
            new Prompt() { public void exec() { app.friendsFeed(); }},
            new Prompt() { public void exec() { app.searchUser(); }},
            new Prompt() { public void exec() { app.myActivityLog(); }},
            new Prompt() { public void exec() { app.getAddActivityOption(); } },
            new Prompt() { public void exec() { app.getStatsTypeOption(); } },
            new Prompt() { public void exec() { app.updateUser(); } }
        };
    }

    private static Prompt[] getDevPrompt() {
        return new Prompt[] {
            new Prompt() { public void exec() { System.out.println("\nBye bye."); } },
            new Prompt() { public void exec() { new FitnessUM( new Seed().generate() ).run(); } },
            new Prompt() { public void exec() { importData(); } }
        };
     }

     public Prompt[] getStatsTypePrompt(){
        final FitnessUM app = this;
        return new Prompt[]{
            new Prompt() { public void exec() { return; }},
            new Prompt() { public void exec() { app.showStatsOverview(); } },
            new Prompt() { public void exec() { app.showAnnualStats(); } },
            new Prompt() { public void exec() { app.showMonthlyStats(); } }
        };
    }

    private static void devPrompt() {
        System.out.println("Do you wish to import an existing network or create a new one?\n0. Exit\n1. Create\n2. Import");

        int option = Scan.menuOption(0, 2);
        FitnessUM.getDevPrompt()[option].exec();
    }

    private static void printStartOptions() {
        int i = 0;
        for (String s : FitnessUM.startOptions)
            System.out.println(i++ + ". " + s);
    }

    private static void printActivityCategories() {
        int i = 0;
        for (String s : FitnessUM.activityCategories)
            System.out.println(i++ + ". " + s);
    }

    private static void printMainOptions() {
        int i = 0;
        for (String s : FitnessUM.mainOptions)
            System.out.println(i++ + ". " + s);
    }

    private static void printStatsOptions() {
        int i = 0;
        for (String s : FitnessUM.statsOptions)
            System.out.println(i++ + ". " + s);
    }

    private static void printActivities() {
        int i = 0;
        for (String s : FitnessUM.activities)
            System.out.println(i++ + ". " + s);
    }

    private static void printAdminOptions() {
        int i = 0;
        for(String s : FitnessUM.adminOptions)
            System.out.println(i++ + ". " + s);
    }


    public FitnessUM clone() {
        return new FitnessUM(this);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to FitnessUM Dev Prompt.");
        FitnessUM.devPrompt();
    }
}
