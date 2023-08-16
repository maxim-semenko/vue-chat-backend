package com.maxsoft.vuechatbackend.repository;

import com.maxsoft.vuechatbackend.entity.EncryptAccountKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EncryptAccountKeyRepository extends JpaRepository<EncryptAccountKey, UUID> {
}

