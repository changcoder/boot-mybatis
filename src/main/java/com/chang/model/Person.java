package com.chang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private int age;
    private String address;
}
