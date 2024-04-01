package beggar.beggarzone.repository;

import beggar.beggarzone.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    Board findByTitle(String title);

    Board findByTitleAndContent(String title, String Content); //제목내용찾기

    List<Board> findByTitleLike(String title);

    Page<Board> findAll(Pageable pageable);


    Page<Board> findAll(Specification<Board> spec, Pageable pageable);



   /* @Query("select distinct b from Board b" +
            " join fetch b.author a" +
            " join fetch b.replyList r"+
            " join fetch b.boardHashtags bh "+
            " join fetch bh.hashtag h "
           )
    Optional<Board> findById2(Long id);*/
}



