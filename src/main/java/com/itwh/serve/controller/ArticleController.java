package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.dto.SaveArticleDTO;
import com.itwh.pojo.dto.ListArticleDTO;
import com.itwh.pojo.dto.UpdateArticleDTO;
import com.itwh.pojo.entity.Article;
import com.itwh.serve.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 新增文章
     * @return
     */
    @PostMapping("/admin/article/save")
    public Result saveArticle(@RequestBody SaveArticleDTO saveArticleDTO){
        articleService.saveArticle(saveArticleDTO);
        return Result.success("文章添加成功");
    }

    /**
     * 修改文章信息
     * @param updateArticleDTO
     * @return
     */
    @PutMapping("/admin/article/update")
    public Result updateArticle(@RequestBody UpdateArticleDTO updateArticleDTO){
        articleService.updateArticle(updateArticleDTO);
        return Result.success("文章信息修改成功");
    }

    /**
     * 根据文章类型，标题，作者获取文章信息
     * @return
     */
    @GetMapping("/article/list")
    public Result listArticle(String type, String title, String author){
        ListArticleDTO listArticleDTO = ListArticleDTO.builder()
                .type(type)
                .title(title)
                .author(author)
                .build();
        List<Article> articles = articleService.listArticle(listArticleDTO);
        return Result.success("文章信息获取成功", articles);
    }

    /**
     * 根据文章id查询文章信息
     * @param id
     * @return
     */
    @GetMapping("/article/list/{id}")
    public Result listArticleById(@PathVariable Long id){
        Article article = articleService.listArticleById(id);
        return Result.success("文章信息获取成功", article);
    }

    /**
     * 根据文章id删除文章
     * @param id
     * @return
     */
    @DeleteMapping("/admin/article/delete/{id}")
    public Result deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);
        return Result.success("文章信息删除成功");
    }

}
