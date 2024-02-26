package beggar.beggarzone.controller;

import beggar.beggarzone.domain.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BoardForm {

    private Category category;

    @NotEmpty(message="제목은 필수 입니다.")
    @Size(max=200)
    private String title;
    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;

    @Override
    public String toString() {
        return "BoardForm{" +
                "category=" + category +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
