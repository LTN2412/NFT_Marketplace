package com.nftmarketplace.order_service.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Document("Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @MongoId
    String id;

    @Field(name = "user_id")
    String userId;

    @Field(name = "cart_id")
    String cartId;

    @Field(name = "voucher_id")
    String voucherId;

    @Field(name = "total_price")
    String totalPrice;

    @Field(name = "shipping_fee")
    String shippingFee;

    @Field(name = "final_total_price")
    String finalTotalPrice;

    @Field(name = "order_date")
    Date orderDate;

    @Field(name = "payment_method")
    String paymentMethod;

    String userName;

    String address;

    String phoneNumber;

    String status;

    Set<String> assetOrderIds = new HashSet<>();
}
