package com.mercator.cart
import com.mercator.model.{CartItem, Offer, Product, ProductCode}

import scala.collection.mutable.ListBuffer

class CheckOut(offers: Map[ProductCode, Offer] = Map()) extends Discount {

  private var productList: ListBuffer[Product] = new ListBuffer[Product]()

  def getItems(): Seq[Product] = productList.toList

  def scanItems(validProductList: Seq[String]): Unit = {
    productList.addAll(
    validProductList.map(CreateProduct.makeProduct(_)).collect {
      case Some(product: Product) => product
    })
  }

  def getCartItems(): Seq[CartItem] =
     getItems().groupBy(identity).collect {
      case (id, key) => CartItem(id, key.length)
    }.toList

  def getTotalPrice(): BigDecimal =
    calculatePrice(getCartItems(), offers)

  def clear()= this.productList.clear()

}

trait Discount {
  def calculatePrice(cartItems: Seq[CartItem], offers: Map[ProductCode, Offer]): BigDecimal = {
    cartItems.map { cartItem =>
      val offerOption = offers.get(cartItem.product.productCode)
      if(offerOption.nonEmpty) {
          val offer = offerOption.get
          val product = offer.product
          offer.discountType.applyDiscount(product.price.cost, cartItem.quantity)
      }
      else cartItem.totalPrice()
    }.sum
  }
}
