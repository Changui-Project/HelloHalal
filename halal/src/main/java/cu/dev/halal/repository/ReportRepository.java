package cu.dev.halal.repository;


import cu.dev.halal.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    public Collection<ReportEntity> getByEmail(String email);
    public boolean existsByContent(String Content);
}
