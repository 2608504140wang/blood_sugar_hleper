package com.itwh.serve.service.Impl;

import com.itwh.pojo.dto.SaveArticleDTO;
import com.itwh.pojo.dto.ListArticleDTO;
import com.itwh.pojo.dto.UpdateArticleDTO;
import com.itwh.pojo.entity.Article;
import com.itwh.serve.mapper.ArticleMapper;
import com.itwh.serve.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;


    /**
     * 新增文章
     * @return
     */
    public void saveArticle(SaveArticleDTO saveArticleDTO) {
        articleMapper.save(saveArticleDTO);
    }

    /**
     * 修改文章信息
     * @param updateArticleDTO
     * @return
     */
    public void updateArticle(UpdateArticleDTO updateArticleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(updateArticleDTO, article);
        articleMapper.update(article);
    }

    /**
     * 获取文章信息
     * @param listArticleDTO
     * @return
     */
    public List<Article> listArticle(ListArticleDTO listArticleDTO) {
        return articleMapper.list(listArticleDTO);
    }

    /**
     * 根据文章id删除文章
     * @param id
     * @return
     */
    public void deleteArticle(Long id) {
        articleMapper.delete(id);
    }

    /**
     * 根据文章id查询文章信息
     * @param id
     * @return
     */
    public Article listArticleById(Long id) {
        return articleMapper.listArticleById(id);
    }

}
