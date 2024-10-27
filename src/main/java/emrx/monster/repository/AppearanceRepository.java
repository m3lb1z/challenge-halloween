package emrx.monster.repository;

import emrx.monster.model.Appearance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance, Long> {
    Optional<Appearance> findByIdAndMonsterId(Long id, Long monsterId);
    List<Appearance> findAllByMonsterId(Long monsterId);

    void deleteByIdAndMonsterId(Long id, Long monsterId);
}
