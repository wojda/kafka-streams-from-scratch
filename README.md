# Kafka Streams from scratch

## Story
### Intro
1. Cumbersome consumer, tricky producer. Vanilla clients
1. New DSL - consume -> transform (map) -> produce
1. Single thread - simplicity
1. Scalability - more threads, Kafka protocol, single consumer group
1. Resiliency - Kafka protocol
1. Data loss

### Stateful operatation
1. Join unbounded data sets
1. Windowing
1. The root of all evil - state
    1. external database
    1. local state
    1. memory vs file system
    1. RocksDB
1. Local state FTW
1. Data loss
    1. don't start until restored
1. Resiliency
1. Scalability
    1. Partition everything!
1. Rekey before join

### Other Features
1. Stream or Table
1. Interactive Queries + External Api for local state
1. Processor API

### Production Readiness
1. Observability
1. Testability
    1. Limited unit testing
    1. MockedStreams
    1. Embedded Kafka
1. Deployment
    1. Continious Delivery (?)

### The good, the bad and the ugly
#### The good
1. Simplicity
1. Control
1. Community - open sourced, stackoverflow, jira, KIPs
1. Performance
1. Small code base
1. Scala Api contributed by Lightbend

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

