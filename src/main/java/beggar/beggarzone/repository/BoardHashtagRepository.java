package beggar.beggarzone.repository;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.BoardHashtag;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardHashtagRepository extends JpaRepository<BoardHashtag, Integer> {
    List<BoardHashtag> findAllByBoard(Board board);
}
