package main.service;

import main.data.Database;
import main.model.User;

import java.util.List;

public class LoginService
{
    private Database database;
    public LoginService()
    {
        database = new Database();
    }
    public User login(String username, String password)
    {
        // making the list of users here to access them easier
        List<User> users = database.getUsers();

        // initializing user as null to return null if the creds are wrong
        User loggedInUser = null;

        for(User user : users)
        {
            if(user.getUsername().equals(username) && user.getPassword().equals(password))
            {
                loggedInUser = user;
                break;
            }
        }

        return loggedInUser;


    }
}
