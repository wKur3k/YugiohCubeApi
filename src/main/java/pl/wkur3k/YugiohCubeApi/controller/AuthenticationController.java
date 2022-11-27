package pl.wkur3k.YugiohCubeApi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.wkur3k.YugiohCubeApi.dto.UserRegisterDTO;
import pl.wkur3k.YugiohCubeApi.dto.UserLoginDTO;
import pl.wkur3k.YugiohCubeApi.model.User;
import pl.wkur3k.YugiohCubeApi.service.UserService;

import java.net.URI;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthenticationController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO dto){
        return ResponseEntity.ok(userService.logIn(dto));
    }
    @PostMapping("register")
    public ResponseEntity registerUser(@RequestBody UserRegisterDTO dto){
        userService.registerUser(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/login").build().toUri();
        return ResponseEntity.created(location).build();
    }
}
