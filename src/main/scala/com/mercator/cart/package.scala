package com.mercator

package object model {

  sealed trait ProductCode
  case object Apple extends ProductCode
  case object Orange extends ProductCode

  case class Price(cost: BigDecimal) extends AnyVal

  case class Product(productCode: ProductCode, price: Price)

  case class CartItem(product: Product, quantity: Int) {
    def totalPrice(): BigDecimal = product.price.cost * quantity
  }
}
