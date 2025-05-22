package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Dao {
    // DataSourceは使わないなら削除してOK
    static DataSource ds;

    protected Connection getConnection() throws Exception {
    	if(ds==null){
	    	InitialContext ic=new InitialContext();
			ds=(DataSource)ic.lookup("java:/comp/env/jdbc/teamE");
    	}
    	return ds.getConnection();
    }
}
