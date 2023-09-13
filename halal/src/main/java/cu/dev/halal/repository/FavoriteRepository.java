package cu.dev.halal.repository;


import cu.dev.halal.entity.FavoriteEntity;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    // User테이블과 Store테이블에 해당 값이 존재하는지 확인한다.
    public Boolean existsByUserAndStore(UserEntity userEntity, StoreEntity storeEntity);
    // User와 Store값을 기준으로 값을 삭제한다.
    public void deleteByUserAndStore(UserEntity userEntity, StoreEntity storeEntity);
    public List<FavoriteEntity> getByUser(UserEntity userEntity);
}
