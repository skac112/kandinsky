package com.github.skac112.kandinsky.opennodes

import cats._
import cats.implicits._
import com.github.skac112.kandinsky._
import com.github.skac112.miro.Graphic

abstract class Node[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, GIS <: Graphic[DIS], DIS, GOS <: Graphic[DOS], DOS, M[_]: Monad]
  extends OpenNodeFun[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M] {
  def plug[GIS2 <: Graphic[DIS2], DIS2, GOS2 <: Graphic[DOS2], DOS2](slotNode: Node[GIS, DIS, GOS, DOS, GIS2, DIS2, GOS2, DOS2, M]) =
    new PlugNode[GI, DI, GO, DO, GIS, DIS, GOS, DOS, GIS2, DIS2, GOS2, DOS2, M](this, slotNode)

  def |=[GIS2 <: Graphic[DIS2], DIS2, GOS2 <: Graphic[DOS2], DOS2](slotNode: Node[GIS, DIS, GOS, DOS, GIS2, DIS2, GOS2, DOS2, M]) =
    plug _

  def alternate(other: Node[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M]) = Alternate(this, other)

  def |(other: Node[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M]) = alternate _

  def applyDefault = apply(emptyNodeFun[GIS, DIS, GOS, DOS, M])
}
