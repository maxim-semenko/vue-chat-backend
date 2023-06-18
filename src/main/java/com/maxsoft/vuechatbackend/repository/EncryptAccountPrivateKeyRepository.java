package com.maxsoft.vuechatbackend.repository;

import com.maxsoft.vuechatbackend.entity.EncryptAccountPrivateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EncryptAccountPrivateKeyRepository extends JpaRepository<EncryptAccountPrivateKey, UUID> {
}

