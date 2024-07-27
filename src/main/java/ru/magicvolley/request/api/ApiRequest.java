package ru.magicvolley.request.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest {
    @Valid
    private List<SearchParam> searchParams;
    @Min(value = 1)
    private Integer pageNumber;
    @Min(value = 1)
    private Integer pageSize;
    private String sort;
    private Boolean withPermissions = Boolean.FALSE;
    private UUID initiatorId;
    @Min(0)
    private Integer offset = 0;    // TODO: 19.05.2021 Это лишнее, удалить в будущем

    public ApiRequest withTenant(UUID tenantId) {
        return this.with("tenantId", tenantId.toString());
    }

    public ApiRequest with(String path, String value) {
        if (this.searchParams == null) {
            searchParams = new ArrayList<>();
        }
        SearchParam param = new SearchParam();
        param.setPath(path);
        param.setPredicate(Predicate.EQ);
        param.setValue(value);
        searchParams.add(param);
        return this;
    }

    public ApiRequest with(String path, String value, Predicate predicate) {
        if (this.searchParams == null) {
            searchParams = new ArrayList<>();
        }
        SearchParam param = new SearchParam();
        param.setPath(path);
        param.setPredicate(predicate);
        param.setValue(value);
        searchParams.add(param);
        return this;
    }

    @Getter
    @AllArgsConstructor
    public enum Predicate {
        NOT_EQ("@"),
        EQ(":"),
        GT(">"),
        GTE(">="),
        LT("<"),
        LTE("<="),
        LK("#"),
        BETWEEN("-");
        private String value;
    }

    @Data
    public static class SearchParam {
        @NotNull(message = "{starter.error.text.must-be-filled}")
        private String path;
        @NotNull(message = "{starter.error.text.must-be-filled}")
        private Predicate predicate;
        private String value;
        private String[] values;
    }
}