package com.codetest.app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CodeTestApplication

object CodeTestApplication {
  def main(args: Array[String]): Unit =
    SpringApplication.run(classOf[CodeTestApplication], args: _*)
}
