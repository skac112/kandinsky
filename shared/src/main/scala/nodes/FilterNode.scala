package com.github.skac112.kandinsky.nodes

import cats.Monad
import cats.implicits._
import com.github.skac112.kandinsky._
import com.github.skac112.miro.{Ensemble, Graphic, PosGraphic}

/**
 * Node which only filters input, not modifying graphics in any way. Output type is possible narrower than input type.
 * CAUTION: Filtering method which defines operation of this node is 'filterToOut()', not 'filter()' - the latter is
 * defined by Node class and overriden by this class.
 * @param monad$M$0
 * @param ev
 * @tparam GI
 * @tparam DI
 * @tparam GO
 * @tparam DO
 * @tparam M
 */
abstract class FilterNode[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad](implicit ev: GO <:< GI) extends Node[GI, DI, GO, DO, M] {

  def filterToOut(g: PosGraphic[GI, DI]): Boolean

  override def filterFun(influx: Ensemble[GI, DI]): Seq[Boolean] = influx map filterToOut _

  def filterToOutAll(influx: Ensemble[GI, DI]): (Ensemble[GO, DO], Ensemble[GI, DI]) = {
    val (filt_pos, filt_neg) = filter(influx)
    (filt_pos.asInstanceOf[Seq[PosGraphic[GO, DO]]], filt_neg)
  }

  override def apply(influx: Ensemble[GI, DI]) = implicitly[Monad[M]].pure(filterToOutAll(influx)._1)
}