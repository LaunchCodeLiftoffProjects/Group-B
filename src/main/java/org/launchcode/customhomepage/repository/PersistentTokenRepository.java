package org.launchcode.customhomepage.repository;

import java.time.LocalDate;
import java.util.List;
import org.launchcode.customhomepage.domain.PersistentToken;
import org.launchcode.customhomepage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link PersistentToken} entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {
    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);
}
