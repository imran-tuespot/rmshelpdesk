package com.peniel.rmshelpdesk.repository;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.peniel.rmshelpdesk.entity.Roles;
import com.peniel.rmshelpdesk.entity.User;
public interface UserRepository extends JpaRepository<User, Long> {

@Query(value="select * from app_user where username=:username ", nativeQuery=true)
public	com.peniel.rmshelpdesk.entity.User getUser(@Param("username") String username);
@Transactional
@Modifying
@Query(value="update app_user set status=:status where user_id=:user_id ", nativeQuery=true)
public void updatePasswordStatusById(Long user_id, String status);
@Transactional
@Modifying
@Query(value="update app_user set password=:code,PASSWORD_STATUS= 'A', status = 'A' where username=:username ", nativeQuery=true)
public void  resetCode(@Param("username") String username,@Param("code") String code);

@Query(value="SELECT * " + 
		" FROM app_user where user_id=:userid ", nativeQuery=true)
public	com.peniel.rmshelpdesk.entity.User myProfile(@Param("userid") long userid);

}
