package beggar.beggarzone.service;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Category;
import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.exception.DataNotFoundException;
import beggar.beggarzone.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    private Specification<Board> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Board> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Board, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Board, Reply> a = q.join("replyList", JoinType.LEFT);
                Join<Reply, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("title"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }


    public Page<Board> getList(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        Specification<Board> spec = search(kw);
        return this.boardRepository.findAll(spec, pageable);
    }



    public Board getBoard(Integer id){
        Optional<Board> board = this.boardRepository.findById(id);
        if(board.isPresent()){
            return board.get();
        }else {
            throw new DataNotFoundException("data not found");
        }

    }
    public void create(String title, String content, SiteUser user,Category category){ //게시글 등록
        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setRegDate(LocalDateTime.now());
        b.setAuthor(user);
        b.setCategory(category);
        //b.setCategory(categoryName);
        this.boardRepository.save(b);
    }

    public void modify(Board board, String title , String content){
        board.setTitle(title);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());
        this.boardRepository.save(board);
    }

    public void delete(Board board){
        this.boardRepository.delete(board);
    }

    public void vote(Board board, SiteUser siteUser) { // 좋아요 누른 사람 이름 저장
        board.getVoter().add(siteUser);
        this.boardRepository.save(board);
    }
}
