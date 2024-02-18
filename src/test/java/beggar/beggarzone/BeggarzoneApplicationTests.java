package beggar.beggarzone;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.repository.BoardRepository;
import beggar.beggarzone.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BeggarzoneApplicationTests {

	@Autowired
	BoardRepository boardRepository;
	@Autowired
	ReplyRepository replyRepository;

	@Test
	void testJPA() {
		Board b1 = new Board();
		b1.setTitle("테스트 제목");
		b1.setContent("테스트 내용");
		b1.setRegDate(LocalDateTime.now());
		this.boardRepository.save(b1);

		Board b2 = new Board();
		b2.setTitle("제목22");
		b2.setContent("내용22");
		b2.setRegDate(LocalDateTime.now());
		this.boardRepository.save(b2);
	}

	/*@Test
	void 보드찾기테스트() {
		List<Board> all = this.boardRepository.findAll();
		assertEquals(2, all.size());

		Board b = all.get(0);
		assertEquals("테스트 제목", b.getTitle());
	}


	@Test
	void 아이디찾기() {
		Optional<Board> ob = this.boardRepository.findById(1); //Optional 타입은 1이 존재하지 않을 수도 있기때문에 null을 유연하게 처리하는 클래스
		if (ob.isPresent()) {
			Board b = ob.get();
			assertEquals("테스트 내용", b.getContent());
		}
	}*/

	/*@Test
	void 제목찾기(){
		Board b = this.boardRepository.findByTitle("테스트 제목");
		assertEquals(1,b.getId());
	}*/

/*	@Test
	void 제목내용찾기(){
		Board b = this.boardRepository.findByTitleAndContent("테스트 제목","테스트 내용");
		assertEquals(1,b.getId());
	}*/

	/*@Test
	void 제목찾기like(){
		List<Board> blist= this.boardRepository.findByTitleLike("%제목%");

		Board b = blist.get(0);
		assertEquals("테스트 제목",b.getTitle());
	}*/
	/*@Test
	void 게시판수정(){
		Optional<Board> ob = this.boardRepository.findById(1);
		assertTrue(ob.isPresent());
		Board b = ob.get();
		b.setTitle("수정된 제목");
		this.boardRepository.save(b);
	}*/

	/*@Test
	void 게시판삭제(){
		assertEquals(2, this.boardRepository.count());
		Optional<Board> ob= this.boardRepository.findById(1);
		assertTrue(ob.isPresent());
		Board b = ob.get();
		this.boardRepository.delete(b);
		assertEquals(1, this.boardRepository.count());
	}*/

	@Test
	void 답변저장(){
		Optional<Board> ob = this.boardRepository.findById(2);
		assertTrue(ob.isPresent());
		Board b = ob.get();

		Reply r = new Reply();
		r.setContent("네 자동으로 생성됩니다.");
		r.setBoard(b); // 게시글 객체가 필요로 한다.
		r.setRegDate(LocalDateTime.now());
		this.replyRepository.save(r);
	}

	@Test
	void 댓글조회(){
		Optional<Reply> or = this.replyRepository.findById(1);
		assertTrue(or.isPresent());
		Reply r = or.get();
		assertEquals(2,r.getBoard().getId());
	}

	@Transactional
	@Test
	void 댓글리스트조회(){
		Optional<Board> ob = this.boardRepository.findById(2);
		assertTrue(ob.isPresent());
		Board b = ob.get();

		List<Reply> replyList = b.getReplyList();

		assertEquals(1,replyList.size());
		assertEquals("네 자동으로 생성됩니다.", replyList.get(0).getContent());
	}

}
