package pl.wkur3k.YugiohCubeApi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.wkur3k.YugiohCubeApi.dto.CubeDTO;
import pl.wkur3k.YugiohCubeApi.model.Cube;
import pl.wkur3k.YugiohCubeApi.service.CubeService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/cube")
public class CubeController {

    private CubeService cubeService;

    @GetMapping
    public ResponseEntity<List<Cube>> getAllCubes(){
        return ResponseEntity.ok(cubeService.getAllCubes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cube>> getCube(@PathVariable Long id){
        return ResponseEntity.ok(cubeService.getCubeById(id));
    }
    @GetMapping("/user")
    public ResponseEntity<List<Cube>> getUserCubes(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        return ResponseEntity.ok(cubeService.getUserCubes(authHeader));
    }
    @PostMapping
    public ResponseEntity createCube(@RequestBody CubeDTO dto, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        Long id = cubeService.createCube(dto, authHeader);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/cube")
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCube(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        cubeService.deleteCube(id, authHeader);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity editParticipants(@PathVariable Long id, @RequestParam String action, @RequestBody ArrayList<String> participants, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        if(action.equals("add")){
            cubeService.addParticipant(id, participants, authHeader);
        }
        else if(action.equals("remove")){
            cubeService.removeParticipants(id, participants, authHeader);
        }
        return ResponseEntity.noContent().build();
    }
}
