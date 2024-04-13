package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.entity.Article;
import com.itwh.serve.service.CollectedArticleService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/collected/article")
public class CollectedArticleController {

    @Autowired
    private CollectedArticleService collectedArticleService;

    /**
     * 新增收藏文章
     * @param articleId
     * @return
     */
    @PostMapping("/save/{articleId}")
    public Result saveCollectedArticle(@PathVariable Long articleId){
        collectedArticleService.saveCollectedArticle(articleId);
        return Result.success("用户新增文章收藏成功");
    }

    /**
     * 获取用户所有收藏文章信息
     * @return
     */
    @GetMapping("/list")
    public Result listCollectedArticle(){
        List<Article> articles = collectedArticleService.listCollectedArticle();
        return Result.success("用户收藏文章信息获取成功", articles);
    }

    /**
     * 取消文章收藏
     * @param articleId
     * @return
     */
    @DeleteMapping("/delete/{articleId}")
    public Result deleteCollectedArticle(@PathVariable Long articleId){
        collectedArticleService.deleteCollectedArticle(articleId);
        return Result.success("文章收藏取消成功");
    }

}
