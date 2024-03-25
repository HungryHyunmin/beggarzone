package beggar.beggarzone.service;

import beggar.beggarzone.controller.BoardForm;
import beggar.beggarzone.domain.*;
import beggar.beggarzone.exception.DataNotFoundException;
import beggar.beggarzone.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardHashtagService boardHashtagService;


    /*private Specification<Board> search(String kw) { //Sepecifation을 활용한 검색방법
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Board, SiteUser> u1 = b.join("author", JoinType.LEFT);
                Join<Board, Reply> r = b.join("replyList", JoinType.LEFT);
                Join<Reply, SiteUser> u2 = r.join("author", JoinType.LEFT);
                return cb.or(cb.like(b.get("title"), "%" + kw + "%"), // 제목
                        cb.like(b.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(r.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }*/


    public Page<Board> getList(int page, String kw, String type) { //page:페이지  ,검색어
        List<Sort.Order> sorts = new ArrayList<>();
            sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        /*Specification<Board> spec = search(kw); //검색방법 1.Specification
        return this.boardRepository.findAll(spec, pageable);*/ //게시물 10개 리턴
       if(type.equals("ct"))
           return this.boardRepository.findByKeywordAndContentAndTitle(kw, pageable);
       else if (type.equals("author")){
           return this.boardRepository.findByKeywordAndAuthor(kw,pageable);
       }else{
           return this.boardRepository.findAll(pageable);
       }
    }

    /*public Page<Board> getOrderList(int page, String order) {
        List<Sort.Order> sorts = new ArrayList<>();
        if(order.equals("date"))
            sorts.add(Sort.Order.desc("regDate"));//등록일 순 정렬
        else{
            sorts.add(Sort.Order.desc("author.size()"));
        }
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.boardRepository.findAll(pageable);
    }*/


    public List<Board> getAllList(){
        return this.boardRepository.findAll();
    }


   /* public Page<Board> getCategoryList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return this.boardRepository.findByCategoryId(pageable);
    }*/


    public Board getBoard(Integer id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("data not found");
        }

    }

    @Transactional
    public Long create(String title, String content, SiteUser user, BoardForm boardForm) { //게시글 등록
        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setRegDate(LocalDateTime.now());
        b.setAuthor(user);
        //b.setCategory(categoryName);
        Board savedBoard = boardRepository.save(b);
        boardHashtagService.saveHashtag(savedBoard,boardForm.getTagNames());
        return savedBoard.getId();
    }

    @Transactional
    public void modify(Board board, String title, String content) {
        board.setTitle(title);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());
        
        this.boardRepository.save(board);
    }

    @Transactional
    public void delete(Board board) {
        this.boardRepository.delete(board);
    }

    @Transactional
    public void vote(Board board, SiteUser siteUser) { // 좋아요 누른 사람 이름 저장
        board.getVoter().add(siteUser);
        this.boardRepository.save(board);
    }



}