package pl.wkur3k.YugiohCubeApi.service;

import pl.wkur3k.YugiohCubeApi.dto.UserLoginDTO;
import pl.wkur3k.YugiohCubeApi.dto.UserRegisterDTO;
import pl.wkur3k.YugiohCubeApi.model.User;

import java.util.Map;

public interface IUserService {
    String logIn(UserLoginDTO dto);
    void registerUser(UserRegisterDTO dto);
    Iterable<User> getAllUser();
}
