package com.github.skac112.kandinsky.nodes

import cats.Monad
import com.github.skac112.kandinsky.nodes.Node.defaultFilterFun
import com.github.skac112.kandinsky.{EnsemblePartition, NodeFun}
import com.github.skac112.miro.{Ensemble, Graphic}

object Node {
  def defaultFilterFun[GI <: Graphic[DI], DI](influx: Ensemble[GI, DI]): Seq[Boolean] = influx.size match {
    case 0 => Seq()
    case size => true +: Seq.fill(size - 1)(false)
  }
}

abstract class Node[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad] extends NodeFun[GI, DI, GO, DO, M] {
  /**
   * Filter function. Specifies which graphic elements this function 'would like' process where it 'had a choice'.
   * Such a situation takes place for example, in alternate node where each of 2 nodes embedded process some part of
   * input flux. Default implementation positively filters the first element only.
   * @param g
   * @return
   */
  def filterFun(influx: Ensemble[GI, DI]): Seq[Boolean] = defaultFilterFun[GI, DI](influx)

  /**
   * Filters - i.e. partitions influx in two parts - of elements positively and negatively filtered by filterFun.
   * @param influx
   * @return
   */
  def filter(influx: Ensemble[GI, DI]): EnsemblePartition[GI, DI] = influx.size match {
    case 0 => (Nil, Nil)
    case _ => {
      val zipped_split = (influx zip filterFun(influx)).partition(kv => kv._2)
      (zipped_split._1 map {kv => kv._1}, zipped_split._2 map {kv => kv._1})
    }
  }

  /**
   * Composes this node with other. This node is inner (applied first).
   * @param other
   */
  def compose[GO2 <: Graphic[DO2], DO2](other: Node[GO, DO, GO2, DO2, M]) = Compose(this, other)

  def +[GO2 <: Graphic[DO2], DO2] = compose[GO2, DO2] _


}
