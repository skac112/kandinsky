package com.github.skac112.kandinsky.nodes

import cats.Monad
import cats.implicits._
import com.github.skac112.kandinsky.EnsemblePartition
import com.github.skac112.miro._

/**
 * Node with internal node. Input flux (influx) is split several times (controlled by value returned by 'times' method).
 * Internal node is used times + 1 times. So, for times == 1 (minimum supported value) node works exactly as
 * internalNode and actual splitting doesn't take place.
 * @param monad$M$0
 * @tparam GI
 * @tparam DI
 * @tparam GO
 * @tparam DO
 * @tparam M
 */
abstract class SplitTimes[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad] extends Node[GI, DI, GO, DO, M] {
  def internalNode: Node[GI, DI, GO, DO, M]

  def times: Int

  override def apply(influx: Ensemble[GI, DI]) = split(influx, times)

  private def split(influx: Ensemble[GI, DI], timesRemaining: Int): M[Ensemble[GO, DO]] = timesRemaining match {
    case 0 => internalNode(influx)
    case _ => {
      val (f1, f_rest) = internalNode.filter(influx)
      for {
        out_f1 <- internalNode(f1)
        out_f_rest <- split(f_rest, timesRemaining - 1)
      } yield out_f1 ++ out_f_rest
    }
  }
}
