package com.peniel.rmshelpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.peniel.rmshelpdesk.entity.Roles;

public interface UserRolesRepository extends JpaRepository<Roles, String> {

@Query(value="select * from app_user_roles aur,app_roles ar where ar.role_id=aur.role_id"
		+ " and aur.user_id=:userid ", nativeQuery=true)
public	Roles getUserRoles(@Param("userid") long userid);
@Query(value="select * from app_roles  ", nativeQuery=true)
		//+ " where COMPANY_ID=:company ")
public List<Roles> getRoles(Long company);

}
