# JPA Entity Əlaqələri — Tapşırıqlar 1–4

**Java 21, Spring Boot 3.5.6, Gradle, Jakarta Persistence, Lombok, H2 (file-based)**.

## Başlatmaq

1. Java 21 və Gradle 8.x quraşdırın. IntelliJ IDEA-da layihənin `build.gradle` faylını açın.
2. Terminal: `gradle bootRun` (və ya Gradle panelində `bootRun`).
3. Konsolda Hibernate `create table` sətirlərini izləyin.
4. Brauzerdə http://localhost:8080/h2-console açın.
5. JDBC URL: `jdbc:h2:file:./data/jpa_task_db;DB_CLOSE_ON_EXIT=FALSE`, username `sa`, password boş.
6. SQL sorğusu: `SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' ORDER BY TABLE_NAME;`
7. Screenshot üçün H2 Console-da cədvəlləri və sütunları açıb görüntü götürün.

**Diqqət:** `spring.jpa.hibernate.ddl-auto=create` hər startda sxemi yenidən yaradır. Tapşırıq üçün tələb olunur, real mühitdə istifadə etməyin.

H2 Console proqram işə salındıqda mövcuddur. Yoxlama üçün:

```sql
SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'PUBLIC' ORDER BY TABLE_NAME;

SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='PUBLIC'
ORDER BY TABLE_NAME, ORDINAL_POSITION;

SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, TABLE_NAME
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA='PUBLIC'
ORDER BY TABLE_NAME, CONSTRAINT_NAME;
```

**Gözlənilən 10 cədvəl:** users, passports, departments, employees, students, courses, student_course, authors, books, author_book.

Qeyd: `Department.removeEmployee()` `orphanRemoval` üçün işçini kolleksiyadan çıxarır və Java istinadını null edir; sonra entity üzərində commit/flush baş verdikdə row silinir. `Passport.user` sütunu `NOT NULL` olduğundan `setPassport(null)` etdikdən sonra User-in idarə olunan entity olması və flush lazımdır.

H2 faylı `data/` qovluğunda yaradılır; təhvil zamanı kod və real ekrandan alınmış screenshot verin.
