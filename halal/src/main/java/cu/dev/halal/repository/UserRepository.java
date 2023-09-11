package cu.dev.halal.repository;

import cu.dev.halal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity getByEmail(String email);
    public boolean existsByEmail(String email);
}
