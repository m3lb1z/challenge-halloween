package emrx.monster.repository;

import emrx.monster.model.Weakness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface WeaknessRepository extends JpaRepository<Weakness, Long> {
    Optional<Weakness> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    @Query("SELECT w FROM Weakness w JOIN w.monsters m WHERE m.id = :monsterId")
    Set<Weakness> findAllByMonsterId(@Param("monsterId") Long monsterId);
}
