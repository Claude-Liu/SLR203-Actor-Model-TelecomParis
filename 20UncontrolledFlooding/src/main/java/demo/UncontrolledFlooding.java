package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class UncontrolledFlooding {
    public static void main(String[] args) {
        // Instantiate an actor system
        final ActorSystem system = ActorSystem.create("system");

        // Instantiate actor a, b, c, d
        final ActorRef a = system.actorOf(MyActor.createActor(), "a");
        final ActorRef b = system.actorOf(MyActor.createActor(), "b");
        final ActorRef c = system.actorOf(MyActor.createActor(), "c");
        final ActorRef d = system.actorOf(MyActor.createActor(), "d");
        final ActorRef e = system.actorOf(MyActor.createActor(), "e");

        // Send the known actors to each actor
        a.tell(b, ActorRef.noSender());
        a.tell(c, ActorRef.noSender());
        b.tell(d, ActorRef.noSender());
        c.tell(d, ActorRef.noSender());
        d.tell(e, ActorRef.noSender());

        // Send one message to actor a to trigger the chain-like communication
        a.tell("Hello", ActorRef.noSender());
        try {
			waitBeforeTerminate();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} 

        // change the topology
        e.tell(b, ActorRef.noSender());
        // Send one message to actor a to trigger the chain-like communication
        a.tell("Hello, flood is coming!", ActorRef.noSender());

        // We wait 5 seconds before ending system (by default)
		// But this is not the best solution.
		try {
			waitBeforeTerminate();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			system.terminate();
		}
    }
    public static void waitBeforeTerminate() throws InterruptedException {
        Thread.sleep(5000);
    }
}
