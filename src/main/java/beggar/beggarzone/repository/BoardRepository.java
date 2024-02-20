package beggar.beggarzone.repository;

import beggar.beggarzone.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    Board findByTitle(String title);
    Board findByTitleAndContent(String title, String Content); //제목내용찾기
    List<Board> findByTitleLike(String title);
    Page<Board> findAll(Pageable pageable);

}
