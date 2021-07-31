package opennodes

import cats.Monad
import com.github.skac112.kandinsky.NodeFun
import com.github.skac112.kandinsky.opennodes.Node
import com.github.skac112.kandinsky.nodes.{Parallel => ClosedParallel}
import com.github.skac112.miro.Graphic

case class Parallel[GI <: Graphic[DI], DI, GO <: Graphic[DO], DO, GIS <: Graphic[DIS], DIS, GOS <: Graphic[DOS], DOS, M[_]: Monad]
(node1: Node[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M],
 node2: Node[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M])
  extends Node[GI, DI, GO, DO, GIS, DIS, GOS, DOS, M] {
  override def apply(nodeFun: NodeFun[GIS, DIS, GOS, DOS, M]) = ClosedParallel[GI, DI, GO, DO, M](node1(nodeFun), node2(nodeFun))
}
