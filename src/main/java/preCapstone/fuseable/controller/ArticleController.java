package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.article.*;
import preCapstone.fuseable.model.article.Article;
import preCapstone.fuseable.model.article.FormStatus;
import preCapstone.fuseable.service.ArticleService;
import preCapstone.fuseable.service.PaginationService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArticleController {

    private final ArticleService articleService;
    @GetMapping("/list/{projectId}")
    public List<Article> getAllArticles(@PathVariable("projectId") Long projectId) {
        return articleService.findArticleByProjectId(projectId);
    }

    // 공지사항 생성 페이지
    @PostMapping("/{userId}/{projectId}")
    public Article createArticle(@RequestBody ArticleRequest articleRequest, @PathVariable ("userId") Long userId, @PathVariable("projectId") Long projectId) {
        return articleService.createArticle(articleRequest.toDto(userId, projectId));
    }

    // 공지사항 상세보기 페이지
    @GetMapping("/{article_id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long article_id) {
        return articleService.getArticle(article_id);
    }

    @PutMapping("/{article_id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long article_id, @RequestBody Article article) {
        return articleService.updateArticle(article_id, article);
    }

    @DeleteMapping("/{article_id}")
    public ResponseEntity<Map<String, Boolean>> deleteArticle(@PathVariable Long article_id) {
        return articleService.deleteArticle(article_id);
    }


//    private final PaginationService paginationService;
//
//    // 공지사항 페이지
//    @GetMapping
//    public String articles(
//            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
//            ModelMap map
//    ) {
//        Page<ArticleResponse> articles = articleService.searchArticles(pageable).map(ArticleResponse::from);
//        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
//
//        map.addAttribute("articles", articles);
//        map.addAttribute("paginationBarNumbers", barNumbers);
//
//        return "articles/index";
//    }
//
//    // 상세 페이지
//    @GetMapping("/{articleId}")
//    public String article(@PathVariable Long articleId, ModelMap map) {
//        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));
//
//        map.addAttribute("article", article);
//        map.addAttribute("totalCount", articleService.getArticleCount());
//
//        return "articles/detail";
//    }
//
//    // 새 공지사항 작성 페이지
//    @GetMapping("/form")
//    public String articleForm(ModelMap map) {
//        map.addAttribute("formStatus", FormStatus.CREATE);
//
//        return "articles/form";
//    }
//
//    // 새 공지사항 등록
//    @PostMapping("/form")
//    public String postNewArticle(ArticleRequest articleRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
//        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));
//
//        return "redirect:/articles";
//    }
//
//    // 공지사항 수정 페이지
//    @GetMapping("/{articleId}/form")
//    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
//        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));
//
//        map.addAttribute("article", article);
//        map.addAttribute("formStatus", FormStatus.UPDATE);
//
//        return "articles/form";
//    }
//
//    // 공지사항 수정
//    @PostMapping("/{articleId}/form")
//    public String updateArticle(
//            @PathVariable Long articleId,
//            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
//            ArticleRequest articleRequest
//    ) {
//        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));
//
//        return "redirect:/articles/" + articleId;
//    }
//
//    // 공지사항 삭제
//    @PostMapping("/{articleId}/delete")
//    public String deleteArticle(
//            @PathVariable Long articleId,
//            @AuthenticationPrincipal BoardPrincipal boardPrincipal
//    ) {
//        articleService.deleteArticle(articleId, boardPrincipal.getUsername());
//
//        return "redirect:/articles";
//    }



}
