package com.vgearen.webdavcaiyundrive.store;

import com.vgearen.webdavcaiyundrive.config.CaiyunProperties;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CachingInputStreamWrapper {
    private final CaiyunProperties caiyunProperties;
    private final InputStream originalInput;
    private ByteArrayOutputStream memoryBuffer; // 内存缓存（小文件）
    private Path tempFile;        // 临时文件（大文件）
    private boolean isCachedToDisk = false;

    public CachingInputStreamWrapper(InputStream input, CaiyunProperties caiyunProperties) {
        this.originalInput = input;
        this.caiyunProperties = caiyunProperties;
    }

    /**
     * 读取数据并缓存，同时计算 SHA256
     * @return SHA256 哈希值
     */
    public String cacheAndCalculateHash() throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        OutputStream cacheOutput;

        // 初始化缓存
        if (memoryBuffer == null && tempFile == null) {
            memoryBuffer = new ByteArrayOutputStream();
            cacheOutput = memoryBuffer;
        } else {
            throw new IllegalStateException("数据已缓存，不可重复调用");
        }

        // 读取并缓存数据
        byte[] buffer = new byte[8192 * 1024];
        int bytesRead;
        long totalRead = 0;
        while ((bytesRead = originalInput.read(buffer)) != -1) {
            // 更新哈希
            digest.update(buffer, 0, bytesRead);
            // 写入缓存
            cacheOutput.write(buffer, 0, bytesRead);
            totalRead += bytesRead;

            // 超过内存阈值时切换到临时文件
            if (totalRead > caiyunProperties.getCacheMemoryLimit() && !isCachedToDisk) {
                // 创建临时文件
                tempFile = Files.createTempFile("caiyundrive-upload-cache-", ".tmp");
                tempFile.toFile().deleteOnExit();
                // 将已缓存的内存数据写入文件
                Files.write(tempFile, memoryBuffer.toByteArray(), StandardOpenOption.WRITE);
                memoryBuffer = null;
                cacheOutput = Files.newOutputStream(tempFile, StandardOpenOption.APPEND);
                isCachedToDisk = true;
            }
        }

        // 关闭原始流
        originalInput.close();
        cacheOutput.close();

        // 返回 SHA256 哈希
        byte[] hashBytes = digest.digest();
        return bytesToHex(hashBytes);
    }

    /**
     * 获取缓存的输入流（用于上传）
     */
    public InputStream getCachedInputStream() throws IOException {
        if (isCachedToDisk) {
            return Files.newInputStream(tempFile);
        } else if (memoryBuffer != null) {
            return new ByteArrayInputStream(memoryBuffer.toByteArray());
        } else {
            throw new IllegalStateException("缓存未初始化");
        }
    }

    /**
     * 清理缓存
     */
    public void cleanUp() {
        if (tempFile != null) {
            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
