package com.itwh.serve.mapper;

import com.itwh.pojo.entity.Article;
import com.itwh.pojo.entity.LikeArticle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LikeArticleMapper {

    /**
     * 添加点赞信息
     * @param likeArticle
     */
    void save(LikeArticle likeArticle);

    /**
     * 删除点赞信息
     * @param likeArticle
     */
    @Delete("delete from like_article where article_id=#{articleId} and user_id=#{userId}")
    void delete(LikeArticle likeArticle);

    /**
     * 查询点赞的文章
     * @param userId
     */
    List<Article> list(Long userId);

    /**
     * 检查文章是否被点赞过
     * @param likeArticle
     * @return
     */
    @Select("select * from like_article where article_id=#{articleId} and user_id=#{userId}")
    LikeArticle likeArticle(LikeArticle likeArticle);
}
