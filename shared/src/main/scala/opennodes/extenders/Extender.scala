package com.github.skac112.kandinsky.opennodes.extenders

import cats.Monad
import cats.implicits._
import com.github.skac112.kandinsky.NodeFun
import com.github.skac112.miro.{Ensemble, Graphic}
import com.github.skac112.kandinsky.nodes.{QuickNode, Node => ClosedNode}
import com.github.skac112.kandinsky.opennodes.Node

abstract class Extender[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, GIS <: Graphic[DIS], DIS, M[_]: Monad]
  extends Node[GI, DI, GO, DO, GIS, DIS, GO, DO, M] {
  def internalNode: ClosedNode[GI, DI, GIS, DIS, M]

  override def apply(nodeFun: NodeFun[GIS, DIS, GO, DO, M]): ClosedNode[GI, DI, GO, DO, M] = internalNode + QuickNode(nodeFun)
}
