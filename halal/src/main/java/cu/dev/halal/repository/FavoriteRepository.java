package cu.dev.halal.repository;


import cu.dev.halal.entity.FavoriteEntity;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    public Boolean existsByUserAndStore(UserEntity userEntity, StoreEntity storeEntity);

    public void deleteByUserAndStore(UserEntity userEntity, StoreEntity storeEntity);
    public List<FavoriteEntity> getByUser(UserEntity userEntity);
}
