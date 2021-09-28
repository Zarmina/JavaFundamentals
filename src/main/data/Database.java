package main.data;

import main.model.User;

import java.util.ArrayList;
import java.util.List;

public class Database
{
    private List<User> users;

    public Database()
    {
        users = new ArrayList<>();

        // filling the list of users
        fillUsersList();

    }

    public List<User> getUsers()
    {
        return users;
    }

    private void fillUsersList()
    {
        // creating dummy objects to seed data
        User Zarmina = new User("Zimi", "ZarminaGill123!!", "Zarmina", "Abbas");
        User Mark = new User("Flashpoint", "marky", "Mark", "something");

        users.add(Zarmina);
        users.add(Mark);


    }


}
