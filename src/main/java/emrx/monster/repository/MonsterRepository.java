package emrx.monster.repository;

import emrx.monster.model.Monster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long>, PagingAndSortingRepository<Monster, Long> {

    Optional<Monster> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    List<Monster> findByType(String type);
    List<Monster> findByDangerLevelGreaterThanEqual(int level);
}
