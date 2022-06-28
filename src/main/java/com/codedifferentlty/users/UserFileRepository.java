package com.codedifferentlty.users;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class UserFileRepository implements Repository<User, Long>{
    private static Long idCount = 1l;
    private Set<User> users;
    private String pathToRepo;
    private File file;

    public UserFileRepository(String pathToRepo) throws IOException {
        this.pathToRepo = pathToRepo;
        this.file = new File(pathToRepo);
        users = new TreeSet<>(); // This will cause an error
        file.createNewFile();
        parseUserFromRawData();
    }

    @Override
    public User save(User entity) throws IOException {
        Long entityId = entity.getId();
        if(entityId == null){
            entity.setId(idCount++);
        }
        users.add(entity);
        String data = convertToCsv();
        saveAllDataToFile(data);
        return entity;
    }

    private void parseUserFromRawData() throws IOException {
        Path fileName = Path.of(pathToRepo);
        String rawData = Files.readString(fileName);
        String[] rawUsers = rawData.split("\n");
        for(String rawUser:rawUsers){
            String[] userData = rawUser.split(",");
            User user = new User();
            Long id = Long.parseLong(userData[0]);
            idCount = id + 1;
            user.setId(id);
            user.setFirstName(userData[1]);
            user.setLastName(userData[2]);
            user.setEmail(userData[3]);
            user.setPassword(userData[4]);
            System.out.println(user.toString());
            users.add(user);
        }
    }

    private String convertToCsv(){
        StringBuilder builder = new StringBuilder();
        for (User user:users) {
            String userData = String.format("%d,%s,%s,%s,%s\n",
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword()
            );
            builder.append(userData);
        }
        return builder.toString().trim();
    }

    private void saveAllDataToFile(String data) throws IOException {
        FileWriter writer = new FileWriter(pathToRepo, false);
        writer.write(data);
        writer.close();
    }


    @Override
    public Optional<User> findById(Long id) {
        for(User user:users){
            if(user.getId() == id)
                return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
