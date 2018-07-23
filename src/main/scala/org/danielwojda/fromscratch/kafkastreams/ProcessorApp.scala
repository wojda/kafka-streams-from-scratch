package org.danielwojda.fromscratch.kafkastreams

import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.kafka.streams.processor.{Processor, ProcessorContext, ProcessorSupplier}
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig, Topology}

import scala.collection.JavaConverters._

object ProcessorApp extends App {

  lazy val deserializer = new StringDeserializer()
  lazy val serializer = new StringSerializer()
  lazy val processorSupplier: ProcessorSupplier[String, String] = () => new UpperCaseProcessor

  def start(kafkaBroker: String): Unit = {

    val config = new StreamsConfig(Map(
      StreamsConfig.APPLICATION_ID_CONFIG -> "kafka-streams-processor-api",
      StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> kafkaBroker
    ).asJava)

    val topology = new Topology()
      .addSource("Source", deserializer, deserializer, "input")
      .addProcessor("Processor", processorSupplier, "Source")
      .addSink("Sink", "output", serializer, serializer, "Processor")

    new KafkaStreams(topology, config).start()
  }
}


class UpperCaseProcessor extends Processor[String, String] {
  var context: ProcessorContext = _ //screeeeeeeeam

  override def init(context: ProcessorContext): Unit = this.context = context

  override def process(key: String, value: String): Unit = {
    val newValue = value.toUpperCase
    context.forward(key, newValue)
  }

  override def punctuate(timestamp: Long): Unit = ()

  override def close(): Unit = ()
}


