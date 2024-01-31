package demo;

import java.util.ArrayList;
//import java.io.Serializable;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Broadcaster extends UntypedAbstractActor {
    // Logger attached to actor
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    // new a arrayList of ActorRef
    ArrayList<ActorRef> destination = new ArrayList<ActorRef>();

    public Broadcaster() {
    }

    // Static function creating actor
    public static Props createActor() {
        return Props.create(Broadcaster.class, () -> {
            return new Broadcaster();
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            String m = (String) message;
            log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name()
                    + "] with String: [" + m + "]");
            if (m.equals("JOIN")) {
                destination.add(getSender());
            } 
            else {
                for (ActorRef a : destination) {
                    if (a != getSender())
                        a.tell(m, getSender());
                }
            }
        }

    }

}
