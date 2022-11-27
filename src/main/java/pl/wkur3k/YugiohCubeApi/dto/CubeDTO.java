package pl.wkur3k.YugiohCubeApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CubeDTO {
    private String name;
    private List<String> decklist;
}
