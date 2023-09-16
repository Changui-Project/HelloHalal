package cu.dev.halal.repository;

import cu.dev.halal.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    public boolean existsByNameAndAddress(String name, String address);
    public List<StoreEntity> getByCoordinateXBetweenAndCoordinateYBetween(Double minX, Double maxX, Double minY,  Double maxY);

}
