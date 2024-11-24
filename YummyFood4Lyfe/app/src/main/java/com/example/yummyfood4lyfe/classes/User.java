package com.example.yummyfood4lyfe.classes;

public class User
{
    private int id;
    private final String username;
    private String password;
    private String email;
    private String name;
    private String bio;
    private int profileImage;
    private int[] userImages;

    public User(String username, String password, String email, String name, String bio, int profileImage, int[] userImages)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.profileImage = profileImage;
        this.userImages = userImages;
    }

    public void getId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getName()
    {
        return name;
    }

    public String getBio()
    {
        return bio;
    }

    public int getProfileImage()
    {
        return profileImage;
    }

    public int[] getUserImages()
    {
        return userImages;
    }
}
