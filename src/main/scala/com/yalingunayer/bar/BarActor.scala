package com.yalingunayer.bar

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props

object BarActor {
  def props(): Props = Props(classOf[BarActor])
}

class BarActor extends Actor {
  def receive = {
    case s: String => println(f"Received a reply: $s")
  }
  
  context.system.actorSelection("akka.tcp://Foo@remote-foo:47000/user/foo").resolveOne()(10.seconds).onComplete(x => x match {
    case Success(ref: ActorRef) => {
      println(f"Located Foo actor: $ref")
      ref ! "Oh, hi Mark!"
      ref ! "Hey, Johnny!"
    }
    case Failure(t) => {
      System.err.println(f"Failed to locate Foo actor. Reason: $t")
      context.system.terminate()
    }
  })
}
