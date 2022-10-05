package eventplanner.core;


public class User {
    
    private String userName;

    public User(String userName){
        if (userName==null || userName.isBlank()){
            throw new IllegalArgumentException("User name is null or blank");
        }
        if (userName.contains(",")) {
            throw new IllegalArgumentException("Username contains a comma");
        }
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

}
