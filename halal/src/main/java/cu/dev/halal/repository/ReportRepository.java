package cu.dev.halal.repository;


import cu.dev.halal.entity.ReportEntity;
import cu.dev.halal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    // Content가 같은 값이 존재하는지 확인
    public boolean existsByContent(String Content);
    // user_id가 같은 값이 존재하는지 확인
    public boolean existsByUser(UserEntity userEntity);
}
