package com.itwh.serve.mapper;

import com.itwh.pojo.dto.SaveArticleDTO;
import com.itwh.pojo.dto.ListArticleDTO;
import com.itwh.pojo.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {

    /**
     * 新增文章
     * @return
     */
    public void save(SaveArticleDTO saveArticleDTO);

    /**
     * 修改文章信息
     * @param article
     * @return
     */
    void update(Article article);

    /**
     * 获取文章信息
     * @param listArticleDTO
     * @return
     */
    List<Article> list(ListArticleDTO listArticleDTO);

    /**
     * 根据文章id删除文章
     * @param id
     * @return
     */
    @Delete("delete from article where id=#{id}")
    void delete(Long id);

    /**
     * 增加文章收藏量
     * @param articleId
     */
    @Update("update article set collect = collect+1 where id=#{articleId}")
    void saveCollectedArticle(Long articleId);

    /**
     * 减去文章收藏量
     * @param articleId
     */
    @Update("update article set collect = collect-1 where id=#{articleId}")
    void deleteCollectedArticle(Long articleId);

    /**
     * 客户点赞文章
     * @param articleId
     */
    @Update("update article set like_num = like_num+1 where id=#{articleId}")
    void likeArticle(Long articleId);

    /**
     * 客户取消点赞文章
     * @param articleId
     */
    @Update("update article set like_num = like_num-1 where id=#{articleId}")
    void deleteLikeArticle(Long articleId);

    /**
     * 根据文章id查询文章信息
     * @param id
     * @return
     */
    @Select("select * from article where id=#{id}")
    Article listArticleById(Long id);
}
