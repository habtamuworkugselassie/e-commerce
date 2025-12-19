package com.kifiya.order.service.promotion;

import com.kifiya.order.models.Product;
import com.kifiya.order.models.Promotion;
import com.kifiya.order.models.PromotionType;
import com.kifiya.order.service.domains.LineItem;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PromotionRuleService {
    PromotionType getPromotionType();
    void apply(Promotion promotion, Map<UUID, Product> productMap, List<LineItem> lineItems);
}
