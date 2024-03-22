package beggar.beggarzone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name="category_id")
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Board> boards = new ArrayList<>();

}
