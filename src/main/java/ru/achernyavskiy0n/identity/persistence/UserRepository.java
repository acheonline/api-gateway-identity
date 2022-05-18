package ru.achernyavskiy0n.identity.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.achernyavskiy0n.identity.user.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameAndEmail(String username, String email);
    Optional<User> findByUsername(String username);
}
