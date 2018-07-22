# Kafka Streams from scratch

## Story

### Intro
1. Cumbersome consumer, tricky producer. Vanilla clients
1. "[Add processor client](https://cwiki.apache.org/confluence/display/KAFKA/KIP-28+-+Add+a+processor+client)" - first Kafka Streams KIP (Kafka Improvment Process)

### New DSL
1. Provides higher-level operations on the data
1. New DSL - consume -> transform (map) -> produce
1. Single thread - simplicity
1. Scalability - more threads, Kafka protocol, single consumer group
1. Resiliency, Fault Tolerance - Kafka protocol
1. At least once delivery guarantee

### Stateful operatation
1. Join unbounded data sets
1. The root of all evil - state
    1. external database
    1. local state
    1. memory vs file system
    1. RocksDB
1. Local state FTW
    1. Logging - Kafka topic as a backup
    1. `Standby replicas` - reduce time of fail-over 
1. Windowing
1. At least once delivery guarantee
    1. don't start until restored
1. Resiliency, Fault Tolerance
1. Scalability
    1. Partition everything!
    
### Dark side of Kafka Scalability
1. Rekey before join
1. More operations - more^2 topics

### Data Enrichment
1. Source of enrichments - kafka topic
1. KTable
    1. Deploying new instance - don't start until restored
1. Global KTable
1. Source of enrichments - database, http service
1. Async calls in Kafka Streams
    1. Async calls == Blocking calls
    1. At least once delivery guarantee
    1. Failure tolerance

### Other Features
1. Stream or Table
1. Interactive Queries + External Api for local state
1. Processor API
1. Exactly-Once Semantic (*)
1. Event Time Processing

### Production Readiness
1. Observability
1. Testability
    1. Limited unit testing
    1. MockedStreams
    1. Embedded Kafka
1. Deployment
    1. Eventual Readiness - don't start until restored
    1. Continious Delivery (?)

### The good, the bad and the ugly
#### The good
1. Simplicity
1. Control
1. Community - open sourced, stackoverflow, jira, KIPs
1. Performance
1. Small code base
1. Scala Api contributed by Lightbend
1. Out of the box metrics
1. At least once delivery guarantee
1. Exactly once delivery guarantee (*)

#### The Bad
1. Lack of obserbability around join operations
1. Cost of `rekeying`
1. frameworkish library 
1. kafka-to-kafka only 
    1. Possible solution - [Kafka-Connect](https://www.confluent.io/product/connectors/)
1. Implemented in Java 6 mindset
    1. Ubiquitous `null`
    1. Painful to contribute if you know Scala or Java 8

#### The Ugly
1. Async calls - blocking calls
    1. naive implementation
    1. Akka Streams as a perfect solution
1. Maintainability 
    1. [changing topology is a breaking change](https://stackoverflow.com/a/48119828)
    1. [Restore topic names contains operation number](https://issues.apache.org/jira/browse/KAFKA-6273)
    
## Q&A
Q: Why bother about Kafka Streams when Spark exists?
A: "Library vs Framework" + "Unix design principle of many small programs working together"



## Materials
1. [Kafka Consumer Javadoc](https://kafka.apache.org/10/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html)
1. [Kafka Producer Javadoc](https://kafka.apache.org/10/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html)
1. [Book - Kafka Streams in Action](https://www.manning.com/books/kafka-streams-in-action)
1. [Event sourcing using kafka](https://blog.softwaremill.com/event-sourcing-using-kafka-53dfd72ad45d)

