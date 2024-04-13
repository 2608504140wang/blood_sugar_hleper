package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.common.exception.BaseException;
import com.itwh.pojo.entity.Article;
import com.itwh.pojo.entity.CollectedArticle;
import com.itwh.serve.mapper.ArticleMapper;
import com.itwh.serve.mapper.CollectedArticleMapper;
import com.itwh.serve.service.CollectedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CollectedArticleServiceImpl implements CollectedArticleService {

    @Autowired
    private CollectedArticleMapper collectedArticleMapper;

    @Autowired
    private ArticleMapper articleMapper;


    /**
     * 新增收藏文章
     * @param articleId
     * @return
     */
    public void saveCollectedArticle(Long articleId) {
        CollectedArticle collectedArticle = new CollectedArticle();
        collectedArticle.setArticleId(articleId);
        collectedArticle.setUserId(BaseContext.getCurrentId());

        //查询该文章是否已收藏
        CollectedArticle article = collectedArticleMapper.listByCollectedArticle(collectedArticle);

        if (article == null){
            //插入新增收藏
            collectedArticleMapper.save(collectedArticle);
        }else {
            throw new BaseException("收藏失败，该文章已经收藏过");
        }

        //增加文章收藏量
        articleMapper.saveCollectedArticle(articleId);
    }

    /**
     * 获取用户所有收藏文章信息
     * @return
     */
    public List<Article> listCollectedArticle() {
        return collectedArticleMapper.list(BaseContext.getCurrentId());
    }

    /**
     * 取消文章收藏
     * @param articleId
     * @return
     */
    public void deleteCollectedArticle(Long articleId) {
        //减去文章收藏量
        articleMapper.deleteCollectedArticle(articleId);

        //删除文章收藏信息
        CollectedArticle collectedArticle = new CollectedArticle();
        collectedArticle.setArticleId(articleId);
        collectedArticle.setUserId(BaseContext.getCurrentId());
        collectedArticleMapper.delete(collectedArticle);
    }
}
