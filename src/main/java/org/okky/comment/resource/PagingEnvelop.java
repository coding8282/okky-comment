package org.okky.comment.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.okky.comment.domain.model.ReplyComment;
import org.springframework.data.domain.Page;

@Getter
public class PagingEnvelop {
    Paging paging;
    Object content;

    public PagingEnvelop(Page<ReplyComment> page) {
        int pageNumber = page.getPageable().getPageNumber();
        boolean isFinalPage = pageNumber + 1 == page.getTotalPages();
        this.paging = new Paging(page.getTotalElements(), page.getNumberOfElements(), page.getTotalPages(), isFinalPage);
        this.content = page.getContent();
    }

    @AllArgsConstructor
    @Getter
    static class Paging {
        long totalResults;
        long pageResults;
        long finalPageNo;
        Boolean isFinalPage;
    }
}
