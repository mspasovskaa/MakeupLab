package com.makeuplab.repository;

import com.makeuplab.model.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizScoreRepository extends JpaRepository<QuizScore,Long> {
}
