package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.ActorA.MyMessage;;

public class Transmitter extends UntypedAbstractActor {
    // Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef from;
    private ActorRef to;

    public Transmitter() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Transmitter.class, () -> {
			return new Transmitter();
		});
	}

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof MyMessage){
            MyMessage mm = (MyMessage) message;
            this.from = getSender();
            this.to = mm.destination;
            log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
            this.to.tell(mm.data, this.from);
        }
    }

    
}
