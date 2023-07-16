package com.mercator.cart

import com.mercator.model.{ThreeForTwo, TwoForOne}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
class DiscountTypeSpec extends AnyFunSuite with Matchers with TableDrivenPropertyChecks {

  test("two for one discount should calculate right price") {
    val twoForOne = TwoForOne()
    val testInputs = Table(
      ("price", "quantity", "expectedCost"),
      (BigDecimal(2.5), 10, BigDecimal(12.5)),
      (BigDecimal(3), 9, BigDecimal(15.0)),
      (BigDecimal(10), 1, BigDecimal(10.0))
    )

    forAll(testInputs) { (price: BigDecimal, quantity: Int, expectedCost: BigDecimal) =>
      twoForOne.applyDiscount(price, quantity) shouldBe expectedCost
    }
  }

  test("three  for two discount should calculate right price") {
    val threeForTwo = ThreeForTwo()
    val testInputs = Table(
      ("price", "quantity", "expectedCost"),
      (BigDecimal(3), 7, BigDecimal(15)),
      (BigDecimal(3), 3, BigDecimal(6)),
      (BigDecimal(3), 10, BigDecimal(21))
    )

    forAll(testInputs) { (price: BigDecimal, quantity: Int, expectedCost: BigDecimal) =>
      threeForTwo.applyDiscount(price, quantity) shouldBe expectedCost
    }
  }

}
