package beggar.beggarzone.controller;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BoardForm {


    @NotEmpty(message="제목은 필수 입니다.")
    @Size(max=200)
    private String title;
    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;

    List<String> tagNames = new ArrayList<>();

    @Override
    public String toString() {
        return "BoardForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tagNames=" + tagNames +
                '}';
    }
}

