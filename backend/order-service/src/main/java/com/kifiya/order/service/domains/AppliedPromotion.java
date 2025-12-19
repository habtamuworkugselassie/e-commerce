package com.kifiya.order.service.domains;

import com.kifiya.order.models.PromotionType;

public record AppliedPromotion(PromotionType type, String name, String description) {
}
