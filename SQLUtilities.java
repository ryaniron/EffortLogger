package application;

import java.util.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;


// THE TWO SQL FILES
// passwords.sql: LoginInformation(username, password)
// tasks.sql: Tasks()


public class SQLUtilities {
    // MUST ADD THESE TWO LINES TO YOUR "module-info.java" FILE
    // requires sqlite.jdbc;
	
    public static boolean loginCredentials(String username, String password) {
    	/* This method checks the login credentials of the user against the database of login information that is stored in the passwords.sqlite file
    	 * It encrypts the username and password and then queries the database for matching credentials. 
    	 * If the provided credentials do not match existing ones, this method returns false, if they do, it returns true
    	 * */
    	
    	// THE RESULT SET STORES THE VALUES THAT ARE RETURNED FROM THE QUERY
        ResultSet returnedUserID = null;
        try {
        	// INITIALIZE THE CURSOR THAT WILL CONNECT TO THE DATABASE 
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/passwords.sqlite");
            
            // A DEFAULT QUERY DEFINES WHAT WE CAN ADD TO THE PREPARED STATEMENT
            String defaultQuery = "SELECT username FROM LoginInformation WHERE username=(?) AND password=(?);";
            PreparedStatement stmnt = conn.prepareStatement(defaultQuery);
            
            // ENCRYPT THE USERNAME AND PASSWORD
            String encryptedUsername = Encryptor.encrypt(username);
            String encryptedPassword = Encryptor.encrypt(password);
            
            // SET THE VALUES IN THE PREPARED STATEMENT
            stmnt.setString(1, encryptedUsername);
            stmnt.setString(2, encryptedPassword);
            returnedUserID = stmnt.executeQuery();
            
            // CHECK IF THE RETURNED RESULTSET IS EMPTY
	        if (!returnedUserID.isBeforeFirst()) {
	        	stmnt.close();
	            conn.close();
	            // THERE ARE NO MATCHING USERS, INVALID CREDENTIALS
	        	return false;
	        } else {
	        	stmnt.close();
	            conn.close();
	            // IF THE RESULTSET IS NOT EMPTY, USER EXISTS, RETURN TRUE
	        	return true;
	        }    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    // MUST FINALIZE THE STRUCTURE OF THE SQL FILE
    public static void enterTask(String taskName, String taskDescription, String projectName, String employeeUsername,  int expectedDuration, Integer completed) {
    	String completedBy = null;
    	if (completed == null) {
    		completed = 0;
    	} else {
    		completedBy = taskName;
    	}
    	
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/tasks.sqlite");
            conn.setAutoCommit(false);
            // CHECK IF THE TASK ALREADY EXISTS?
            String defaultQuery = "INSERT INTO Tasks (taskName, taskDescription, projectName, createdBy, createdOnUTC, expectedDuration, alteredOnUTC, lastAlteredBy, completed, completedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stmnt = conn.prepareStatement(defaultQuery);
            java.util.Date utc = new java.util.Date();
            java.sql.Date sqlUTC = new java.sql.Date(utc.getTime());

            // ADD THE COMPONENTS TO THE PREPARED STATEMENT
            stmnt.setString(1, taskName);
            stmnt.setString(2, taskDescription);
            stmnt.setString(3, projectName);
            stmnt.setString(4, employeeUsername);
            stmnt.setDate(5, sqlUTC);
            stmnt.setInt(6, expectedDuration);
            stmnt.setDate(7, sqlUTC);
            stmnt.setString(8, employeeUsername);
            stmnt.setInt(9, completed);
            stmnt.setString(10, completedBy);
            stmnt.executeUpdate();
            stmnt.close();
            conn.commit();
            conn.close();
        } catch (Exception z) {
            z.printStackTrace();
            System.out.println(z.getLocalizedMessage());
        }
    }

    // ALTER TASK, MAKE DEFAULT VALUES?
    public static void alterTask(String taskName) {
    	try {
            Class.forName("org.sqlite.JDBC");  // REQUIRES A SPECIAL MODULE, SEE LINE 45
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/tasks.sqlite");

            String defaultQuery = "UPDATE Tasks SET expectedDuration=(?) WHERE taskName=(?);";
            PreparedStatement alterTaskStatement = conn.prepareStatement(defaultQuery);
            // PLACE THE CHANGES THAT ARE INSERTED HERE
            alterTaskStatement.setString(-1, taskName);
            // CLOSE THE STATEMENT AND THE DATABASE
            alterTaskStatement.execute();
            alterTaskStatement.close();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // SOMEWHAT COMPLETED, MUST COMPLETE WHAT IS SHOULD BE RETRIEVED FIRST
    public static ArrayList<Dictionary> retrieveEmployeeTasks(String username) {
    	ArrayList<Dictionary> results = new ArrayList<Dictionary>();
        ResultSet returnedResults = null;
    	
    	try {
            Class.forName("org.sqlite.JDBC");  // REQUIRES A SPECIAL MODULE, SEE LINE 45
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/tasks.sqlite");

            String defaultQuery = "SELECT * FROM Tasks WHERE createdBy=(?)";
            PreparedStatement nameInfoRetriever = conn.prepareStatement(defaultQuery);
            nameInfoRetriever.setString(1, username);
            returnedResults = nameInfoRetriever.executeQuery();

            // CLOSE THE STATEMENT AND THE DATABASE
            nameInfoRetriever.close();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (returnedResults != null) {
                while (returnedResults.next()) {
                	Dictionary<String, String> taskDict = new Hashtable<>();
                	taskDict.put("taskName", returnedResults.getString("taskName"));
                	taskDict.put("taskDescription", returnedResults.getString("taskDescription"));
                	taskDict.put("expectedDuration", returnedResults.getString("taskDescription"));
                	// ADD MORE HERE AS NEEDED
                	results.add(taskDict);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	return results;
    }

    public static ArrayList<Dictionary> retrieveTaskLogs() {
    	ArrayList<Dictionary> results = new ArrayList<Dictionary>();
        ResultSet returnedResults = null;
    	
    	try {
            Class.forName("org.sqlite.JDBC");  // REQUIRES A SPECIAL MODULE, SEE LINE 45
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/tasks.sqlite");
            conn.setAutoCommit(false);
            String defaultQuery = "SELECT * FROM Tasks";
            PreparedStatement nameInfoRetriever = conn.prepareStatement(defaultQuery);
            returnedResults = nameInfoRetriever.executeQuery();
        	// System.out.println(returnedResults.absolute(0));
            
            if (returnedResults != null) {
            	while (returnedResults.next()){
                	Dictionary<String, String> taskDict = new Hashtable<>();
                	taskDict.put("taskName", returnedResults.getString("taskName"));
                	taskDict.put("taskDescription", returnedResults.getString("taskDescription"));
                	taskDict.put("projectName", returnedResults.getString("projectName"));
                	taskDict.put("expectedDuration", returnedResults.getString("expectedDuration"));
                	taskDict.put("alteredOnUTC", returnedResults.getString("alteredOnUTC"));
                	taskDict.put("lastAlteredBy", returnedResults.getString("lastAlteredBy"));
                	// ADD MORE HERE AS NEEDED
                	results.add(taskDict);
                } 
            }

            // CLOSE THE STATEMENT AND THE DATABASE
            nameInfoRetriever.close();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	return results;
    }

    //////////////////////
    // THE FOLLOWING FUNCTIONS ARE RELEVANT ONLY TO CREATING AND MODIFYING THE SQL FILES TO REMAKE THEM OR EDIT THEM DIRECTLY
    //////////////////////
    public static boolean insertLoginCredentials(String username, String password) {
    	// ENCRYPT THE USERNAME AND PASSWORD
    	String encryptedUsername = Encryptor.encrypt(username);
        String encryptedPassword = Encryptor.encrypt(password);

        try {
            Class.forName("org.sqlite.JDBC");  // REQUIRES A SPECIAL MODULE
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/passwords.sqlite");
            conn.setAutoCommit(false);

          
            // CHECK TO SEE IF THE USERNAME ALREADY EXISTS
            String checkDuplicateQuery = "SELECT username FROM LoginInformation WHERE username=(?);";
            PreparedStatement dupeStmnt = conn.prepareStatement(checkDuplicateQuery);
            dupeStmnt.setString(1, encryptedUsername);
            ResultSet checkDupe = dupeStmnt.executeQuery();
            
            // CHECK IF THE RETURNED RESULTSET IS EMPTY
	        if (checkDupe.isBeforeFirst()) {
	        	dupeStmnt.close();
	            conn.close();
	            // THERE ARE MATCHING USERS
	        	return false;
	        } 
	        dupeStmnt.close();
            
            // INSERT THE NEW VALUES TO THE 
	        String defaultQuery = "INSERT INTO LoginInformation (username, password) VALUES (?, ?);";
            PreparedStatement inputCleaner = conn.prepareStatement(defaultQuery);
           
            // SET THE VALUES OF THE PREPARED STATEMENT
            inputCleaner.setString(1, encryptedUsername);
            inputCleaner.setString(2, encryptedPassword);
            inputCleaner.executeUpdate();

            // CLOSE THE STATEMENT AND THE DATABASE
            inputCleaner.close();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // INITIALIZATION FUNCTIONS TO CREATE THE DATABASES
    @SuppressWarnings("unused")
	private static boolean createTaskDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/tasks.sqlite");
            conn.setAutoCommit(false);
            Statement stmnt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Tasks (taskName text NOT NULL, taskDescription text NOT NULL, projectName text NOT NULL, createdBy text NOT NULL, createdOnUTC datetime NOT NULL, expectedDuration int NOT NULL, alteredOnUTC datetime NOT NULL, lastAlteredBy text NOT NULL, completed int NOT NULL, completedBy text);";
            stmnt.executeUpdate(query);
            stmnt.close();
            conn.commit();
            conn.close();
        } catch (Exception z) {
            z.printStackTrace();
            System.out.println(z.getLocalizedMessage());
            return false;
        }

        return true;
    }
    
    @SuppressWarnings("unused")
    private static boolean createLoginDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/passwords.sqlite");
            conn.setAutoCommit(false);
            Statement stmnt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS LoginInformation (username text NOT NULL, password text NOT NULL);";
            stmnt.executeUpdate(query);
            stmnt.close();
            conn.commit();
            conn.close();
        } catch (Exception z) {
            z.printStackTrace();
            System.out.println(z.getLocalizedMessage());
            return false;
        }

        return true;
    }

    // DO NOT USE ONCE COMPLETED
    @SuppressWarnings("unused")
    private static boolean deleteFromLoginSQLFile() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/passwords.sqlite");
            conn.setAutoCommit(false);
            Statement stmnt = conn.createStatement();
            String query = "DROP TABLE LoginInformation;";
            stmnt.executeUpdate(query);
            stmnt.close();
            conn.commit();
            conn.close();
        } catch (Exception z) {
            z.printStackTrace();
            System.out.println(z.getLocalizedMessage());
            return false;
        }

        return true;
    }

    // DO NOT USE ONCE COMPLETED
    @SuppressWarnings("unused")
    private static boolean clearTaskSQLFile() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/resources/tasks.sqlite");
            conn.setAutoCommit(false);
            Statement stmnt = conn.createStatement();
            String query = "DROP TABLE Tasks;";
            stmnt.executeUpdate(query);
            stmnt.close();
            conn.commit();
            conn.close();
        } catch (Exception z) {
            z.printStackTrace();
            System.out.println(z.getLocalizedMessage());
            return false;
        }

        return true;
    }

    // THIS IS AN OLD EXAMPLE QUERY THAT SHOULD BE REMOVED WHEN NO LONGER NEEDED FOR REFERENCE
    public ArrayList<String> retrieveName(String filename, String name) {
        ArrayList<String> retrievedResults = new ArrayList<String>();
        ResultSet results = null;
        try {
            Class.forName("org.sqlite.JDBC");  // REQUIRES A SPECIAL MODULE, SEE LINE 45
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/example/effortlogger/" + filename);
            conn.setAutoCommit(false);

            String defaultQuery = "SELECT * FROM ExampleNames WHERE  name=(?)";
            PreparedStatement nameInfoRetriever = conn.prepareStatement(defaultQuery);
            nameInfoRetriever.setString(1, name);
            results = nameInfoRetriever.executeQuery();

            // CLOSE THE STATEMENT AND THE DATABASE
            nameInfoRetriever.close();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (results != null) {
                while (results.next()) {
                    retrievedResults.add(results.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retrievedResults;
    }

    public static void main(String[] args) {
//    	createLoginDatabase();
    	if (insertLoginCredentials("Jack Black", "8734")) {
    		System.out.println("Inserted new user");
    	} else {
    		System.out.println("User already exists");
    	}
    	
//    	deleteFromLoginSQLFile();
//    	clearTaskSQLFile();
//    	createTaskDatabase();
//    	enterTask("ExampleTask2", "An Example 2", "An Example project 2", "Lynn Carter", 8, null);
    	
    	// System.out.println(retrieveTaskLogs());
    }
}
