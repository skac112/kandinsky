package com.github.skac112.kandinsky.nodes

import cats.Monad
import cats.implicits._
import com.github.skac112.miro.{Ensemble, Graphic}
/**
 * Node which widens output type of other node. Such a node could be used to match types of nodes involved in some
 * node composition scheme (function-like composing, aggregating and so on).
 */
case class PostWiden[GI <: Graphic[DI], DI, GO1 <: Graphic[DO1], DO1, GO2 <: Graphic[DO2], DO2, M[_]: Monad](node: Node[GI, DI, GO1, DO1, M])(implicit ev: GO1 <:< GO2)
 extends Node[GI, DI, GO2, DO2, M]{
//  override def apply(influx: Ensemble[GI, DI]) = node(influx).flatMap[Ensemble[GO2, DO2]]((ens: Ensemble[GO1, DO1]) => implicitly[Monad[M]].pure(ens.asInstanceOf[Ensemble[GO2, DO2]]))
  override def apply(influx: Ensemble[GI, DI]) = node(influx).flatMap[Ensemble[GO2, DO2]]((ens: Ensemble[GO1, DO1]) => implicitly[Monad[M]].pure(ens.asInstanceOf[Ensemble[GO2, DO2]]))
}
