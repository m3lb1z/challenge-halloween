package emrx.monster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weakness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weakness_id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "weaknesses")
    private Set<Monster> monsters;

    public Weakness(String name) {
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