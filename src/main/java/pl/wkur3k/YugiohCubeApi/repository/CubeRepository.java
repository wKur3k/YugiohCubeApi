package pl.wkur3k.YugiohCubeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.wkur3k.YugiohCubeApi.model.Cube;

import java.util.List;

@RepositoryRestResource
public interface CubeRepository extends JpaRepository<Cube, Long> {
    List<Cube> findAllByOwnerId(Long id);
}
