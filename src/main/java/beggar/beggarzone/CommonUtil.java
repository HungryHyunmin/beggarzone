package beggar.beggarzone;

import beggar.beggarzone.domain.Board;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class CommonUtil {
    public String markdown(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }


   /* public static String convertDate(LocalDateTime dayBefore) { //시간 변환

        long gap = ChronoUnit.MINUTES.between(dayBefore, LocalDateTime.now());

        String word;
        if (gap == 0) {
            word = "방금 전";
        } else if (gap < 60) {
            word = gap + "분 전";
        } else if (gap < 60 * 24) {
            word = (gap / 60) + "시간 전";
        } else if (gap < 60 * 24 * 10) {
            word = (gap / 60 / 24) + "일 전";
        } else {
            word = dayBefore.format(DateTimeFormatter.ofPattern("MM월 dd일"));
        }
        return word;
    }*/



}
