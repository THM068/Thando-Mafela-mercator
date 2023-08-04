package com.mercator

package object model {

  sealed trait ProductCode
  case object Apple extends ProductCode
  case object Orange extends ProductCode
  case object Banana extends ProductCode

  case class Price(cost: BigDecimal) extends AnyVal

  case class Product(productCode: ProductCode, price: Price)

  case class CartItem(product: Product, quantity: Int) {
    def totalPrice(): BigDecimal = product.price.cost * quantity
  }

  abstract class DiscountType(dealQuantity: Int, priceOf: Int = 1) {
    def applyDiscount(itemPrice: BigDecimal, itemTotalQuantity: Int): BigDecimal =
      ((itemTotalQuantity / dealQuantity) * (BigDecimal(priceOf) * itemPrice) ) + ((itemTotalQuantity % dealQuantity) * itemPrice)
  }
  case class TwoForOne(dealQuantity: Int  = 2)  extends DiscountType(dealQuantity)

  case class ThreeForTwo(dealQuantity: Int = 3, priceOf: Int = 2) extends DiscountType(dealQuantity, priceOf)

  case class Offer(product: Product, discountType: DiscountType)
}
