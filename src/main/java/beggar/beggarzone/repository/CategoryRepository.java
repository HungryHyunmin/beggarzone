package beggar.beggarzone.repository;

import beggar.beggarzone.domain.Category;
import beggar.beggarzone.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
