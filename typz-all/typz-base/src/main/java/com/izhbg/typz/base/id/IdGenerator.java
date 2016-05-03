package com.izhbg.typz.base.id;

public interface IdGenerator {
    long generateId();

    long generateId(String name);

    long generateId(Class<?> clz);
}
