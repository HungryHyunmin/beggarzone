package beggar.beggarzone.service;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.BoardHashtag;
import beggar.beggarzone.domain.Hashtag;
import beggar.beggarzone.repository.BoardHashtagRepository;
import beggar.beggarzone.repository.BoardHashtagQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardHashtagService {

    private final HashtagService hashtagService;
    private final BoardHashtagRepository boardHashtagRepository;
    private final BoardHashtagQueryRepository boardHashtagQueryRepository;
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

    public List<BoardHashtag> findHashtagListByBoard(Board board) {

        return boardHashtagRepository.findAllByBoard(board);
    }

    public Page<Board> findAllByHashtag(int page, String hashtag) {
        List<Sort.Order> sortsList = new ArrayList<>();
        sortsList.add(Sort.Order.desc("regDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortsList));
        List<Board> boardList = boardHashtagRepository.findAllByHashtagTagName(hashtag)
                .stream()
                .map(BoardHashtag::getBoard)
                .toList();

        return new PageImpl<>(boardList, pageable, boardList.size());
    }

    /*public List<BoardHashtagQueryDto> findHashtagList(){
        return boardHashtagQueryRepository.findHashtags();
    }*/

}

