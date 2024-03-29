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
    public Long create(SiteUser user, BoardForm boardForm) { //게시글 등록
        Board b = new Board();
        b.setTitle(boardForm.getTitle());
        b.setContent(boardForm.getContent());
        b.setRegDate(LocalDateTime.now());
        b.setAuthor(user);
        //b.setCategory(categoryName);
        Board savedBoard = boardRepository.save(b);
        boardHashtagService.saveHashtag(savedBoard,boardForm.getTagNames());
        return savedBoard.getId();
    }




    @Transactional
    public void modify(Board board, BoardForm boardForm) {
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
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