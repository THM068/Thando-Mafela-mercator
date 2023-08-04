package com.mercator.cart

import com.mercator.model.{Apple, Banana, Orange, Price, Product}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class CreateProductSpec extends AnyFunSuite with Matchers {

  test("Correct product string returns correct Product") {
      CreateProduct.makeProduct("apple") shouldBe Some(Product(Apple, Price(0.6)))
      CreateProduct.makeProduct("Orange") shouldBe Some(Product(Orange, Price(0.25)))
      CreateProduct.makeProduct("Banana") shouldBe Some(Product(Banana, Price(0.20)))
  }

  test("Incorrect product string returns a non object") {
    CreateProduct.makeProduct("pineappble") shouldBe None
  }


}
