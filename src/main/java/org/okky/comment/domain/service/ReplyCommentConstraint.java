package org.okky.comment.domain.service;

import lombok.AllArgsConstructor;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.comment.domain.repository.ReplyCommentRepository;
import org.okky.share.execption.ExternalServiceError;
import org.okky.share.execption.ModelNotExists;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class ReplyCommentConstraint {
    private ReplyCommentRepository repository;
    private RestTemplate template;

    public void checkExists(String commentId) {
        checkExistsAndGet(commentId);
    }

    public ReplyComment checkExistsAndGet(String commentId) {
        return repository
                .findById(commentId)
                .orElseThrow(() -> new ModelNotExists(format("해당 코멘트는 존재하지 않습니다: '%s'", commentId)));
    }

    public void checkReplyExists(String replyId) {
        try {
            ResponseEntity<Boolean> result = template.getForEntity(format("/replies/%s/exists", replyId), Boolean.class);
            if (!result.getBody())
                throw new ModelNotExists(format("해당 답글은 존재하지 않기 때문에 코멘트를 달 수 없습니다: '%s'", replyId));
        } catch (HttpStatusCodeException e) {
            throw new ExternalServiceError(e.getResponseBodyAsByteArray());
        }
    }
}
