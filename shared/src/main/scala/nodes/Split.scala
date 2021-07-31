package com.github.skac112.kandinsky.nodes

import cats.Monad
import cats.implicits._
import com.github.skac112.kandinsky.{EnsemblePartition, NodeFun}
import com.github.skac112.miro.{Ensemble, Graphic}

abstract class Split[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad] extends Node[GI, DI, GO, DO, M] {
  def node1: Node[GI, DI, GO, DO, M]
  def node2: Node[GI, DI, GO, DO, M]
  def splitFun(influx: Ensemble[GI, DI]): (Ensemble[GI, DI], Ensemble[GI, DI])

  override def apply(influx: Ensemble[GI, DI]) = {
    val (in_f1, in_f2) = splitFun(influx)
    for {
      out_f1 <- node1(in_f1)
      out_f2 <- node2(in_f2)
    } yield out_f1 ++ out_f2
  }
}
