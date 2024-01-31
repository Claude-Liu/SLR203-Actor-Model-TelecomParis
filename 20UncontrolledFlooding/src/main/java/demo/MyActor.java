package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;

public class MyActor extends UntypedAbstractActor {

    // Logger attached to actor
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    // list of known actors
    ArrayList<ActorRef> knownActors = new ArrayList<ActorRef>();
    // count refers to the number of string messages received
    // use count to know when to stop the chain-like communication (count>=10)
    private int count = 0;

    public MyActor() {
    }

    // Static function creating actor
    public static Props createActor() {
        return Props.create(MyActor.class, () -> {
            return new MyActor();
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ActorRef) {
            ActorRef m = (ActorRef) message;
            log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name()
                    + "] with ActorRef: [" + m + "]");
            knownActors.add(m);
        }
        if (message instanceof String) {
            String m = (String) message;
            this.count++;
            log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name()
                    + "] with String: [" + m + "]");
            if (this.count >= 10) {
                log.info("[" + getSelf().path().name() + "] received too much messages, flooding...");
            }
            else{
                for (ActorRef a : knownActors) {
                    a.tell(m, getSelf());
                }
            }
        }
    }

}
