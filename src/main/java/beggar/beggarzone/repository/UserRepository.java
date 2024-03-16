package beggar.beggarzone.repository;

import beggar.beggarzone.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser,Long> { //siteuser의 기본키는 long


    Optional<SiteUser> findByUsername(String username);


}
