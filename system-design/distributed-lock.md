# Distributed Lock

## Redis Redlock

## Case Study

### [Uber](https://www.youtube.com/watch?v=MDuagr729aU)

Consider the follow sequence of events

1. Rider request a trip
2. Rider cancel trip
3. Driver accept

Without proper lock mechenism, step 2 and step 3 can interleve and break the state machine. 

#### Solutions

##### Ringpop

- Features
  - Consistent Hashing
  - Gossip
- Disadvantages
  - Hotspot
  - Multiple forward (hops) indicates a upper bound of size 1500 nodes
  - Network partition problem (two rings)
  - Consistency during cluster change (node goes down and comeback)

##### Distributed Lock Manager (DLM)

- Apache helix for cluster management
- Cassandra for lock persistency

## Readings

- [Distributed locks with Redis](https://redis.io/topics/distlock)
- [How to do distributed locking - Martin Kleppmann](https://martin.kleppmann.com/2016/02/08/how-to-do-distributed-locking.html)