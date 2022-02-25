package com.igroupsolution.prueba.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponse<T> {

    public String message;
    public T data;
}
