package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.entity.Article;
import com.itwh.serve.service.LikeArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/like/article")
public class LikeArticleController {

    @Autowired
    private LikeArticleService likeArticleService;

    /**
     * 客户点赞文章
     * @param articleId
     * @return
     */
    @PostMapping("/save/{articleId}")
    public Result likeArticle(@PathVariable Long articleId){
        likeArticleService.likeArticle(articleId);
        return Result.success("点赞文章成功！");
    }

    /**
     * 客户取消文章点赞
     * @param articleId
     * @return
     */
    @PostMapping("/delete/{articleId}")
    public Result deleteLikeArticle(@PathVariable Long articleId){
        likeArticleService.deleteLikeArticle(articleId);
        return Result.success("取消点赞文章成功！");
    }

    /**
     * 获取客户点赞过的文章
     * @return
     */
    @GetMapping("/list")
    public Result listLikeArticle(){
        List<Article> likeArticles = likeArticleService.listLikeArticle();
        return Result.success("用户点赞过的文章信息获取成功", likeArticles);
    }
}
