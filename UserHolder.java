package application;

/*******
 * <p> Title: UserHolder Class </p>
 * 
 * <p> Description: This class stores the current user's username where any scene controller can access. </p>
 * 
 * 
 * @author Simon Liu
 * 
 * 
 */

public final class UserHolder {
	private String username;
	private final static UserHolder INSTANCE = new UserHolder();
	
	private UserHolder() {}
	
	public static UserHolder getInstance() {
		return INSTANCE;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
}
