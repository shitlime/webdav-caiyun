package com.vgearen.webdavcaiyundrive.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    /**
     * 计算文件的 SHA-256 哈希值
     * @param file 目标文件
     * @return 十六进制格式的哈希字符串
     */
    public static String calculateSHA256(File file) throws Exception {
        InputStream inputStream = Files.newInputStream(file.toPath());
        String sha256 = calculateSHA256(inputStream);
        inputStream.close();
        return sha256;
    }

    /**
     * 计算文件的 SHA-256 哈希值
     * @param file
     * @return
     */
    public static String calculateSHA256(InputStream file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[8192 * 1024]; // 8MB 缓冲区（可调整）
        int bytesRead;
        while ((bytesRead = file.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead); // 分块更新哈希
        }
        byte[] hashBytes = digest.digest();
        return bytesToHex(hashBytes);
    }

    /**
     * 将字节数组转换为十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0'); // 补零
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
