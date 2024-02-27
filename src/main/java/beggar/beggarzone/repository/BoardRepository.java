package beggar.beggarzone.repository;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    Board findByTitle(String title);
    Board findByTitleAndContent(String title, String Content); //제목내용찾기
    List<Board> findByTitleLike(String title);
    Page<Board> findAll(Pageable pageable);

    Page<Board> findAll(Specification<Board> spec, Pageable pageable);

    Page<Board> findByCategoryId(Pageable pageable,Integer categoryId);


}
