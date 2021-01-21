package am.learning.library.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class User {


    private long id;

    private String username;

    private String name;

    private String surname;

    private String password;


    private Status status;




    private Set<Authority> authorities;


}
