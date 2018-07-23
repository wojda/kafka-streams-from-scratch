package util

import net.manub.embeddedkafka.EmbeddedKafka
import org.scalatest.{FlatSpec, Matchers}

abstract class IntegratedSpec extends FlatSpec with Matchers with EmbeddedKafka
