package auth;


import db.User;
import db.UserService;

public class AuthenticationService {
    public final static String USER_NOT_FOUND = "User not found", USER_EXISTS = "User exist", WRONG_PASSWORD = "Wrong password", SUCCESS = "Success", FAILURE = "Failure";
    public static UserService userService;

    public static void initDBConnection(){
        userService=new UserService();
    }

    public static void removeDBConnection(){
        userService=null;
    }

    public static String register(String username, String password) {
        User user = userService.findByName(username);
        if (user != null) {
            return USER_EXISTS;
        }
        if (!userService.insert(new User(username, password))) {
            return FAILURE;
        }
        return SUCCESS;
    }

    public static String login(String username, String password) {
        User user = userService.findByName(username);
        if (user == null) {
            return USER_NOT_FOUND;
        }

        if (!user.getPassword().equals(password)) {
            return WRONG_PASSWORD;
        }
        return SUCCESS;
    }

}
