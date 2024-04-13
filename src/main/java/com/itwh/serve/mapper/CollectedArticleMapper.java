package com.itwh.serve.mapper;

import com.itwh.pojo.entity.Article;
import com.itwh.pojo.entity.CollectedArticle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollectedArticleMapper {

    /**
     * 新增收藏文章
     * @param collectedArticle
     * @return
     */
    public void save(CollectedArticle collectedArticle);

    /**
     * 获取用户所有收藏文章信息
     * @return
     */
    List<Article> list(Long userId);

    /**
     * 取消文章收藏
     * @param collectedArticle
     * @return
     */
    @Delete("delete from collected_article where article_id=#{articleId} and user_id=#{userId}")
    void delete(CollectedArticle collectedArticle);

    /**
     * 获取收藏文章信息
     * @param collectedArticle
     */
    @Select("select * from collected_article where article_id=#{articleId} and user_id=#{userId}")
    CollectedArticle listByCollectedArticle(CollectedArticle collectedArticle);
}
