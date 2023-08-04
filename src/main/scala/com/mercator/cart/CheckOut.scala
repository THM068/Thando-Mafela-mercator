package com.mercator.cart
import com.mercator.model.{Apple, Banana, CartItem, Offer, Product, ProductCode}

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
    val bananaAndAppleList = cartItems.filter { item =>
      item.product.productCode == Banana || item.product.productCode == Apple
    }.sortBy {item => item.totalPrice() }

    val expensiveProduct = bananaAndAppleList.last
    val cheapList = cartItems.filterNot( item => item.product.productCode == expensiveProduct.product.productCode)


    cheapList.map { cartItem =>
      val offerOption = offers.get(cartItem.product.productCode)
      offerOption match {
        case Some(offer) =>
          val product = offer.product
          offer.discountType.applyDiscount(product.price.cost, cartItem.quantity)
        case _ =>
          cartItem.totalPrice()
      }
    }.sum
  }
}
