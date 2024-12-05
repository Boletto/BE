package com.demoboletto.repository;

import com.demoboletto.domain.User;
import com.demoboletto.type.EProvider;
import com.demoboletto.type.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    Optional<User> findUserById(Long id);

    Optional<User> findBySerialId(String serialId);

    Optional<User> findByProviderAndSerialId(EProvider provider, String serialId);

    Optional<User> findUserByRefreshToken(String refreshToken);

    @Query("select u.id as id, u.role as role, u.password as password from User u where u.serialId = :serialId")
    Optional<UserSecurityForm> findSecurityFormBySerialId(String serialId);

    //TODO: Refactor this query
    @Query("select u.id as id, u.role as role, u.password as password from User u where u.id = :id")
    Optional<UserSecurityForm> findSecurityFormById(Long id);


    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.provider = :provider AND u.serialId = :providerId")
    boolean existsByProviderAndProviderId(@Param("provider") EProvider provider, @Param("providerId") String providerId);

    interface UserSecurityForm {
        static UserSecurityForm invoke(User user) {
            return new UserSecurityForm() {
                @Override
                public Long getId() {
                    return user.getId();
                }

                @Override
                public ERole getRole() {
                    return user.getRole();
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }
            };
        }

        Long getId();

        ERole getRole();

        String getPassword();
    }
}

