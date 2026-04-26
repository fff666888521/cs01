import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class ProblematicExample {

    // 问题1：硬编码敏感信息
    private static final String DB_PASSWORD = "MySecret123";

    public void processUserData(String username) {
        // 问题2：使用不安全的反序列化
        try {
            FileInputStream fis = new FileInputStream("data.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object data = ois.readObject();  // 不安全的反序列化
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 问题3：SQL注入风险
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", DB_PASSWORD);
            Statement stmt = conn.createStatement();

            // ❌ SQL注入漏洞
            String query = "SELECT * FROM users WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);

            // 问题4：资源未正确关闭
            // rs.close() 和 stmt.close() 应该在finally块中

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 问题5：finally块中可能仍有异常
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // 问题6：不恰当的异常处理
                    e.printStackTrace();
                }
            }
        }
    }

    // 问题7：命令注入风险
    public void executeSystemCommand(String input) {
        try {
            // ❌ 命令注入风险
            Runtime.getRuntime().exec("ping " + input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 问题8：不安全的比较
    public boolean checkPassword(String input) {
        // ❌ 字符串比较可能被时序攻击
        return input.equals(DB_PASSWORD);
    }
}