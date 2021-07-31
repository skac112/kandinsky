package com.github.skac112.kandinsky.opennodes

import cats.Monad
import com.github.skac112.kandinsky._
import com.github.skac112.miro.Graphic

/**
 * Encapsulate pair of nodes - the mainNode and the slotNode. slotNode is "plugged" into the "slot" of the mainNode.
 * "Slot" of slotNode becomes the "slot" of the whole node.
 *
 * @param mainNode
 * @param slotNode
 * @param monad$M$0
 * @tparam GI
 * @tparam DI
 * @tparam GO
 * @tparam DO
 * @tparam GIS
 * @tparam DIS
 * @tparam GOS
 * @tparam DOS
 * @tparam GIS2
 * @tparam DIS2
 * @tparam GOS2
 * @tparam DOS2
 * @tparam M
 */
case class PlugNode[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, GIS <: Graphic[DIS], DIS, GOS <: Graphic[DOS],
  DOS, GIS2 <: Graphic[DIS2], DIS2, GOS2 <: Graphic[DOS2], DOS2, M[_]: Monad](mainNode: Node[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M],
                                                                              slotNode: Node[GIS, DIS, GOS, DOS, GIS2, DIS2, GOS2, DOS2, M])
  extends Node[GI, DI, GO, DO, GIS2, DIS2, GOS2, DOS2, M] {
  override def apply(nodeFun: NodeFun[GIS2, DIS2, GOS2, DOS2, M]) = mainNode(slotNode(nodeFun))
}