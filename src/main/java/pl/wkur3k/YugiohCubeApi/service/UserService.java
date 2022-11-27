package pl.wkur3k.YugiohCubeApi.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wkur3k.YugiohCubeApi.dto.UserRegisterDTO;
import pl.wkur3k.YugiohCubeApi.dto.UserLoginDTO;
import pl.wkur3k.YugiohCubeApi.exception.InvalidCredentialsException;
import pl.wkur3k.YugiohCubeApi.model.Role;
import pl.wkur3k.YugiohCubeApi.model.User;
import pl.wkur3k.YugiohCubeApi.repository.UserRepository;
import pl.wkur3k.YugiohCubeApi.security.JwtTokenProvider;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String logIn(UserLoginDTO dto){
        try{
            String username = dto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, dto.getPassword()));
            String token = jwtTokenProvider.createToken(username, Collections.singletonList(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + "not found.")).getRole().name()));
            return token;
        }catch(AuthenticationException e){
            throw new InvalidCredentialsException();
        }
    }
    @Override
    public void registerUser(UserRegisterDTO dto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(newUser);
    }
    @Override
    public Iterable<User> getAllUser(){
        return userRepository.findAll();
    }

}
