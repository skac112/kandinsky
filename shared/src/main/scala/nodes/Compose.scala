package com.github.skac112.kandinsky.nodes

import cats.Monad
import cats.implicits._
import com.github.skac112.miro.{Ensemble, Graphic}
import com.github.skac112.vgutils.Point

/**
 * Composition of two nodes (in a quasi-functional sense). It is a Kleisli composition.
 * @param node1
 * @param node2
 * @param monad$M$0
 * @tparam G1
 * @tparam D1
 * @tparam G2
 * @tparam D2
 * @tparam G3
 * @tparam D3
 * @tparam M
 */
case class Compose[G1 <: Graphic[D1], D1, G2 <: Graphic[D2], D2, G3 <: Graphic[D3], D3, M[_]: Monad](innerNode: Node[G1, D1, G2, D2, M],
                                                                                                     outerNode: Node[G2, D2, G3, D3, M])
  extends Node[G1, D1, G3, D3, M] {
  override def apply(influx: Ensemble[G1, D1]): M[Ensemble[G3, D3]] = innerNode(influx).flatMap(outerNode.apply)

  /**
   * filterFun is same as of innerNode.
   * @param influx
   * @return
   */
  override def filterFun(influx: Ensemble[G1, D1]) = innerNode.filterFun(influx)

  //  override def filterFun(influx: Ensemble[G1, D1]): Seq[Boolean] = {
//    val influx_zip = (influx zip innerNode.filterFun(influx)) zipWithIndex
//    val (f1, f2) = influx_zip partition { elem => elem._1._2 }
//    val f1_bool = outerNode.filterFun(f1 map { elem => elem._1._1 })
//    val f1_filt = (f1 zip f1_bool) map { elem => ((elem._1._1._1, elem._2), elem._1._2)}
//    (f1_filt ++ f2).sortWith{ (elem1: (((G1, Point), Boolean), Int), elem2: (((G1, Point), Boolean), Int)) => (elem1._2 < elem2._2) } map { elem => elem._1._2}
//  }
}
