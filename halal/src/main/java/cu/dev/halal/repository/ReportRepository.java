package cu.dev.halal.repository;


import cu.dev.halal.entity.ReportEntity;
import cu.dev.halal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    public boolean existsByContent(String Content);
    public boolean existsByUser(UserEntity userEntity);
}
