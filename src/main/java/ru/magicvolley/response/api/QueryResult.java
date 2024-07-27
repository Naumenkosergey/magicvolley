package ru.magicvolley.response.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryResult<T> {
    private List<T> entities;
    private Integer pageNumber;
    private Integer pageCount;
    private Integer pageTotal;
    private String sort;
    private Integer offset;
    private Boolean hasNext;
}