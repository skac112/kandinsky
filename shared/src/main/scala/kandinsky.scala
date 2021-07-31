package com.github.skac112

import cats.Monad
import cats.implicits._
import com.github.skac112.kandinsky.nodes.Node
import com.github.skac112.miro.{Ensemble, Graphic}

package object kandinsky {
  type NodeFun[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]] = Ensemble[GI, DI] => M[Ensemble[GO, DO]]

  type OpenNodeFun[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, GIS <: Graphic[DIS], DIS, GOS <: Graphic[DOS], DOS, M[_]] =
    NodeFun[GIS, DIS, GOS, DOS, M] => Node[GI, DI, GO, DO, M]

  def emptyNodeFun[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, M[_]: Monad](influx: Ensemble[GI, DI]): M[Ensemble[GO, DO]] =
    implicitly[Monad[M]].pure(Nil)

  type FilterFun[GI <: Graphic[DI], DI] = Ensemble[GI, DI] => (Ensemble[GI, DI], Ensemble[GI, DI])

  type EnsemblePartition[GI <: Graphic[DI], DI] = (Ensemble[GI, DI], Ensemble[GI, DI])
}


