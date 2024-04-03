package beggar.beggarzone.repository;

import beggar.beggarzone.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardQueryRepository  extends JpaRepository<Board,Integer> {
    @Query("select b from Board b "+
            "join fetch b.author a")
    Page<Board> findAllWithAuthor(Pageable pageable);

    @Query("select "
            + "distinct b "
            + "from Board b "
            + "where "
            + "   b.title like %:kw% " //게시판 제목
            + "   or b.content like %:kw% " //게시판 내용
    )
        //댓글에 작성자
    Page<Board> findByKeywordAndContentAndTitle(@Param("kw") String kw, Pageable pageable);// 내용+제목

    @Query("select "
            + "distinct b "
            + " from Board b "
            + " left outer join SiteUser u1 on b.author=u1 "
            + " where "
            + " u1.username like %:kw% "
    )
    Page<Board> findByKeywordAndAuthor(@Param("kw") String kw, Pageable pageable); //작성자


}
