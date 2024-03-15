package sample.Library_spr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.Library_spr.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Дополнительные методы могут быть добавлены здесь по мере необходимости
    User findByUsername(String username);

}

