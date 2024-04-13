package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.common.exception.BaseException;
import com.itwh.pojo.entity.Article;
import com.itwh.pojo.entity.LikeArticle;
import com.itwh.serve.mapper.ArticleMapper;
import com.itwh.serve.mapper.LikeArticleMapper;
import com.itwh.serve.service.LikeArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeArticleServiceImpl implements LikeArticleService {

    @Autowired
    private LikeArticleMapper likeArticleMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 客户点赞文章
     * @param articleId
     * @return
     */
    public void likeArticle(Long articleId) {
        LikeArticle likeArticle = new LikeArticle();
        likeArticle.setArticleId(articleId);
        likeArticle.setUserId(BaseContext.getCurrentId());
        //检查文章是否被点赞过
        LikeArticle likeArticle1 = likeArticleMapper.likeArticle(likeArticle);
        if (likeArticle1 == null){
            //添加文章点赞信息
            likeArticleMapper.save(likeArticle);
        }else {
            throw new BaseException("该文章已经点赞过了");
        }

        //增加文章点赞量
        articleMapper.likeArticle(articleId);
    }

    /**
     * 客户取消文章点赞
     * @param articleId
     * @return
     */
    public void deleteLikeArticle(Long articleId) {
        //减去文章点赞量
        articleMapper.deleteLikeArticle(articleId);

        //删除文章点赞信息
        LikeArticle likeArticle = new LikeArticle();
        likeArticle.setArticleId(articleId);
        likeArticle.setUserId(BaseContext.getCurrentId());
        likeArticleMapper.delete(likeArticle);
    }

    /**
     * 获取客户点赞过的文章
     * @return
     */
    public List<Article> listLikeArticle() {
        return likeArticleMapper.list(BaseContext.getCurrentId());
    }


}
