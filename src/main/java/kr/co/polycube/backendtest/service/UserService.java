package kr.co.polycube.backendtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.polycube.backendtest.exception.CustomException;
import kr.co.polycube.backendtest.model.UserInfo;
import kr.co.polycube.backendtest.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;

    public UserInfo registerUser(Long id, String name) {
        UserInfo user = new UserInfo(id, name);
        return userRepository.save(user);
    }

    public UserInfo getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));
    }

    public UserInfo updateUser(Long id, String name) {
        UserInfo user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));
        user.setName(name);
        return userRepository.save(user);
    }

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

}
