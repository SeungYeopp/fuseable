package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.article.ArticleDto;
import preCapstone.fuseable.exception.ResourceNotFoundException;
import preCapstone.fuseable.model.article.Article;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.article.ArticleRepository;
import preCapstone.fuseable.repository.project.ProjectRepository;
import preCapstone.fuseable.repository.user.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    public Article createArticle(ArticleDto dto) {

        User user = userRepository.getReferenceById(dto.userId());
        Project project = projectRepository.getReferenceById(dto.projectId());

        Article article = dto.toEntity(user, project);
        return articleRepository.save(article);
    }

    public ResponseEntity<Article> getArticle(Long article_id) {
        Article article = articleRepository.findById(article_id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 공지사항이 존재하지 않습니다."));

        return ResponseEntity.ok(article);
    }

    public ResponseEntity<Article> updateArticle(Long article_id, Article updateArticle) {
        Article article = articleRepository.findById(article_id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 공지사항이 존재하지 않습니다."));

        article.setTitle(updateArticle.getTitle());
        article.setContent(updateArticle.getContent());

        Article updatedArticle = articleRepository.save(article);
        return ResponseEntity.ok(updatedArticle);
    }

    public ResponseEntity<Map<String, Boolean>> deleteArticle(Long article_id) {
        Article article = articleRepository.findById(article_id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 공지사항이 존재하지 않습니다"));

        articleRepository.delete(article);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete Article NO: [" + article_id + "]", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public List<Article> findArticleByProjectId(Long projectId){
        return articleRepository.findAllByProjectId(projectId);
    }

//
//    @Transactional(readOnly = true)
//    public Page<ArticleDto> searchArticles(Pageable pageable) {
//        return articleRepository.findAll(pageable).map(ArticleDto::from);
//    }
//
//    @Transactional(readOnly = true)
//    public ArticleDto getArticle(Long articleId) {
//        return articleRepository.findById(articleId)
//                .map(ArticleDto::from)
//                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
//    }
//
//    public void saveArticle(ArticleDto dto) {
//        User user = userRepository.getReferenceById(dto.userDto().kakaoId());
//
//        Article article = dto.toEntity(user);
//        articleRepository.save(article);
//    }
//
//    public void updateArticle(Long articleId, ArticleDto dto) {
//        try {
//            Article article = articleRepository.getReferenceById(articleId);
//            User user = userRepository.getReferenceById(dto.userDto().kakaoId());
//
//            if (article.getUser().equals(user)) {
//                if (dto.title() != null) { article.setTitle(dto.title()); }
//                if (dto.content() != null) { article.setContent(dto.content()); }
//
//                articleRepository.flush();
//
//            }
//        } catch (EntityNotFoundException e) {
//            log.warn("게시글 업데이트 실패. - {}", e.getLocalizedMessage());
//        }
//    }
//
//    public void deleteArticle(long articleId, String userId) {
//        articleRepository.deleteByIdAndUser(articleId, userId);
//        articleRepository.flush();
//    }
//
//    public long getArticleCount() {
//        return articleRepository.count();
//    }

}
