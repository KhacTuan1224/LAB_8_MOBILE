package thanhdnh.ueh.edu.article_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName(value = "user_name", alternate = {"userName", "username"})
    @Expose
    private String userName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName(value = "description", alternate = {"desc"})
    @Expose
    private String description;

    @SerializedName(value = "avatar_url", alternate = {"avatarUrl"})
    @Expose
    private String avatarUrl;

    @SerializedName(value = "hobbies", alternate = {"hobies"})
    @Expose
    private String hobbies;

    public UserProfile(int id, String userName, String email, String description,
                       String avatarUrl, String hobbies) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.hobbies = hobbies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
}
