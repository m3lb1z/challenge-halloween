package emrx.monster.repository;

import emrx.monster.model.Monster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {
    boolean existsByNameIgnoreCase(String name);
    List<Monster> findByType(String type);
    List<Monster> findByDangerLevelGreaterThanEqual(int level);
}
