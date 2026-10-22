package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "age", nullable = false)
    private Integer age;

    // Yalnız yeni/yenilənmiş Course paylaşılır; REMOVE yoxdur ki, Student silinəndə Course qalsın.
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "student_course",
        joinColumns = @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_sc_student")),
        inverseJoinColumns = @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_sc_course")),
        uniqueConstraints = @UniqueConstraint(name = "uk_student_course", columnNames = {"student_id", "course_id"}))
    private Set<Course> courses = new HashSet<>();

    public void addCourse(Course course) {
        if (course == null) throw new IllegalArgumentException("Course null ola bilməz");
        courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course) {
        if (course != null) {
            courses.remove(course);
            course.getStudents().remove(this);
        }
    }
}
