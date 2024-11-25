package com.example.yummyfood4lyfe.classes;

public class User
{
    private int id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String bio;
    private int profileImage;
    private String birthday;

    public User(){
    }

    //Constructor for creating a new user
    public User(String username, String email, String birthday, String password)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.name = null;
        this.bio = null;
        this.profileImage = 0;
    }

    //Constructor for getting user info from database
    public User(String username, String email, String birthday, String password, String name, String bio, int profileImage)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.name = name;
        this.bio = bio;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
