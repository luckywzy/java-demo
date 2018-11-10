package demo.com.helper;

import com.mysql.jdbc.Connection;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

/**
 * 数据库操作
 */
@Slf4j
public class DataBaseHelper {

    /**
     * 使用threadlocal 存放 数据库链接
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    static {
        CONNECTION_HOLDER = new ThreadLocal<>();
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                log.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }


    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                log.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }

        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                log.error("rollback transaction failure!", e);
                throw new RuntimeException(e);

            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    private static Connection getConnection() {
        return null;
    }
}
