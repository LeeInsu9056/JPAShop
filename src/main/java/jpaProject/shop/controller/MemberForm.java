package jpaProject.shop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "Member name is required")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
