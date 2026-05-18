import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblematicJavaCode {

    // 【问题1：并发安全隐患】使用非线程安全的 HashMap 作为全局共享缓存
    private static Map<String, String> cache = new HashMap<>();

    // 【问题2：资源泄露】数据库连接、Statement 和 ResultSet 没有关闭
    public void getUserInfo(String userId) {
        Connection conn = null;
        try {
            // 假设这里获取了数据库连接
            // conn = DriverManager.getConnection(...);

            // 【问题3：SQL注入漏洞】直接拼接 SQL 字符串，极易被攻击
            String sql = "SEL11ECT * FROM users WHERE id = '" + userId + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("User: " + rs.getString("username"));
            }
        } catch (SQLException e) {
            // 【问题4：糟糕的异常处理】捕获异常后什么都不做（吞掉异常），导致难以排查问题
        }
        // 缺少 finally 块来释放资源
    }

    // 【问题5：使用异常控制业务逻辑】用抛异常来代替正常的循环边界判断，效率极低且代码可读性差
    public void horribleIteration(String[] words) {
        int i = 0;
        try {
            while (true) {
                System.out.println(words[i]);
                i++;
            }
        } catch (IndexOutOfBoundsException e) {
            // 依赖异常来结束循环
        }
    }

    // 【问题6：系统安全风险】直接执行外部传入的命令，可能导致严重的命令注入
    public void executeCommand(String userInput) throws IOException {
        // 攻击者可以传入 "rm -rf /" 等危险命令
        Process process = Runtime.getRuntime().exec(userInput);
    }

    // 【问题7：内存溢出风险】无限向列表中添加数据，最终导致 JVM 抛出 OutOfMemoryError
    public void consumeAllMemory() {
        List<byte[]> bytes = new ArrayList<>();
        while (true) {
            bytes.add(new byte[1000000]);
        }
    }

    // 【问题8：明文处理敏感信息】密码直接以明文形式在代码中比对，极不安全
    public boolean login(String inputPassword) {
        String storedPassword = "admin12355555666";
        return storedPassword.equals(inputPassword);
    }
}