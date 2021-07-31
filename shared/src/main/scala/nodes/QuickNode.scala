package com.github.skac112.kandinsky.nodes

import cats.Monad
import com.github.skac112.kandinsky.NodeFun
import com.github.skac112.miro.{Ensemble, Graphic}
import Node._

case class QuickNode[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad](
                                                                                 nodeFun: NodeFun[GI, DI, GO, DO, M],
                                                                                 filterFunVal: (Ensemble[GI, DI] => Seq[Boolean]) = defaultFilterFun[GI, DI] _)
 extends Node[GI, DI, GO, DO, M] {
  override def filterFun(influx: Ensemble[GI, DI]) = filterFunVal(influx)

  override def apply(influx: Ensemble[GI, DI]): M[Ensemble[GO, DO]] = nodeFun(influx)
}
