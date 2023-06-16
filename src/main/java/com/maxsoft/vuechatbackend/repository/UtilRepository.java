package com.maxsoft.vuechatbackend.repository;

import com.maxsoft.vuechatbackend.entity.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilRepository extends JpaRepository<Utility, String> {

}
