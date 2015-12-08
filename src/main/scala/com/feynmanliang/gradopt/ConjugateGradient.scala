package com.feynmanliang.gradopt

import breeze.linalg._
import breeze.numerics._

/**
* Minimizes a function using conjugate gradient.
*/
class ConjugateGradient() {
  val opt = new Optimizer() // TODO: subclass or share lineSearch

  /**
  * Nocedal Algorithm 5.2
  * TODO: save the trace
  **/
  def minimize(
      f: Vector[Double] => Double,
      df: Vector[Double] => Vector[Double],
      x0: Vector[Double],
      reportPerf: Boolean): Vector[Double] = {
    var x = x0
    var grad = df(x)
    var p = -grad
    while (norm(grad.toDenseVector) > 1E-6) {
      val alpha = LineSearch.chooseStepSize(f, p, df, x).get // TODO: refactor algo into stream
      x = x + alpha * p
      val newGrad = df(x)
      val beta = (newGrad dot newGrad) / (grad dot grad)
      p = -newGrad + beta * p
      grad = newGrad
    }
    x
  }
}
