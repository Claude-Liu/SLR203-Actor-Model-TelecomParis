# SLR203 Actor Model

It is the repository of the labs of SLR203 of Telecom Paris. It contains some exercises on actor model.

## 1. How to run the code

```
cd file_of_the_excercise (for example: 23LeaderElectionAlgorithm)
mvn exec:exec
```



## 2. some explanations

1. **Communication Topology (19CommunicationTopologyCreation)**

   Here I first send the know actors to each actor by sending ActoRref in main function.

   Then I send "Hello" Actor A, each actor will send the message to its known actors once it receives string message.

   This will show the topology of the communication graph.

   ```
    [akka://system/user/b] [b] received message from [a] with String: [Hello]
    [akka://system/user/c] [c] received message from [a] with String: [Hello]
    [akka://system/user/d] [d] received message from [b] with String: [Hello]
    [akka://system/user/a] [a] received message from [c] with String: [Hello]
    [akka://system/user/d] [d] received message from [c] with String: [Hello]
    [akka://system/user/b] [b] received message from [a] with String: [Hello]
    [akka://system/user/a] [a] received message from [d] with String: [Hello]
   ```

   

2. **Flooding (20UncontrolledFlooding)**

   Here I first send the know actors to each actor by sending ActoRref in main function.

   Then I send **"Hello"** Actor A, each actor will send the message to its known actors once it receives string message.

   We can find that each actor only send 'Hello' to its known actors once, demonstrating that there is **no infinite cycle**.

   ```
   [INFO] [01/31/2024 18:19:56.433] [system-akka.actor.default-dispatcher-2] [akka://system/user/b] [b] received message from [deadLetters] with ActorRef: [Actor[akka://system/user/d#-784162902]]
   [INFO] [01/31/2024 18:19:56.433] [system-akka.actor.default-dispatcher-4] [akka://system/user/c] [c] received message from [deadLetters] with ActorRef: [Actor[akka://system/user/d#-784162902]]
   [INFO] [01/31/2024 18:19:56.433] [system-akka.actor.default-dispatcher-5] [akka://system/user/a] [a] received message from [deadLetters] with ActorRef: [Actor[akka://system/user/b#1881781545]]
   [INFO] [01/31/2024 18:19:56.433] [system-akka.actor.default-dispatcher-3] [akka://system/user/d] [d] received message from [deadLetters] with ActorRef: [Actor[akka://system/user/e#-343919804]]
   [INFO] [01/31/2024 18:19:56.434] [system-akka.actor.default-dispatcher-5] [akka://system/user/a] [a] received message from [deadLetters] with ActorRef: [Actor[akka://system/user/c#1240454780]]
   [INFO] [01/31/2024 18:19:56.434] [system-akka.actor.default-dispatcher-5] [akka://system/user/a] [a] received message from [deadLetters] with String: [Hello]
   [INFO] [01/31/2024 18:19:56.434] [system-akka.actor.default-dispatcher-3] [akka://system/user/b] [b] received message from [a] with String: [Hello]
   [INFO] [01/31/2024 18:19:56.434] [system-akka.actor.default-dispatcher-4] [akka://system/user/c] [c] received message from [a] with String: [Hello]
   [INFO] [01/31/2024 18:19:56.434] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received message from [b] with String: [Hello]
   [INFO] [01/31/2024 18:19:56.434] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received message from [c] with String: [Hello]
   [INFO] [01/31/2024 18:19:56.434] [system-akka.actor.default-dispatcher-4] [akka://system/user/e] [e] received message from [d] with String: [Hello]
   [INFO] [01/31/2024 18:19:56.435] [system-akka.actor.default-dispatcher-4] [akka://system/user/e] [e] received message from [d] with String: [Hello]
   ```

   Then we add a known actor B to E and send **"Hello, flood is coming!"** to actor A.

   We find that all the actors are flooded. (receive **"Hello, flood is coming!"**  for more than 10 times)

   demonstrating that there is a **infinite cycle**.

   ```
   [INFO] [01/31/2024 18:20:01.443] [system-akka.actor.default-dispatcher-4] [akka://system/user/a] [a] received message from [deadLetters] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.443] [system-akka.actor.default-dispatcher-9] [akka://system/user/e] [e] received message from [deadLetters] with ActorRef: [Actor[akka://system/user/b#1881781545]]
   [INFO] [01/31/2024 18:20:01.443] [system-akka.actor.default-dispatcher-3] [akka://system/user/c] [c] received message from [a] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.443] [system-akka.actor.default-dispatcher-5] [akka://system/user/b] [b] received message from [a] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.444] [system-akka.actor.default-dispatcher-4] [akka://system/user/d] [d] received message from [c] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.444] [system-akka.actor.default-dispatcher-4] [akka://system/user/d] [d] received message from [b] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.444] [system-akka.actor.default-dispatcher-5] [akka://system/user/e] [e] received message from [d] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.445] [system-akka.actor.default-dispatcher-5] [akka://system/user/e] [e] received message from [d] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.445] [system-akka.actor.default-dispatcher-4] [akka://system/user/b] [b] received message from [e] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.445] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received message from [b] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.445] [system-akka.actor.default-dispatcher-4] [akka://system/user/b] [b] received message from [e] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.445] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received message from [b] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.446] [system-akka.actor.default-dispatcher-3] [akka://system/user/e] [e] received message from [d] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.446] [system-akka.actor.default-dispatcher-3] [akka://system/user/e] [e] received message from [d] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.446] [system-akka.actor.default-dispatcher-5] [akka://system/user/b] [b] received message from [e] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.446] [system-akka.actor.default-dispatcher-5] [akka://system/user/b] [b] received message from [e] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.446] [system-akka.actor.default-dispatcher-3] [akka://system/user/d] [d] received message from [b] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.447] [system-akka.actor.default-dispatcher-3] [akka://system/user/d] [d] received message from [b] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.447] [system-akka.actor.default-dispatcher-5] [akka://system/user/e] [e] received message from [d] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.447] [system-akka.actor.default-dispatcher-5] [akka://system/user/e] [e] received message from [d] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.447] [system-akka.actor.default-dispatcher-3] [akka://system/user/b] [b] received message from [e] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.447] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received message from [b] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.447] [system-akka.actor.default-dispatcher-3] [akka://system/user/b] [b] received message from [e] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.448] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received message from [b] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.448] [system-akka.actor.default-dispatcher-3] [akka://system/user/e] [e] received message from [d] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.448] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received too much messages, flooding...
   [INFO] [01/31/2024 18:20:01.448] [system-akka.actor.default-dispatcher-4] [akka://system/user/b] [b] received message from [e] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.449] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received message from [b] with String: [Hello, flood is coming!]
   [INFO] [01/31/2024 18:20:01.449] [system-akka.actor.default-dispatcher-5] [akka://system/user/d] [d] received too much messages, flooding...
   ```

   

