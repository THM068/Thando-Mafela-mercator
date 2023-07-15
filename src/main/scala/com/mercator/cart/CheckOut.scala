package com.mercator.cart
import com.mercator.model.{Product}
import com.mercator.model.CartItem

import scala.collection.mutable.ListBuffer

class CheckOut {

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
    getCartItems().map(_.totalPrice()).sum

  def clear()= this.productList.clear()


}
