package demo;

//import java.io.Serializable;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
//import javafx.scene.text.Text;

public class ActorB extends UntypedAbstractActor {

    // Logger attached to actor
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    ActorRef broadcaster;

    public ActorB() {
    }

    // Static function creating actor
    public static Props createActor() {
        return Props.create(ActorB.class, () -> {
            return new ActorB();
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ActorRef) {
            ActorRef m = (ActorRef) message;
            log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name()
                    + "] with ActorRef: [" + m + "]");
            this.broadcaster = m;
            String m2 = "JOIN";
            broadcaster.tell(m2, getSelf());
        }
        if (message instanceof String) {
            String m = (String) message;
            log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name()+ "]");
            log.info("The message is: " + m);
        }

    }

}
