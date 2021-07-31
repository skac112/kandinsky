package com.github.skac112.kandinsky.nodes

import cats.Monad
import cats.implicits._
import com.github.skac112.kandinsky.{EnsemblePartition, FilterFun}
import com.github.skac112.miro.{Ensemble, Graphic}

/**
 * Node with internal node. Influx is part by part processed by internal node as long as condition on remaining input
 * flux is satisfied. Internal node is used at least once. If condition is not satisfied, remaining flux is processed
 * by internal node without more splitting. If condition is satisfied, remaining flux is splitted by splitFun,
 * the first part is processed by internalNode and becomes part of output and the second part is recurrently processed
 * by apply method.
 *
 * @param monad$M$0
 * @tparam GI
 * @tparam DI
 * @tparam GO
 * @tparam DO
 * @tparam M
 */
abstract class SplitWhile[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad] extends Node[GI, DI, GO, DO, M] {
  def internalNode: Node[GI, DI, GO, DO, M]

  def splitFun(influx: Ensemble[GI, DI]): EnsemblePartition[GI, DI]

  def whileCond(influx: Ensemble[GI, DI]): Boolean

  override def apply(influx: Ensemble[GI, DI]) = if (whileCond(influx)) {
    val (node_flux, remaining_flux) = splitFun(influx)
    for {
      out_f1 <- internalNode(node_flux)
      out_f2 <- apply(remaining_flux)
    } yield out_f1 ++ out_f2
  } else {
    internalNode(influx)
  }
}