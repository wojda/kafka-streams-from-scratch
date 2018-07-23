package org.danielwojda.fromscratch.kafkastreams

import net.manub.embeddedkafka.EmbeddedKafkaConfig
import util.IntegratedSpec

class ProcessorSpec extends IntegratedSpec {

  implicit val kafkaConfig: EmbeddedKafkaConfig = EmbeddedKafkaConfig()
  val kafkaBroker = s"localhost:${kafkaConfig.kafkaPort}"

  "Kafka Streams Processor" should "consume from Kafka topic, uppercase the message and produce back to output topic" in {
    withRunningKafka {
      createCustomTopic("input")
      createCustomTopic("output")

      ProcessorApp.start(kafkaBroker)

      publishStringMessageToKafka("input", "banana")

      val result = consumeFirstStringMessageFrom("output")

      result shouldBe "BANANA"
    }
  }

}
