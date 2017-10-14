package com.gochinatv.cdn.api.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by jacktomcat on 17/10/14.
 */
public class Validation {


    @NotNull(message = "not null")
    @Min(value = 5,message = "min length 5")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
