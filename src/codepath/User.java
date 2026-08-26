package codepath;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String hashedPassword;
    private List<String> interests;

    public User(int id, String username, String hashedPassword) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.interests = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getHashedPassword() { return hashedPassword; }
    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }
    public void addInterest(String interest) { this.interests.add(interest); }
}