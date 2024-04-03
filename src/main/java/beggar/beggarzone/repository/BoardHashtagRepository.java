package beggar.beggarzone.repository;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.BoardHashtag;
import beggar.beggarzone.domain.Hashtag;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BoardHashtagRepository extends JpaRepository<BoardHashtag, Integer> {
    List<BoardHashtag> findAllByBoard(Board board);

    List<BoardHashtag> findAllByHashtagTagName(String hashtag);

    List<BoardHashtag> findAllByHashtag(Hashtag hashtag);


}
