package com.justincranford.springsecurity.webauthn.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.management.MapPublicKeyCredentialUserEntityRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyPublicKeyCredentialUserEntityRepository extends MapPublicKeyCredentialUserEntityRepository {
    @Override
    public PublicKeyCredentialUserEntity findById(final Bytes id) {
        final PublicKeyCredentialUserEntity userEntity = super.findById(id);
        if (userEntity == null) {
            log.info("findById failed, id: {}, ", id);
        } else {
            log.info("findById succeeded, id: {}, name: {}, displayName: {}", id, userEntity.getName(), userEntity.getDisplayName());
        }
        return userEntity;
    }

    @Override
    public PublicKeyCredentialUserEntity findByUsername(final String username) {
        final PublicKeyCredentialUserEntity userEntity = super.findByUsername(username);
        if (userEntity == null) {
            log.info("findByUsername failed, username: {}", username);
        } else {
            log.info("findByUsername succeeded, username: {}, name: {}, displayName: {}", username, userEntity.getId(), userEntity.getDisplayName());
        }
        return userEntity;
    }

    @Override
    public void save(final PublicKeyCredentialUserEntity userEntity) {
        super.save(userEntity);
        log.info("save, id: {}, name: {}, displayName: {}", userEntity.getId(), userEntity.getName(), userEntity.getDisplayName());
    }

    @Override
    public void delete(final Bytes id) {
        super.delete(id);
        log.info("delete, id: {}", id);
    }
}
