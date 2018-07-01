package com.netcracker.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netcracker.jwt.domain.User;

/**
 * @author Sarath Muraleedharan
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	public User findOneByUsername(String username);
}
