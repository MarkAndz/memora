package com.markandz.memora;

import org.springframework.boot.SpringApplication;

public class TestMemoraApplication {

  public static void main(String[] args) {
    SpringApplication.from(MemoraApplication::main).with(TestcontainersConfiguration.class)
        .run(args);
  }

}
