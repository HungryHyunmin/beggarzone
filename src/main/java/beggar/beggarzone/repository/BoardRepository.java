package beggar.beggarzone.repository;

import beggar.beggarzone.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    Board findByTitle(String title);
    Board findByTitleAndContent(String title, String Content); //제목내용찾기
    List<Board> findByTitleLike(String title);
    Page<Board> findAll(Pageable pageable);




    Page<Board> findAll(Specification<Board> spec, Pageable pageable);

    Page<Board> findByCategoryId(Pageable pageable,Integer categoryId);

    @Query("select "
            + "distinct b "
            + "from Board b "
            + "left outer join SiteUser u1 on b.author=u1 "
            + "left outer join Reply r on r.board=b "
            + "left outer join SiteUser u2 on r.author=u2 "
            + "where "
            + "   b.title like %:kw% " //게시판 제목
            + "   or b.content like %:kw% " //게시판 내용
            + "   or u1.username like %:kw% " //게시판 작성자
            + "   or r.content like %:kw% " //댓글에 내용
            + "   or u2.username like %:kw% ") //댓글에 작성자
    Page<Board> findAllByKeyword(@Param("kw") String kw, Pageable pageable);

}
