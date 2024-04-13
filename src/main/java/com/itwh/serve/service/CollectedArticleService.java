package com.itwh.serve.service;

import com.itwh.pojo.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectedArticleService {

    /**
     * 新增收藏文章
     *
     * @param articleId
     * @return
     */
    void saveCollectedArticle(Long articleId);

    /**
     * 获取用户所有收藏文章信息
     * @return
     */
    List<Article> listCollectedArticle();

    /**
     * 取消文章收藏
     * @param articleId
     * @return
     */
    void deleteCollectedArticle(Long articleId);
}
