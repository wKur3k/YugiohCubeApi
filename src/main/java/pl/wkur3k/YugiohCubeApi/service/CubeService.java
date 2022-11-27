package pl.wkur3k.YugiohCubeApi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wkur3k.YugiohCubeApi.dto.CubeDTO;
import pl.wkur3k.YugiohCubeApi.exception.CubeNotFoundException;
import pl.wkur3k.YugiohCubeApi.exception.InvalidCredentialsException;
import pl.wkur3k.YugiohCubeApi.exception.NotOwnerOfCubeException;
import pl.wkur3k.YugiohCubeApi.model.Cube;
import pl.wkur3k.YugiohCubeApi.model.Decklist;
import pl.wkur3k.YugiohCubeApi.model.User;
import pl.wkur3k.YugiohCubeApi.repository.CubeRepository;
import pl.wkur3k.YugiohCubeApi.repository.UserRepository;
import pl.wkur3k.YugiohCubeApi.security.JwtTokenProvider;

import java.util.*;

@Service
@AllArgsConstructor
public class CubeService {
    private CubeRepository cubeRepository;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public Long createCube(CubeDTO dto, String authHeader) {
        Cube newCube = new Cube();
        User owner = userRepository.findById(jwtTokenProvider.getUserIdFromToken(authHeader.substring(7, authHeader.length()))).get();
        newCube.setOwner(owner);
        newCube.setName(dto.getName());
        Decklist newDecklist = new Decklist();
        newDecklist.setCube(newCube);
        newDecklist.setCardlist(dto.getDecklist().toString());
        newCube.setDecklist(newDecklist);
        cubeRepository.save(newCube);
        return newCube.getId();
    }

    public void deleteCube(Long id, String authHeader) {
        if (getCubeById(id).isPresent()) {
            if(checkIfOwner(id, authHeader)) {
                cubeRepository.deleteById(id);
            }else{
                throw new NotOwnerOfCubeException();
            }
        }else{
            throw new CubeNotFoundException();
        }
    }

    public void updateDecklist(Long id, ArrayList<String> decklist, String authHeader) {
        Optional<Cube> cube = cubeRepository.findById(id);
        if(cube.isPresent()) {
            if (checkIfOwner(id, authHeader)) {
                Decklist newDecklist = new Decklist();
                newDecklist.setCardlist(String.join(",", decklist));
                cube.get().setDecklist(newDecklist);
            } else {
                throw new NotOwnerOfCubeException();
            }
        }else{
            throw new CubeNotFoundException();
        }

    }
    public void addParticipant (Long id, ArrayList<String> participants, String authHeader){
        Optional<Cube> cube = cubeRepository.findById(id);
        if(cube.isPresent()){
            if(checkIfOwner(id, authHeader)){
                for (String username: participants) {
                    Optional<User> user = userRepository.findByUsername(username);
                    if(user.isPresent())
                        cube.get().addParticipant(user.get());
                }
                cubeRepository.save(cube.get());
            }
            else{
                throw new NotOwnerOfCubeException();
            }
        }else{
            throw new CubeNotFoundException();
        }
    }
    public void removeParticipants(Long id, ArrayList<String> participants, String authHeader){
        Optional<Cube> cube = cubeRepository.findById(id);
        if(cube.isPresent()){
            if(checkIfOwner(id, authHeader)){
                for (String username: participants) {
                    Optional<User> user = userRepository.findByUsername(username);
                    if(user.isPresent())
                        cube.get().deleteParticipant(user.get());
                }
                cubeRepository.save(cube.get());
            }
            else{
                throw new NotOwnerOfCubeException();
            }
        }else{
            throw new CubeNotFoundException();
        }
    }
    public List<Cube> getAllCubes(){
        return cubeRepository.findAll();
    }
    public Optional<Cube> getCubeById(Long id){
        return cubeRepository.findById(id);
    }
    public List<Cube> getUserCubes(String authHeader){
        return cubeRepository.findAllByOwnerId(getUserIdFromToken(authHeader));
    }
    private Long getUserIdFromToken(String authHeader){
        return jwtTokenProvider.getUserIdFromToken(authHeader.substring(7, authHeader.length()));
    }
    private boolean checkIfOwner(Long id, String authHeader){
        return cubeRepository.findById(id).get().getOwner().getId() == getUserIdFromToken(authHeader);
    }
}
