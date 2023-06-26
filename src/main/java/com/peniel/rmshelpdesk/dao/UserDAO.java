package com.peniel.rmshelpdesk.dao;

import java.sql.SQLException;
import java.util.Map;

import com.peniel.rmshelpdesk.modals.UserCreation;

public interface UserDAO {
	public Map<String,Object> UserCreationDAO(UserCreation userCreation)throws SQLException;
}
