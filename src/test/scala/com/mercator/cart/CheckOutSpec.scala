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

  test("checkout shows correct cost of apples when discount is applied") {
    val appleList = List("Apple", "Apple", "Apple")
    val appleOffer = Offer(Product(Apple, Price(0.6)), TwoForOne())
    val offerMap = Map[ProductCode, Offer](Apple -> appleOffer)

    val checkOut = new CheckOut(offerMap)
    checkOut.scanItems(appleList)

    checkOut.getTotalPrice() shouldBe 1.20
    checkOut.clear()

    checkOut.scanItems(List("Apple", "Apple"))
    checkOut.getTotalPrice() shouldBe 0.6
  }

  test("checkout shows correct cost of oranges when discount is applied") {
    val orangesList = List("Orange", "Orange", "Orange", "Orange", "Orange")
    val orangesOffer = Offer(Product(Orange, Price(0.25)), ThreeForTwo())
    val offerMap = Map[ProductCode, Offer](Orange -> orangesOffer)

    val checkOut = new CheckOut(offerMap)
    checkOut.scanItems(orangesList)

    checkOut.getTotalPrice() shouldBe 1.0
    checkOut.clear()

    checkOut.scanItems(List("Orange", "Orange", "Orange"))
    checkOut.getTotalPrice() shouldBe 0.5
  }

  test("checkout shows correct cost of a banana when a two for one discount is applies") {
    val bananaList = List("Banana", "Banana")
    val bananaOffer = Offer(Product(Banana, Price(0.20)), TwoForOne())
    val offerMap: Map[ProductCode, Offer] = Map(Banana -> bananaOffer)

    val checkOut = new CheckOut(offerMap)
    checkOut.scanItems(bananaList)

    checkOut.getTotalPrice() shouldBe 0.20
  }

  test("when apple and banana are bought together the cheapest one is free") {
    val fruitList = List("Banana", "Apple")

    val checkout = new CheckOut()
    checkout.scanItems(fruitList)

    checkout.getTotalPrice() shouldBe 0.2
  }


  test("checkout shows correct price when discount is applied to apples and oranges") {
    val foodList = List("Orange", "Orange", "Orange", "Orange", "Orange","Apple", "Apple", "Apple")

    val orangesOffer = Offer(Product(Orange, Price(0.25)), ThreeForTwo())
    val appleOffer = Offer(Product(Apple, Price(0.6)), TwoForOne())
    val offerMap = Map[ProductCode, Offer](Orange -> orangesOffer, Apple -> appleOffer)
    val checkOut = new CheckOut(offerMap)
    checkOut.scanItems(foodList)

    checkOut.getTotalPrice() shouldBe 2.20
  }
}
