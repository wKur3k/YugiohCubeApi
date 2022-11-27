package pl.wkur3k.YugiohCubeApi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cubes")
public class Cube {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user_id")
    private User owner;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "participants", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "cube_id")})
    @JsonIgnore
    @JsonBackReference("user_id")
    private Set<User> participants = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "decklist_id")
    @JsonBackReference("decklist_id")
    private Decklist decklist;

    public void addParticipant(User user){
        this.participants.add(user);
    }
    public void deleteParticipant(User user){
        this.participants.remove(user);
    }
}
