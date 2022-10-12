package eventplanner.core;

public record User(String username){

    public User(String username){
        if (username==null || username.isBlank()){
            throw new IllegalArgumentException("User name is null or blank");
        }
        this.username = username;
    }
}