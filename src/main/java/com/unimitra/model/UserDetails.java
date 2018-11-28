package com.unimitra.model;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDetails {

	private String firstName;
	private String lastName;
	private Date dateBirth;

}
