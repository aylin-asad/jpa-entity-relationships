package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JoinTable ilə müqayisədə üstünlük: əlaqəyə role, addedAt kimi sahələr əlavə oluna bilər.
 * Çatışmazlıq: ayrıca entity, repository və iki istiqamətdə əlaqə idarəçiliyi tələb edir.
 */
@Entity
@Table(name = "author_book", uniqueConstraints =
    @UniqueConstraint(name = "uk_author_book", columnNames = {"author_id", "book_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthorBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Cascade yoxdur: AuthorBook silinməsi müəllifi silməməlidir.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_ab_author"))
    private Author author;

    // Cascade yoxdur: AuthorBook silinməsi kitabı silməməlidir.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_ab_book"))
    private Book book;
}
