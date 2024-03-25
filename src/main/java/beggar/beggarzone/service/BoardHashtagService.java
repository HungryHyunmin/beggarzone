package beggar.beggarzone.service;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.BoardHashtag;
import beggar.beggarzone.domain.Hashtag;
import beggar.beggarzone.repository.BoardHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardHashtagService {

    private final HashtagService hashtagService;
    private final BoardHashtagRepository boardHashtagRepository;
    public void saveHashtag(Board board, List<String> tagNames) {

        if(tagNames.size() == 0) return;

        tagNames.stream()
                .map(hashtag ->
                        hashtagService.findByTagName(hashtag)
                                .orElseGet(() -> hashtagService.save(hashtag)))
                .forEach(hashtag -> mapHashtagToBoard(board, hashtag));
    }
    private Long mapHashtagToBoard(Board board, Hashtag hashtag) {

        return boardHashtagRepository.save(new BoardHashtag(board, hashtag)).getId();
    }

    public List<BoardHashtag> findHashtagListByQuestion(Board board) {

        return boardHashtagRepository.findAllByBoard(board);
    }
}

