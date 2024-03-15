package sample.Library_spr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.Library_spr.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Дополнительные методы могут быть добавлены здесь по мере необходимости

}
