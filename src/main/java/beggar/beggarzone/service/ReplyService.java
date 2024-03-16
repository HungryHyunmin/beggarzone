package beggar.beggarzone.service;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.exception.DataNotFoundException;
import beggar.beggarzone.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Reply create(Board board, String content, SiteUser author){ //답글 생성
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setRegDate(LocalDateTime.now());
        reply.setBoard(board);
        reply.setAuthor(author);
        this.replyRepository.save(reply);
        return reply;
    }

    public Reply getReply(Integer id){
        Optional<Reply> reply = this.replyRepository.findById(id);
        if(reply.isPresent()){
            return reply.get();
        }else{
            throw new DataNotFoundException("answer not found");
        }
    }
    @Transactional
    public void Modify(Reply reply,String content){
        reply.setContent(content);
        reply.setModifyDate(LocalDateTime.now());
        this.replyRepository.save(reply);
    }

    @Transactional
    public void delete(Reply reply) {
        this.replyRepository.delete(reply);
    }

    public void vote(Reply reply, SiteUser siteUser) {
        reply.getVoter().add(siteUser);
        this.replyRepository.save(reply);
    }
}
