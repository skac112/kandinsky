package com.github.skac112.kandinsky.nodes

import cats.Monad
import com.github.skac112.miro.{Ensemble, Graphic}

/**
 * Node which narrows input type of other node. Such a node could be used to match types of nodes involved in some
 * node composition scheme (function-like composing, aggregating and so on).
 */
case class PreNarrow[GI1 <: Graphic[DI1], DI1, GI2 <: Graphic[DI2], DI2, GO <: Graphic[DO], DO, M[_]: Monad](node: Node[GI1, DI1, GO, DO, M])(implicit ev: GI2 <:< GI1)
 extends Node[GI2, DI2, GO, DO, M]{
  override def apply(influx: Ensemble[GI2, DI2]) = node(influx.asInstanceOf[Ensemble[GI1, DI1]])
}
