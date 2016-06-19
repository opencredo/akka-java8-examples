package com.opencredo.examples.akkajava;

public class Order {
    public final Coffee.CoffeeType type;

    private Order(final Coffee.CoffeeType type) {
        this.type = type;
    }

    public static Order of(Coffee.CoffeeType type) {
        return new Order(type);
    }

    @Override
    public String toString() {
        return type.name();
    }
}
