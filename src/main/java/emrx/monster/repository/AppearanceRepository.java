package emrx.monster.repository;

import emrx.monster.model.Appearance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance, Long> {
    List<Appearance> findAllByMonsterId(Long monsterId);
}
