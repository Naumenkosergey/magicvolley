package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.PackageCardEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PackageCardDto {

    private Integer packageId;
    private String name;
    private String costNamingLink;
    private String info;

    public PackageCardDto(PackageCardEntity packageCard) {
        this.packageId = packageCard.getId();
        this.name = packageCard.getNamePackage();
        this.costNamingLink = packageCard.getCostNamingLink();
        this.info = packageCard.getInfo();
    }
}
