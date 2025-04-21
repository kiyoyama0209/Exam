package dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

public class Dao {
    // DataSourceは使わないなら削除してOK
    static DataSource ds;

    protected Connection getConnection() throws Exception {
        // ★H2DB接続設定例:
        // 必要に応じてパスやID/PWを修正
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/teamE", "sa", "");
    }
}
