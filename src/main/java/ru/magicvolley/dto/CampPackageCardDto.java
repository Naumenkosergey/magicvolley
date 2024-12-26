package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.CampPackageCardEntity;
import ru.magicvolley.enums.TypePackageCard;

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

    private TypePackageCard type;
    private String displayName;

    public CampPackageCardDto(CampPackageCardEntity campPackageCardEntity) {
        String namePackage = campPackageCardEntity.getPackageCard().getNamePackage();
        this.packageId = campPackageCardEntity.getPackageCard().getId();
        this.name = namePackage;
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
        this.type = getType(namePackage);
        this.displayName = getDisplayNamePackage(namePackage);
    }

    private TypePackageCard getType(String packageName) {
        if (packageName.toUpperCase().contains(TypePackageCard.GOLD.name())) {
            return TypePackageCard.GOLD;
        } else if (packageName.toUpperCase().contains(TypePackageCard.SILVER.name())) {
            return TypePackageCard.SILVER;
        } else if (packageName.toUpperCase().contains(TypePackageCard.PREMIUM.name())) {
            return TypePackageCard.PREMIUM;
        } else if (packageName.toUpperCase().contains(TypePackageCard.TOUR.name())) {
            return TypePackageCard.TOUR;
        } else return null;
    }

    private String getDisplayNamePackage(String packageName) {
        if (packageName.toUpperCase().contains(TypePackageCard.GOLD.name())) {
            return "Пакет \"Gold\"";
        } else if (packageName.toUpperCase().contains(TypePackageCard.SILVER.name())) {
            return "Пакет \"Silver\"";
        } else if (packageName.toUpperCase().contains(TypePackageCard.PREMIUM.name())) {
            return "Пакет \"Premium\"";
        } else if (packageName.toUpperCase().contains(TypePackageCard.TOUR.name())) {
            return "Пакет \"Tour\"";
        } else return null;
    }
}
