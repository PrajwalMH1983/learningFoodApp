package com.learning.Utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;


public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	private final static String POSTFIX= "_table";
	
	
	public Identifier toPhysicalTableName(Identifier identifier,JdbcEnvironment context) {
		
		if(identifier == null) {
			return null;
		}
		final String newName = identifier.getText()+POSTFIX;
	
	return identifier.toIdentifier(newName);
	
	}
	
	public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment context) {
		
		if(identifier == null) {
			return null;
		}
		
		return Identifier.toIdentifier(identifier.getText().toLowerCase());
	}
}
