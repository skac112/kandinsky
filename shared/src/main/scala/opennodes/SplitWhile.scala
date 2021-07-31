package com.github.skac112.kandinsky.opennodes

import cats.Monad
import com.github.skac112.kandinsky.nodes.{QuickSplitWhile => ClosedQuickSplitWhile}
import com.github.skac112.kandinsky._
import com.github.skac112.miro.{Ensemble, Graphic}

/**
 * Node
 * @param monad$M$0
 * @tparam GI
 * @tparam DI
 * @tparam GO
 * @tparam DO
 * @tparam GIS
 * @tparam DIS
 * @tparam GOS
 * @tparam DOS
 * @tparam M
 */
abstract class SplitWhile[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, GIS <: Graphic[DIS], DIS, GOS <: Graphic[DOS], DOS, M[_]: Monad] extends Node[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M] {
  def internalNode: Node[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M]

  def splitFun(influx: Ensemble[GI, DI]): EnsemblePartition[GI, DI]

  def whileCond(influx: Ensemble[GI, DI]): Boolean

//  def splitFun: FilterFun[GI, DI]
//
//  def whileCond: Ensemble[GI, DI] => Boolean

  override def apply(nodeFun: NodeFun[GIS, DIS, GOS, DOS, M]) = ClosedQuickSplitWhile(internalNode(nodeFun), splitFun _, whileCond _)
}