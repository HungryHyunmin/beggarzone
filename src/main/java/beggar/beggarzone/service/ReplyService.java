package beggar.beggarzone.service;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;




    public void create(Board board,String content){
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setRegDate(LocalDateTime.now());
        reply.setBoard(board);
        this.replyRepository.save(reply);
    }
}
