package com.mercator.cart

import com.mercator.model.{Apple, Orange, Price, Product}

object CreateProduct {

  def makeProduct(item: String): Option[Product] = item match {
    case convertToProduct(product) => Option(product)
    case _ => None
  }
}

object convertToProduct {

  def unapply(product: String): Option[Product] = {
    product.toUpperCase match {
      case "APPLE" => Some(Product(Apple, Price(0.6)))
      case "ORANGE" => Some(Product(Orange, Price(0.25)))
      case _ => None
    }
  }
}
