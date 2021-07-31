package com.github.skac112.kandinsky.nodes

import cats.Monad
import cats.implicits._
import com.github.skac112.miro.{Ensemble, Graphic}

case class Parallel[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad](node1: Node[GI, DI, GO, DO, M],
                                                                                   node2: Node[GI, DI, GO, DO, M])
  extends Node[GI, DI, GO, DO, M] {

  lazy val expNode1 = NaturalExploit(node1)
  lazy val expNode2 = NaturalExploit(node2)

  override def filterFun(influx: Ensemble[GI, DI]): Seq[Boolean] = (node1.filterFun(influx) zip node2.filterFun(influx)) map
    { elem => elem._1 || elem._2 }

//  override def filter(influx: Ensemble[GI, DI]) = internalNode.filter(influx)

  override def apply(influx: Ensemble[GI, DI]): M[Ensemble[GO, DO]] = for {
    out_f1 <- expNode1(influx)
    out_f2 <- expNode2(influx)
  } yield out_f1 ++ out_f2
}
