package ru.magicvolley.response.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.magicvolley.request.api.ApiRequest;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class ApiResponse<T> {
    private static final ApiResponse<Boolean> OK_RESPONSE = new ApiResponse<>(Boolean.TRUE);
    private int statusCode = HttpStatus.OK.value();
    private T result;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer pageTotal;
    private String sort;
    private Set<String> permissions;
//    private Integer offset;
//    private Boolean hasNext;

    @SuppressWarnings("unchecked")
    public ApiResponse(QueryResult<?> queryResult) {
        this(queryResult, null);
    }

    @SuppressWarnings("unchecked")
    public ApiResponse(QueryResult<?> queryResult, Set<String> permissions) {
        this.result = (T) queryResult.getEntities();
        this.pageNumber = queryResult.getPageNumber();
        this.pageSize = queryResult.getPageCount();
        this.pageTotal = queryResult.getPageTotal();
        this.sort = queryResult.getSort();
        this.permissions = permissions;
//        this.hasNext = queryResult.getHasNext();
//        this.offset = queryResult.getOffset();
    }

    public ApiResponse(T result) {
        this(result, null);
    }

    public ApiResponse(T result, Set<String> permissions) {
        this.result = result;
        this.permissions = permissions;
    }

    public static ApiResponse<Boolean> ok() {
        return OK_RESPONSE;
    }

    public ApiResponse(T result, ApiRequest request, Integer pageTotal, Set<String> permissions) {
        this.result = result;
        this.pageNumber = request.getPageNumber();
        this.pageSize = request.getPageSize();
        this.pageTotal = pageTotal;
        this.permissions = permissions;
    }

    public ApiResponse(T result, ApiRequest request, Integer pageTotal, Set<String> permissions, String sort) {
        this.result = result;
        this.pageNumber = request.getPageNumber();
        this.pageSize = request.getPageSize();
        this.pageTotal = pageTotal;
        this.permissions = permissions;
        this.sort = sort;
    }

    public ApiResponse(T result, ApiRequest request, Integer pageTotal) {
        this(result, request, pageTotal, null);
    }
}