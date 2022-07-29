package com.makeuplab.repository;

import com.makeuplab.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ContentRepository extends JpaRepository<Content,Long> {
}
