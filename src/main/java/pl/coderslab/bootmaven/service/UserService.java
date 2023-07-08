package pl.coderslab.bootmaven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bootmaven.UserRepository;
import pl.coderslab.bootmaven.entity.User;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByEmail(String email){
       return userRepository.findByEmail(email);
    }

}
