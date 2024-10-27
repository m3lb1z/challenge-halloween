package emrx.monster.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Power {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "power_id")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "powers")
    private Set<Monster> monsters;

    public Power(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(Set<Monster> monsters) {
        this.monsters = monsters;
    }
}