package com.kifiya.order.controller.domains;

public record AppliedPromotionDto(
        String promotionName,
        String ruleType,
        String description
) {
}
