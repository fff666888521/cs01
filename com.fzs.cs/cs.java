public class BuggyCode {
    public static void main(String[] args) {
        // 1. 字符串比较的坑
        String str1 = new String("hello");
        String str2 = new String("hello");
        if (str1 == str2) {
            System.out.println("这两个字符串相等");
        } else {
            System.out.println("这两个字符串不相等");
        }

        // 2. 浮点数直接比较的坑
        double a = 0.1 + 0.2;
        double b = 0.3;
        if (a == b) {
            System.out.println("0.1 + 0.2 等于 0.3");
        } else {
            System.out.println("0.1 + 0.2 不等于 0.3");
        }

        // 3. 资源未关闭的坑
        try {
            java.io.FileReader reader = new java.io.FileReader("test.txt");
            reader.read();
            // 忘记关闭 reader，会导致资源泄漏
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. 数组越界的坑
        int[] arr = new int[5];
        for (int i = 1; i <= 5; i++) {
            arr[i] = i * 10;
        }
    }
}