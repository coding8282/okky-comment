package org.okky.comment.resource;

import lombok.AllArgsConstructor;
import org.okky.comment.application.ReplyCommentService;
import org.okky.comment.application.command.ModifyReplyCommentCommand;
import org.okky.comment.application.command.WriteReplyCommentCommand;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.comment.domain.repository.ReplyCommentRepository;
import org.okky.comment.domain.repository.dto.ReplyCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
class ReplyCommentResource {
    ReplyCommentService service;
    ReplyCommentRepository repository;
    ContextHolder holder;

    @GetMapping(value = "/comments/{commentId}/exists")
    boolean exists(@PathVariable String commentId) {
        return repository.existsById(commentId);
    }

    @GetMapping(value = "/replies/{replyId}/comments", produces = APPLICATION_JSON_VALUE)
    PagingEnvelop getByReplyId(
            @PathVariable String replyId,
            @PageableDefault(size = 15) Pageable request) {
        Page<ReplyComment> result = repository.findByReplyIdAndCommentedOnAfterOrderByCommentedOnDesc(replyId, 0L, request);
        return new PagingEnvelop(result);
    }

    @GetMapping(value = "/commenters/{commenterId}/comments/count")
    long getReplyCountOfMember(@PathVariable String commenterId) {
        return repository.countByCommenterId(commenterId);
    }

    @GetMapping(value = "/replies/{replyIds}/comments/count", produces = APPLICATION_JSON_VALUE)
    List<Long> getReplyCounts(@PathVariable List<String> replyIds) {
        return replyIds
                .stream()
                .map(replyId -> repository.countByReplyId(replyId))
                .collect(toList());
    }

    @PostMapping(value = "/replies/{replyId}/comments", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    ReplyCommentDto write(
            @PathVariable String replyId,
            @RequestBody WriteReplyCommentCommand cmd) {
        cmd.setReplyId(replyId);
        cmd.setCommenterId(holder.getId());
        ReplyComment comment = service.write(cmd);
        return ReplyCommentDto.toDto(comment);
    }

    @PutMapping(value = "/comments/{commentId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    void modify(@PathVariable String commentId, @RequestBody ModifyReplyCommentCommand cmd) {
        cmd.setCommentId(commentId);
        service.modify(cmd);
    }

    @DeleteMapping(value = "/comments/{commentId}")
    @ResponseStatus(NO_CONTENT)
    void remove(@PathVariable String commentId) {
        service.remove(commentId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/comments/{commentId}/force")
    @ResponseStatus(NO_CONTENT)
    void removeForce(@PathVariable String commentId) {
        service.removeForce(commentId);
    }
}
