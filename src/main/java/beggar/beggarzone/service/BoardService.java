package beggar.beggarzone.service;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.exception.DataNotFoundException;
import beggar.beggarzone.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;



    public List<Board> getList(){
        return this.boardRepository.findAll();
    }



    public Board getBoard(Integer id){
        Optional<Board> board = this.boardRepository.findById(id);
        if(board.isPresent()){
            return board.get();
        }else {
            throw new DataNotFoundException("data not found");
        }

    }
}
