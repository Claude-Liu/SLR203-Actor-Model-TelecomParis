package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class BroadcastRoundRobin {
    public static void main(String[] args) {
        // Instantiate an actor system
        final ActorSystem system = ActorSystem.create("system");

        // Instantiate actor a, b and transmitter
        final ActorRef a = system.actorOf(ActorA.createActor(), "a");
        final ActorRef b = system.actorOf(ActorB.createActor(), "b");
        final ActorRef c = system.actorOf(ActorB.createActor(), "c");
        final ActorRef broadcaster = system.actorOf(Broadcaster.createActor(), "broadcaster");

        a.tell(broadcaster, ActorRef.noSender());
        b.tell(broadcaster, ActorRef.noSender());
        c.tell(broadcaster, ActorRef.noSender());
        
        // We wait 5 seconds before ending system (by default)
		// But this is not the best solution.
		try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
    }
    public static void waitBeforeTerminate() throws InterruptedException {
        Thread.sleep(5000);
    }

    
}