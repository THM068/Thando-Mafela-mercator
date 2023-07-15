package com.mercator.cart

import com.mercator.model.{CartItem, Orange, Price, Product}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class CartItemSpec extends AnyFunSuite with Matchers {

  test("Calculate correct product prices") {
    val orange = Product(Orange, Price(0.25))
    val quantity = 5
    val orangesCartItem = CartItem(orange, quantity)

    orangesCartItem.totalPrice() shouldBe 1.25
  }
}
