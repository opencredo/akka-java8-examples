package com.opencredo.examples.akkajava.simple;

import com.opencredo.examples.akkajava.Coffee;

public class CoffeeMachine {
    public Coffee prepare(Coffee.CoffeeType type) {
        return Coffee.ofType(type);
    }
}
