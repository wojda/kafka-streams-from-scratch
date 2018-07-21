package org.danielwojda.fromscratch.vanilla

import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer, OffsetAndMetadata}
import org.apache.kafka.clients.producer._
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object VanillaKafkaClientsApp {

  def start(kafkaBrokers: String): Unit = {

    val consumerProperties = new Properties() {
      put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers)
      put(ConsumerConfig.GROUP_ID_CONFIG, "vanilla-consumer")
      put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
      put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    }

    val producerProperties = new Properties() {
      put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers)
      put(ProducerConfig.ACKS_CONFIG, "all")
      put(ProducerConfig.RETRIES_CONFIG, "0")
      put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
      put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    }

    val consumer = new KafkaConsumer[String, String](consumerProperties)
    consumer.subscribe(List("input").asJava)

    val producer = new KafkaProducer[String, String](producerProperties)

    Future {
      while (true) {

        val records = consumer.poll(100)

        records.asScala.foreach(record => {
          val newValue = record.value().toUpperCase
          producer.send(
            new ProducerRecord[String, String]("output", newValue),
            (metadata: RecordMetadata, exception: Exception) => {
              if (exception == null) {
                val topicPartition = new TopicPartition(metadata.topic(), metadata.partition())
                val offsetAndMetadata = new OffsetAndMetadata(metadata.offset())
                consumer.commitSync(Map(topicPartition -> offsetAndMetadata).asJava)
              } else {
                System.exit(-1) //What could possibly go wrong?
              }

            })
        })

      }
    }.onComplete(_ => producer.close())
  }
}
