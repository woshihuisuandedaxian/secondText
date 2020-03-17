package springboot_system.password;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author ZouXianTao
 * @Date2020/1/10
 */
public class bytePassword {
    public static void main(String[] args) {
// 1.加密算法
        String algorithmName = "MD5";

        // 2.密码
        Object source = "jxciaroot";

        // 3.盐值
        Object salt = ByteSource.Util.bytes("root"); // 盐值一般是用户名或者userId

        // 4.加密次数
        int hashIterations = 1024;

        SimpleHash simpleHash = new SimpleHash(algorithmName, source, salt, hashIterations);
        // 5.得到加密后的密码
        System.out.println(simpleHash);
    }
}
