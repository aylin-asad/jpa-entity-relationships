package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "phone", length = 20)
    private String phone;

    // Departament silinəndə və ya işçi siyahıdan çıxanda Employee də silinsin.
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        if (employee == null) throw new IllegalArgumentException("Employee null ola bilməz");
        if (employee.getDepartment() == this) {
            if (!employees.contains(employee)) employees.add(employee);
            return;
        }
        if (employee.getDepartment() != null) employee.getDepartment().removeEmployee(employee);
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        if (employee != null && employees.remove(employee) && employee.getDepartment() == this) {
            employee.setDepartment(null);
        }
    }
}
