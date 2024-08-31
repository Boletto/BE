package com.demoboletto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EProfile {
    Blue("Blue"),
    Green("Green"),
    Yellow("Yellow"),
    Purple("Purple");

    private final String color;
    @Override
    public String toString() {
        return color;
    }
}