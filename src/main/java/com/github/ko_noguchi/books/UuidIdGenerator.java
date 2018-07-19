package com.github.ko_noguchi.books;

import java.util.UUID;

public class UuidIdGenerator implements IdGenerator {
  @Override
  public String generate16CharacterId() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
  }
}
