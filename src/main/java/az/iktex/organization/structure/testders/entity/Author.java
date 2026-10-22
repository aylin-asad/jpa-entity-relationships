package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "country", length = 50)
    private String country;

    // Müəllif silinərsə yalnız onun AuthorBook əlaqələri silinsin; Book saxlanılsın.
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthorBook> authorBooks = new ArrayList<>();

    public void addAuthorBook(AuthorBook link) {
        if (link.getAuthor() != null && link.getAuthor() != this) link.getAuthor().removeAuthorBook(link);
        if (!authorBooks.contains(link)) authorBooks.add(link);
        link.setAuthor(this);
    }
    public void removeAuthorBook(AuthorBook link) {
        if (authorBooks.remove(link) && link.getAuthor() == this) link.setAuthor(null);
    }
}
