package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.Util;
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
    private String totalPrice;
    private String bookingPrice;

    private String firstPrice;
    private String firstLimitation;
    private String firstLimitationString;

    private String secondPrice;
    private String secondLimitation;
    private String secondLimitationString;

    private String thirdPrice;
    private String thirdLimitation;
    private String thirdLimitationString;

    private TypePackageCard type;
    private String displayName;

    public CampPackageCardDto(CampPackageCardEntity campPackageCardEntity) {
        String namePackage = campPackageCardEntity.getPackageCard().getNamePackage();
        this.packageId = campPackageCardEntity.getPackageCard().getId();
        this.name = namePackage;
        this.costNamingLink = campPackageCardEntity.getPackageCard().getCostNamingLink();
        this.info = campPackageCardEntity.getInfo();
        this.totalPrice = Util.getOrUndefinedIfNull(campPackageCardEntity.getTotalPrice());
        this.bookingPrice = Util.getOrUndefinedIfNull(campPackageCardEntity.getBookingPrice());
        this.firstPrice = Util.getOrUndefinedIfNull(campPackageCardEntity.getFirstPrice());
        this.firstLimitation = Util.getOrUndefinedIfNullForLimitation(campPackageCardEntity.getFirstLimitation());
        this.firstLimitationString = Util.getOrUndefinedIfNullForLimitationString(campPackageCardEntity.getFirstLimitation());
        this.secondPrice = Util.getOrUndefinedIfNull(campPackageCardEntity.getSecondPrice());
        this.secondLimitation = Util.getOrUndefinedIfNullForLimitation(campPackageCardEntity.getSecondLimitation());
        this.secondLimitationString = Util.getOrUndefinedIfNullForLimitationString(campPackageCardEntity.getSecondLimitation());
        this.thirdPrice = Util.getOrUndefinedIfNull(campPackageCardEntity.getThirdPrice());
        this.thirdLimitation = Util.getOrUndefinedIfNullForLimitation(campPackageCardEntity.getThirdLimitation());
        this.thirdLimitationString = Util.getOrUndefinedIfNullForLimitationString(campPackageCardEntity.getThirdLimitation());
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
        } else {
            return TypePackageCard.TOUR;
        }
    }

    private String getDisplayNamePackage(String packageName) {
        if (packageName.toUpperCase().contains(TypePackageCard.GOLD.name())) {
            return "Пакет \"Gold\"";
        } else if (packageName.toUpperCase().contains(TypePackageCard.SILVER.name())) {
            return "Пакет \"Silver\"";
        } else if (packageName.toUpperCase().contains(TypePackageCard.PREMIUM.name())) {
            return "Пакет \"Premium\"";
        } else {
            return "Пакет \"Тур\"";
        }
    }

}
