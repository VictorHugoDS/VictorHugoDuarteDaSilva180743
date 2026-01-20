package br.com.hamix.domain.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PaginationResponse<T> {
    List<T> content;
    String page;
    String size;
    String totalPages;
    String totalElements;
}
