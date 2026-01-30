package com.example.blogplatform.repository;

import com.example.blogplatform.model.dto.UserListDTO;
import com.example.blogplatform.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    @Query("""
            SELECT new com.example.blogplatform.model.dto.UserListDTO(
                    a.username, a.name, r)
                FROM AppUser a JOIN a.roles r
            """)
    List<UserListDTO> findAllUserList();

}
