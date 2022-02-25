package com.igroupsolution.prueba.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse<D> {

    private int total;
    private boolean hasMore;
    private List<D> data;

    @Override
    public String toString() {
        return "ListResponse{" +
                "total=" + total +
                ", hasMore=" + hasMore +
                ", data=" + data +
                '}';
    }
}
