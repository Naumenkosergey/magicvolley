package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.CampPackageCardEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CampPackageCardDto {

    private Integer packageId;
    private String name;
    private String costNamingLink;
    private String info;
    private Integer totalPrice;
    private Integer bookingPrice;

    private Integer firstPrice;
    private String firstLimitation;

    private Integer secondPrice;
    private String secondLimitation;

    private Integer thirdPrice;
    private String thirdLimitation;

    public CampPackageCardDto(CampPackageCardEntity campPackageCardEntity) {
        this.packageId = campPackageCardEntity.getPackageCard().getId();
        this.name = campPackageCardEntity.getPackageCard().getName();
        this.costNamingLink = campPackageCardEntity.getPackageCard().getCostNamingLink();
        this.info = campPackageCardEntity.getInfo();
        this.totalPrice = campPackageCardEntity.getTotalPrice();
        this.bookingPrice = campPackageCardEntity.getBookingPrice();
        this.firstPrice = campPackageCardEntity.getFirstPrice();
        this.firstLimitation = campPackageCardEntity.getFirstLimitation();
        this.secondPrice = campPackageCardEntity.getSecondPrice();
        this.secondLimitation = campPackageCardEntity.getSecondLimitation();
        this.thirdPrice = campPackageCardEntity.getThirdPrice();
        this.thirdLimitation = campPackageCardEntity.getThirdLimitation();
    }
}
