package com.itwh.serve.service;

import com.itwh.pojo.dto.SaveArticleDTO;
import com.itwh.pojo.dto.ListArticleDTO;
import com.itwh.pojo.dto.UpdateArticleDTO;
import com.itwh.pojo.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    /**
     * 新增文章
     * @return
     */
    void saveArticle(SaveArticleDTO saveArticleDTO);

    /**
     * 修改文章信息
     * @param updateArticleDTO
     * @return
     */
    void updateArticle(UpdateArticleDTO updateArticleDTO);

    /**
     * 获取文章信息
     * @param listArticleDTO
     * @return
     */
    List<Article> listArticle(ListArticleDTO listArticleDTO);

    /**
     * 根据文章id删除文章
     * @param id
     * @return
     */
    void deleteArticle(Long id);

    /**
     * 根据文章id查询文章信息
     * @param id
     * @return
     */
    Article listArticleById(Long id);
}
