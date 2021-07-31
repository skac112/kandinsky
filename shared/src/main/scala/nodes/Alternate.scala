package com.github.skac112.kandinsky.nodes

import cats.Monad
import com.github.skac112.miro.{Ensemble, Graphic}

case class Alternate[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad](node1: Node[GI, DI, GO, DO, M],
                                                                                node2: Node[GI, DI, GO, DO, M])
  extends Exploit[GI, DI, GO, DO, M] {
  override def splitFun(influx: Ensemble[GI, DI]) = internalNode.filter(influx)

  override val internalNode = NaturalSplit(node1, node2)
}
