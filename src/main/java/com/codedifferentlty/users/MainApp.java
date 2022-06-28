package com.codedifferentlty.users;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MainApp {
    private Repository repository;

    public MainApp(Repository repository){
        this.repository = repository;
    }

    public static void main(String[] args) {
        try {
            UserFileRepository repo = new UserFileRepository("./data/users.csv");
            MainApp main = new MainApp(repo);
            //User user = new User("Renato", "Rios", "r@r.com", "password");
            //repo.save(user);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
