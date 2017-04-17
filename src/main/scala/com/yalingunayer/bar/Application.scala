package com.yalingunayer.bar

import akka.actor.ActorSystem

object Application {
  def main(args: Array[String]): Unit = {
    val foo = ActorSystem("Bar")
    foo.actorOf(BarActor.props())
  }
}
