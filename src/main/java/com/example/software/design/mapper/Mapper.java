package com.example.software.design.mapper;

public interface Mapper<R, W, E>{
    R mapRead(E from);
    E mapEntity(W from);
}
