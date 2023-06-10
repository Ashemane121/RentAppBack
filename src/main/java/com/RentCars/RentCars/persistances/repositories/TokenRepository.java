package com.RentCars.RentCars.persistances.repositories;

import java.util.List;
import java.util.Optional;

import com.RentCars.RentCars.persistances.entities.Token;
import com.RentCars.RentCars.persistances.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Long id);

  @Transactional
  void deleteAllByUser(User user);

  Optional<Token> findByToken(String token);
}
