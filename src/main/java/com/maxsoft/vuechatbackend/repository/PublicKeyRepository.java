package com.maxsoft.vuechatbackend.repository;

import com.maxsoft.vuechatbackend.entity.PublicKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PublicKeyRepository extends JpaRepository<PublicKey, UUID> {
}

