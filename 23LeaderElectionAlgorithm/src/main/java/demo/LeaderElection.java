package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LeaderElection {

    public static void main(String[] args) {
        // Instantiate an actor system
        final ActorSystem system = ActorSystem.create("system");

        // Instantiate 20 actors with random and distinct uids
        final int numActors = 20;
        final ActorRef[] actors = new ActorRef[numActors];
        final Set<Integer> uniqueIDs = generateRandomUniqueIDs(numActors, 0, 100);
        int i = 0;
        for (int uid: uniqueIDs) {
            actors[i] = system.actorOf(MyActor.createActor(uid), "actor-" + uid);
            i++;
        }
        // print the actors
        for (int j = 0; j < actors.length; j++) {
            System.out.println(actors[j].path().name());
        }

        // generate an unidirectional ring of actors
        for (int j = 0; j < actors.length; j++) {
            int nextIndex = (j + 1) % actors.length;
            actors[j].tell(actors[nextIndex], ActorRef.noSender());
        }
        // choosing a random actor and start the leader election 
        Random random = new Random();
        int randomIndex = random.nextInt(actors.length);
        actors[randomIndex].tell("start", ActorRef.noSender());

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

    private static Set<Integer> generateRandomUniqueIDs(int count,int minValue,int maxValue) {
        if (count > (maxValue - minValue + 1)) {
            throw new IllegalArgumentException("Cannot generate more unique IDs than the range allows.");
        }

        Set<Integer> uniqueIDs = new HashSet<>();
        Random random = new Random();

        while (uniqueIDs.size() < count) {
            int randomUID = random.nextInt(maxValue - minValue + 1) + minValue;
            uniqueIDs.add(randomUID);
        }

        return uniqueIDs;
    }
}
