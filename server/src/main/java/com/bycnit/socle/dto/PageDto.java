package com.bycnit.socle.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageDto<T> implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** The content of the page */
    private List<T> content;

    /** Total pages */
    private long totalPages;

    /** Number of elements */
    private long totalElements;

    /** Is it the first page */
    private boolean first;

    /** Is it the last page */
    private boolean last;

    /**
     * Constructor
     * 
     * @param content
     *            the list of elements
     * @param page
     *            contains infos on the page
     */
    public PageDto(final List<T> content, final Page<?> page) {
        this.content = content;

        totalPages = page.getTotalPages();
        totalElements = page.getTotalElements();
        first = page.isFirst();
        last = page.isLast();
    }
}
