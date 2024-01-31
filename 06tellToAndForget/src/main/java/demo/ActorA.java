package demo;

import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.TellToAndForget.Message;

public class ActorA extends UntypedAbstractActor {
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef transmitter;
	private ActorRef actorB;

	public ActorA() {}
	// Static function creating actor
	public static Props createActor() {
		return Props.create(ActorA.class, () -> {
			return new ActorA();
		});
	}

	static public class MyMessage{
		public final String data;
		public final ActorRef destination;
		
		public MyMessage(String data, ActorRef destination){
			this.data = data;
			this.destination = destination;
		}
	}

	@Override
	public void onReceive(Object message) throws Throwable{
		if(message instanceof Message){
			Message mm = (Message) message;
			this.transmitter =  mm.transmitter;
			this.actorB =  mm.destination;
			log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
			log.info("Transmitter updated ! New reference is: {}", this.transmitter);
			log.info("ActorB updated ! New reference is: {}", this.actorB);
		}
		if (message instanceof String){
			String mm = (String) message;
			log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
			log.info("Message is: {}", message);
			if (mm == "start"){
				MyMessage m = new MyMessage("Hello", this.actorB);
				this.transmitter.tell(m, getSelf());
			}
		}
	}
}
