package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyActor extends UntypedAbstractActor {
    // Logger attached to actor
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    // list of known actors
    private ActorRef nextActor;
    private int uid;
    private int isLeader = 0;
    private int participated = 0;
    private int leaderId = -1; // invalid uid

    public MyActor(int uid) {
        this.uid = uid;
    }

    // Static function creating actor
    public static Props createActor(int uid) {
        return Props.create(MyActor.class, () -> {
            return new MyActor(uid);
        });
    }

    public int getLeaderId(){
        return this.leaderId;
    }

    public int isLeader(){
        return this.isLeader;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        // receive an actor
        if (message instanceof ActorRef) {
            nextActor = (ActorRef) message;
            log.info("[" + getSelf().path().name() + "] received actor [" + nextActor.path().name() + "]");
        }
        // recieve start message
        if (message instanceof String ) {
            String m = (String) message;
            if (m=="start") {
                log.info("[" + getSelf().path().name() + "] received start message");
                nextActor.tell(new ElectionMessage(this.uid), getSelf());
            }
        }
        // receive election message
        if (message instanceof ElectionMessage) {
            ElectionMessage m = (ElectionMessage) message;
            if (m.uid > this.uid) {
                log.info("[" + getSelf().path().name() + "] received election message from [" + getSender().path().name() + "] with uid [" + m.uid + "]");
                nextActor.tell(new ElectionMessage(m.uid), getSelf());
                this.participated = 1;
            } else if (m.uid < this.uid && this.participated == 0) {
                log.info("[" + getSelf().path().name() + "] received election message from [" + getSender().path().name() + "] with uid [" + m.uid + "]");
                nextActor.tell(new ElectionMessage(this.uid), getSelf());
                this.participated = 1;
            } else if (m.uid == this.uid) {
                log.info("[" + getSelf().path().name() + "] received election message from [" + getSender().path().name() + "] with uid [" + m.uid + "]");
                log.info("actor [" + getSelf().path().name() + "] is elected. \n\n");
                nextActor.tell(new ElectedMessage(this.uid), getSelf());
                this.participated = 0;
                this.isLeader = 1;
                this.leaderId = this.uid;
            }
        }
        // recieve elected message
        if (message instanceof ElectedMessage) {
            ElectedMessage m = (ElectedMessage) message;
            if (m.ElectedUid == this.uid) {
                log.info("[" + getSelf().path().name() + "] received elected message from [" + getSender().path().name() + "] with uid [" + m.ElectedUid + "]");
                log.info("Confirmed: actor [" + getSelf().path().name() + "] is elected. \n\n");
            } else {
                log.info("[" + getSelf().path().name() + "] received elected message from [" + getSender().path().name() + "] with uid [" + m.ElectedUid + "]");
                this.leaderId = m.ElectedUid;
                this.participated = 0;
                nextActor.tell(new ElectedMessage(m.ElectedUid), getSelf());
            }
        }
    }

    static public class ElectionMessage {
        public int uid;
        public ElectionMessage(int uid) {
            this.uid = uid;
        }
    }

    static public class ElectedMessage {
        public int ElectedUid;
        public ElectedMessage(int uid) {
            this.ElectedUid = uid;
        }
    }
}
