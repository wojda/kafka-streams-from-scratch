package org.danielwojda.fromscratch.kafkastreams

import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.kafka.streams.processor.{Processor, ProcessorContext, ProcessorSupplier}
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig, Topology}
import scala.collection.JavaConverters._

object ProcessorApp extends App {

  def start(kafkaBroker: String): Unit = {
    val config = new StreamsConfig(Map(
      StreamsConfig.APPLICATION_ID_CONFIG -> "kafka-streams-processor-api",
      StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> kafkaBroker
    ).asJava)

    val processorSupplier: ProcessorSupplier[String, String] = () => new UpperCaseProcessor

    val topology = new Topology()
      .addSource("Source", new StringDeserializer(), new StringDeserializer(), "input")
      .addProcessor("Processor", processorSupplier, "Source")
      .addSink("Sink", "output", new StringSerializer(), new StringSerializer(), "Processor")

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


