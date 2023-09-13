package cu.dev.halal.repository;

import cu.dev.halal.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
}
