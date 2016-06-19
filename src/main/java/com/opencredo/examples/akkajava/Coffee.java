package com.opencredo.examples.akkajava;

public class Coffee {
    public static enum CoffeeType {
        ESPRESSO,
        AMERICANO,
        CAPPUCCINO,
        LATTE,
        DOUBLE_EXPRESSO,
        RISTRETTO,
        MOCHACCINO,
        DECAFFEINATED,
        IRISH_COFFEE
    }

    public static final CoffeeType[] types = CoffeeType.values();

    public final CoffeeType type;

    private Coffee(final CoffeeType type) {
        this.type = type;
    }

    public static Coffee ofType(CoffeeType type) {
        return new Coffee(type);
    }

    @Override
    public String toString() {
        return type.name();
    }
}
