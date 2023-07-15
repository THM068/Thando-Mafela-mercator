package com.mercator.cart

import com.mercator.model._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class CheckOutSpec extends AnyFunSuite with Matchers {
  val validProductList = List("Apple", "Apple", "Orange", "Apple")
  val invalidProductList = List("Apple", "Orange", "Mango")

  test("scanItems adds items to the checkout system") {
      val checkOutSystem = new CheckOut()
      checkOutSystem.scanItems(validProductList)

      val items = checkOutSystem.getItems()
      items.size shouldBe 4
      items(0) shouldBe Product(Apple, Price(0.6))
      items(1) shouldBe Product(Apple, Price(0.6))
      items(2) shouldBe Product(Orange, Price(0.25))
      items(3) shouldBe Product(Apple, Price(0.6))

      val cartItems = checkOutSystem.getCartItems()
      cartItems.size shouldBe 2
      cartItems.contains(CartItem(Product(Apple, Price(0.6)), 3)) shouldBe true
      cartItems.contains(CartItem(Product(Orange, Price(0.25)), 1)) shouldBe true

  }

  test("scanItems ignores items not sold in shop") {
    val checkOutSystem = new CheckOut()
    checkOutSystem.scanItems(invalidProductList)

    val items = checkOutSystem.getItems()
    items.size shouldBe 2
    items(0) shouldBe Product(Apple, Price(0.6))
    items(1) shouldBe Product(Orange, Price(0.25))

    val cartItems = checkOutSystem.getCartItems()
    cartItems.size shouldBe 2
    cartItems.contains(CartItem(Product(Apple, Price(0.6)), 1)) shouldBe true
    cartItems.contains(CartItem(Product(Orange, Price(0.25)), 1)) shouldBe true
  }

  test("get total cost of scanned items") {
    val checkOutSystem = new CheckOut()
    checkOutSystem.scanItems(validProductList)

    checkOutSystem.getTotalPrice shouldBe 2.05
  }

  test("clear checkout list") {
    val checkOutSystem = new CheckOut()
    checkOutSystem.scanItems(validProductList)

    checkOutSystem.clear()

    checkOutSystem.getItems().size shouldBe 0
    checkOutSystem.getCartItems().size shouldBe 0
    checkOutSystem.getTotalPrice() shouldBe 0

  }



}
