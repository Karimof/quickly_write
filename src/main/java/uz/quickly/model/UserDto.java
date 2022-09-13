package uz.quickly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private File file;
    private String fullName;
    private String userName;
    private String email;
    private String password;
    private String checkPassword;
}
