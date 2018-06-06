package org.okky.comment.domain.repository;

import org.okky.comment.domain.model.ReplyComment;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = ReplyComment.class, idClass = String.class)
@EnableScan
@EnableScanCount
public interface ReplyCommentRepository {
    void save(ReplyComment comment);
    Optional<ReplyComment> findById(String id);
    Page<ReplyComment> findByReplyIdAndCommentedOnAfterOrderByCommentedOnDesc(String replyId, long range, Pageable page);
    boolean existsById(String id);
    long countByReplyId(String replierId);
    long countByCommenterId(String articleId);
    void delete(ReplyComment comment);
}
