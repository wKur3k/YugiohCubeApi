package pl.wkur3k.YugiohCubeApi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Getter
@Setter
public class Decklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(mappedBy = "decklist")
    private Cube cube;
    private String cardlist;
    public ArrayList<String> getCardListAsCollection(){
        return new ArrayList<String>(Arrays.asList(this.cardlist.split(",")));
    }
}
