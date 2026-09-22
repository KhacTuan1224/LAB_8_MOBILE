package thanhdnh.ueh.edu.article_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserList {
    @SerializedName("users")
    @Expose
    private ArrayList<UserProfile> users;

    public UserList(ArrayList<UserProfile> users) {
        this.users = users;
    }

    public ArrayList<UserProfile> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserProfile> users) {
        this.users = users;
    }

    public UserProfile getUserById(int id) {
        for (UserProfile user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