3. **leader election (23LeaderElectionAlgorithm)**

   Here we implement an election algorithm proposed by  E. Chang and R. Roberts (1979) using actor model.

   ```
   when START() is received do
   (1) if (¬parti) then parti ← true; send ELECTION(idi) end if.
   
   when ELECTION(id) is received do
   (2) case (id > idi) then parti ← true; send ELECTION(id)
   (3) (id < idi) then if (¬parti) then parti ← true; send ELECTION(idi) end if
   (4) (id = idi) then send ELECTED(idi); electedi ← true
   (5) end case.
   
   when ELECTED(id) is received do
   (6) leaderi ← id; donei ← true;
   (7) if (id ≠ idi) then electedi ← false; send ELECTED(id) end if.
   ```

   In our test, we initialize 20 candidates with random id and creating a circle topology

   After that we pass the election and then elected message one by one, **updating the elected_id, leader_id, participated flag if needed**. Here is the results.

   We can find that actor-100 with the largest id is selected, which is as expected.

   **The process of passing election message:**

   ```
   [INFO] [01/31/2024 18:31:12.559] [system-akka.actor.default-dispatcher-16] [akka://system/user/actor-65] [actor-65] received start message
   [INFO] [01/31/2024 18:31:12.560] [system-akka.actor.default-dispatcher-17] [akka://system/user/actor-2] [actor-2] received election message from [actor-65] with uid [65]
   [INFO] [01/31/2024 18:31:12.560] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-35] [actor-35] received election message from [actor-2] with uid [65]
   [INFO] [01/31/2024 18:31:12.560] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-100] [actor-100] received election message from [actor-35] with uid [65]
   [INFO] [01/31/2024 18:31:12.560] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-68] [actor-68] received election message from [actor-100] with uid [100]
   [INFO] [01/31/2024 18:31:12.560] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-37] [actor-37] received election message from [actor-68] with uid [100]
   [INFO] [01/31/2024 18:31:12.560] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-5] [actor-5] received election message from [actor-37] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-72] [actor-72] received election message from [actor-5] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-44] [actor-44] received election message from [actor-72] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-80] [actor-80] received election message from [actor-44] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-16] [actor-16] received election message from [actor-80] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-17] [actor-17] received election message from [actor-16] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-18] [actor-18] received election message from [actor-17] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-51] [actor-51] received election message from [actor-18] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-23] [actor-23] received election message from [actor-51] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-56] [actor-56] received election message from [actor-23] with uid [100]
   [INFO] [01/31/2024 18:31:12.561] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-57] [actor-57] received election message from [actor-56] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-89] [actor-89] received election message from [actor-57] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-90] [actor-90] received election message from [actor-89] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-59] [actor-59] received election message from [actor-90] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-65] [actor-65] received election message from [actor-59] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-2] [actor-2] received election message from [actor-65] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-35] [actor-35] received election message from [actor-2] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-100] [actor-100] received election message from [actor-35] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-100] actor [actor-100] is elected.
   ```

   **The process of passing elected message:**

   ```
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-68] [actor-68] received elected message from [actor-100] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-37] [actor-37] received elected message from [actor-68] with uid [100]
   [INFO] [01/31/2024 18:31:12.562] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-5] [actor-5] received elected message from [actor-37] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-72] [actor-72] received elected message from [actor-5] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-44] [actor-44] received elected message from [actor-72] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-80] [actor-80] received elected message from [actor-44] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-16] [actor-16] received elected message from [actor-80] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-17] [actor-17] received elected message from [actor-16] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-18] [actor-18] received elected message from [actor-17] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-51] [actor-51] received elected message from [actor-18] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-23] [actor-23] received elected message from [actor-51] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-56] [actor-56] received elected message from [actor-23] with uid [100]
   [INFO] [01/31/2024 18:31:12.563] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-57] [actor-57] received elected message from [actor-56] with uid [100]
   [INFO] [01/31/2024 18:31:12.564] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-89] [actor-89] received elected message from [actor-57] with uid [100]
   [INFO] [01/31/2024 18:31:12.564] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-90] [actor-90] received elected message from [actor-89] with uid [100]
   [INFO] [01/31/2024 18:31:12.564] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-59] [actor-59] received elected message from [actor-90] with uid [100]
   [INFO] [01/31/2024 18:31:12.564] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-65] [actor-65] received elected message from [actor-59] with uid [100]
   [INFO] [01/31/2024 18:31:12.564] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-2] [actor-2] received elected message from [actor-65] with uid [100]
   [INFO] [01/31/2024 18:31:12.564] [system-akka.actor.default-dispatcher-20] [akka://system/user/actor-35] [actor-35] received elected message from [actor-2] with uid [100]
   [INFO] [01/31/2024 18:31:12.564] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-100] [actor-100] received elected message from [actor-35] with uid [100]
   [INFO] [01/31/2024 18:31:12.564] [system-akka.actor.default-dispatcher-7] [akka://system/user/actor-100] Confirmed: actor [actor-100] is elected.
   ```

   