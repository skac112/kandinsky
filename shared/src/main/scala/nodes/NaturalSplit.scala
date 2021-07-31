package com.github.skac112.kandinsky.nodes

import cats.Monad
import com.github.skac112.miro.{Ensemble, Graphic}
import com.github.skac112.vgutils.Point

/**
 * Node which partitions input flux between two nodes - i.e. each graphic element is processed by one and only one
 * node (node1 or node2). Partition is done based on filtering functions (filterFun) of these nodes.
 * @param node1
 * @param node2
 * @param monad$M$0
 * @tparam GI
 * @tparam DI
 * @tparam GO
 * @tparam DO
 * @tparam M
 */
case class NaturalSplit[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad](
                                                                                    override val node1: Node[GI, DI, GO, DO, M], override val node2: Node[GI, DI, GO, DO, M])
  extends Split[GI, DI, GO, DO, M] {
  override def splitFun(influx: Ensemble[GI, DI]) = node1.filter(influx)

  override def filterFun(influx: Ensemble[GI, DI]): Seq[Boolean] = {
    val influx_zip = (influx zip node1.filterFun(influx)) zipWithIndex
    val (f1, f2) = influx_zip partition { elem => elem._1._2 }
    val f2_bool = node2.filterFun(f2 map { elem => elem._1._1 })
    val f2_filt = (f2 zip f2_bool) map { elem => ((elem._1._1._1, elem._2), elem._1._2)}
    (f1 ++ f2_filt).sortWith{ (elem1: (((GI, Point), Boolean), Int), elem2: (((GI, Point), Boolean), Int)) => (elem1._2 < elem2._2) } map { elem => elem._1._2}
  }
}
