package com.itwh.serve.service;

import com.itwh.pojo.entity.Article;
import com.itwh.pojo.entity.LikeArticle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeArticleService {

    /**
     * 客户点赞文章
     * @param articleId
     * @return
     */
    void likeArticle(Long articleId);

    /**
     * 客户取消文章点赞
     * @param articleId
     * @return
     */
    void deleteLikeArticle(Long articleId);

    /**
     * 获取客户点赞过的文章
     * @return
     */
    List<Article> listLikeArticle();
}
