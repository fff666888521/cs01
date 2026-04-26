// ✅ 安全方法：使用 PreparedStatement 防御 SQL 注入
public void getUserByNameSafe(Connection conn, String userName) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        // 使用 ? 作为占位符
        String sql = "SELECT * FROM t_user WHERE username = ?";
        pstmt = conn.prepareStatement(sql);
        // 将参数安全地填入占位符，数据库会把它纯当作字符串处理，不再解析为指令
        pstmt.setString(1, userName);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            System.out.println("查询到用户: " + rs.getString("username"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // 省略关闭资源的代码
    }
}