package org.danielwojda.fromscratch.vanilla

import net.manub.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}
import util.IntegratedSpec

class VanillaKafkaClientsSpec extends IntegratedSpec with EmbeddedKafka {

  implicit val kafkaConfig: EmbeddedKafkaConfig = EmbeddedKafkaConfig()
  val kafkaBroker = s"localhost:${kafkaConfig.kafkaPort}"

  "Vanilla Kafka Clients" should "consume from Kafka topic, uppercase the message and produce back to output topic" in {
    withRunningKafka {
      createCustomTopic("input")
      createCustomTopic("output")

      VanillaKafkaClientsApp.start(kafkaBroker)

      publishStringMessageToKafka("input", "banana")

      val result = consumeFirstStringMessageFrom("output")

      result shouldBe "BANANA"
    }
  }

}
