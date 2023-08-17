package com.maxsoft.vuechatbackend.repository;

import com.maxsoft.vuechatbackend.entity.EncryptAccountKey;
import com.maxsoft.vuechatbackend.entity.enums.EncryptKeyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface EncryptAccountKeyRepository extends JpaRepository<EncryptAccountKey, UUID> {

    Optional<EncryptAccountKey> findByAccountIdAndType(UUID accountId, EncryptKeyType type);
}

