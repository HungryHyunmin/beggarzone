package beggar.beggarzone;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Category;
import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.repository.BoardRepository;
import beggar.beggarzone.repository.CategoryRepository;
import beggar.beggarzone.repository.ReplyRepository;
import beggar.beggarzone.repository.UserRepository;
import beggar.beggarzone.service.BoardService;
import org.hibernate.dialect.SelectItemReferenceStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
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
	UserRepository userRepository;
	@Autowired
	ReplyRepository replyRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	BoardService boardService;


	@Test
	void testJPA() {
		Board b1 = new Board();
		b1.setTitle("총 지출 7000원");
		b1.setContent("김밥4000+라면3000");
		b1.setRegDate(LocalDateTime.now());
		this.boardRepository.save(b1);

		Board b2 = new Board();
		b2.setTitle("절약왕");
		b2.setContent("저번달 대비 3만원 절약했습니다.");
		b2.setRegDate(LocalDateTime.now());
		this.boardRepository.save(b2);
	}


	@Test
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
	}

	@Test
	void 제목찾기() {
		Board b = this.boardRepository.findByTitle("테스트 제목");
		assertEquals(1, b.getId());
	}

	@Test
	void 제목내용찾기() {
		Board b = this.boardRepository.findByTitleAndContent("테스트 제목", "테스트 내용");
		assertEquals(1, b.getId());
	}

	@Test
	void 제목찾기like() {
		List<Board> blist = this.boardRepository.findByTitleLike("%제목%");

		Board b = blist.get(0);
		assertEquals("테스트 제목", b.getTitle());
	}

	@Test
	void 게시판수정() {
		Optional<Board> ob = this.boardRepository.findById(1);
		assertTrue(ob.isPresent());
		Board b = ob.get();
		b.setTitle("수정된 제목");
		this.boardRepository.save(b);
	}

	@Test
	void 게시판삭제() {
		assertEquals(2, this.boardRepository.count());
		Optional<Board> ob = this.boardRepository.findById(1);
		assertTrue(ob.isPresent());
		Board b = ob.get();
		this.boardRepository.delete(b);
		assertEquals(1, this.boardRepository.count());
	}

	@Test
	void 게시판넣기() {
		Optional<Category> oc = this.categoryRepository.findById(1);
		assertTrue(oc.isPresent());
		Category c = oc.get();

		Board b = new Board();
		b.setCategory(c);
		b.setTitle("카테고리 테스트");
		b.setContent("카테고리 내용");
		b.setRegDate(LocalDateTime.now());
		b.setModifyDate(LocalDateTime.now());
		this.boardRepository.save(b);
	}

	@Test
	void 답변저장() {
		Optional<Board> ob = this.boardRepository.findById(2);
		assertTrue(ob.isPresent());
		Board b = ob.get();

		Reply r = new Reply();
		r.setContent("많이 안쓰셨네요");
		r.setBoard(b); // 게시글 객체가 필요로 한다.
		r.setRegDate(LocalDateTime.now());
		this.replyRepository.save(r);
	}

	@Test
	void 댓글조회() {
		Optional<Reply> or = this.replyRepository.findById(1);
		assertTrue(or.isPresent());
		Reply r = or.get();
		assertEquals(2, r.getBoard().getId());
	}

	@Transactional
	@Test
	void 댓글리스트조회() {
		Optional<Board> ob = this.boardRepository.findById(2);
		assertTrue(ob.isPresent());
		Board b = ob.get();

		List<Reply> replyList = b.getReplyList();

		assertEquals(1, replyList.size());
		assertEquals("많이 안쓰셨네요", replyList.get(0).getContent());
	}

	@Test
	void 페이징데이터생성() {

		SiteUser u = new SiteUser();
		u.setUsername("길동님7");
		u.setEmail("gusals5123@gmail.com");
		u.setPassword("1234231234");
		this.userRepository.save(u);


		for (int i = 0; i <= 300; i++) {

			String title = String.format("소비 내역 : [%03d일차]", i);
			String content = "내용 없음";
			this.boardService.create(title, content, u, null);
		}
	}

	@Test
	void 게시글전체삭제() {
		List<Board> list = this.boardRepository.findAll();
		for (int i = 0; i < list.size(); i++) {
			Board board = list.get(i);
			this.boardRepository.delete(board);
		}

	}
}