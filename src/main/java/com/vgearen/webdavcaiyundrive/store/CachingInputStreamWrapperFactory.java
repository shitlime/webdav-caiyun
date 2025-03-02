package com.vgearen.webdavcaiyundrive.store;

import com.vgearen.webdavcaiyundrive.config.CaiyunProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class CachingInputStreamWrapperFactory {
    @Autowired
    private CaiyunProperties caiyunProperties;

    public CachingInputStreamWrapper create(InputStream input) {
        return new CachingInputStreamWrapper(input, caiyunProperties);
    }
}
