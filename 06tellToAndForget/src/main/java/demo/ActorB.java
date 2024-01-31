package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ActorB extends UntypedAbstractActor {
    public ActorB() {}
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Static function creating actor
	public static Props createActor() {
		return Props.create(ActorB.class, () -> {
			return new ActorB();
		});
	}

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
            log.info("Message is: {}", message);
            ActorRef sender = getSender();
            sender.tell("Hi", getSelf());
        }
    }


}
