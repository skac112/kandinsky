package com.github.skac112.kandinsky.nodes

import cats.Monad
import cats.implicits._
import com.github.skac112.kandinsky.FilterFun
import com.github.skac112.miro.{Ensemble, Graphic}

case class QuickSplitWhile[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad](override val internalNode: Node[GI, DI, GO, DO, M],
                                                                                      val aSplitFun: (Ensemble[GI, DI]) => (Ensemble[GI, DI], Ensemble[GI, DI]),
                                                                                      val aWhileCond: (Ensemble[GI, DI]) => Boolean) extends SplitWhile[GI, DI, GO, DO, M] {
  override def splitFun(influx: Ensemble[GI, DI]): (Ensemble[GI, DI], Ensemble[GI, DI]) = aSplitFun(influx)

  override def whileCond(influx: Ensemble[GI, DI]): Boolean = aWhileCond(influx)
}
