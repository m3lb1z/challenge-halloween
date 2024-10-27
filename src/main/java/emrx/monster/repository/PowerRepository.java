package emrx.monster.repository;

import emrx.monster.model.Power;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PowerRepository extends JpaRepository<Power, Long> {
    Optional<Power> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    @Query("SELECT p FROM Power p JOIN p.monsters m WHERE m.id = :monsterId")
    Set<Power> findAllByMonsterId(@Param("monsterId") Long monsterId);
}
