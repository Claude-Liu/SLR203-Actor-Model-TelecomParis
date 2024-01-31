package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class TellToAndForget  {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");

		// Instantiate three actors
		final ActorRef a = system.actorOf(ActorA.createActor(), "a");
        final ActorRef b = system.actorOf(ActorB.createActor(), "b");
        final ActorRef transmitter = system.actorOf(Transmitter.createActor(), "transmitter");

        // send to a the reference of b and transmitter by message
        Message message = new Message(b, transmitter);
		a.tell(message, ActorRef.noSender());
        // tell a to start
        a.tell("start", ActorRef.noSender());
		
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

	public static void sleepFor(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static public class Message{
		public ActorRef destination;
		public ActorRef transmitter;

		public Message(ActorRef destination, ActorRef transmitter){
			this.destination = destination;
			this.transmitter = transmitter;
		}
	}
}
