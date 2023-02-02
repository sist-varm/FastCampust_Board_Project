package com.fastcampus.board_project.repository;

import com.fastcampus.board_project.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<Article, Long> {
}