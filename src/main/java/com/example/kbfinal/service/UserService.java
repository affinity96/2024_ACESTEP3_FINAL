package com.example.kbfinal.service;

import com.example.kbfinal.aop.EncAndDec;
import com.example.kbfinal.entity.User;
import com.example.kbfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncAndDec encAndDec;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // 비밀번호를 암호화하여 저장
        // password를 인코딩
        // user entity에 인코딩 된 password를 넣기
        System.out.println("등록할 user: " + user);
        String unencodedPassword = user.getPassword();
        // 비밀번호를 Base64로 인코딩
        String encodedPassword = encAndDec.encryptPassword(unencodedPassword);
        user.setPassword(encodedPassword);


        return userRepository.save(user);

    }
    public User createUser() {
        //비밀번호 인코딩 디코딩이 잘 되는지 테스트
        User user = User.builder()
                .username("lee")
                .password(encAndDec.encryptPassword("123"))
                .email("hi@gmail.com")
                .age(21)
                .address("seoul")
                .build();



        return userRepository.save(user);

    }
   public boolean authenticate(String username, String password) {
       // 사용자 조회
       User user = userRepository.findByUsername(username); // 직접 repo에서 구현
       if (user == null) {
           return false;
       }
       // 입력된 비밀번호와 저장된 암호화된 비밀번호를 비교
       String encodedPasswordForComp = encAndDec.encryptPassword(password);
       return encodedPasswordForComp.equals(user.getPassword());

   }

   public List<User> getAllUsers(){

        return userRepository.findAll();
   }


    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


    // 이후 컨트롤러에서 들어오게 될  내용 추가 구현하기

    public User editUser(Long id, User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUsername(user.getUsername() != null ? user.getUsername() : existingUser.getUsername());
            existingUser.setPassword(user.getPassword() != null ? encAndDec.encryptPassword(user.getPassword()) : existingUser.getPassword());
            existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
            existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
            existingUser.setAddress(user.getAddress() != null ? user.getAddress() : existingUser.getAddress());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("id에 해당하는 user 정보가 없어요. 입력한 id: " + id);
        }
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("id에 해당하는 user 정보가 없어요. 입력한 id: " + id);
        }
    }

    public long getUserCount() {
        return userRepository.count();
    }

}
