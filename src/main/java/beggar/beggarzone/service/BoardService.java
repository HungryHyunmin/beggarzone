package beggar.beggarzone.service;

import beggar.beggarzone.domain.Board;
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
public class BoardService {

    private final BoardRepository boardRepository;



    public Page<Board> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return this.boardRepository.findAll(pageable);
    }



    public Board getBoard(Integer id){
        Optional<Board> board = this.boardRepository.findById(id);
        if(board.isPresent()){
            return board.get();
        }else {
            throw new DataNotFoundException("data not found");
        }

    }
    public void create(String title, String content){ //게시글 등록
        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setRegDate(LocalDateTime.now());
        this.boardRepository.save(b);
    }
}
