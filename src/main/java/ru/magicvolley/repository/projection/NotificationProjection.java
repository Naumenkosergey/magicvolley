package ru.magicvolley.repository.projection;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotificationProjection {

    private UUID campId;
    private Long numberOfUnviewed;
}
