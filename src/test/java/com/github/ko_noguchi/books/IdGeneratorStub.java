package com.github.ko_noguchi.books;

class IdGeneratorStub implements IdGenerator {
    private final String generate16CharacterId_returnValue;

    IdGeneratorStub(String id) {
        this.generate16CharacterId_returnValue = id;
    }

    @Override
    public String generate16CharacterId() {
        return generate16CharacterId_returnValue;
    }
}
