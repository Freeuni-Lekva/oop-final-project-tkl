package Objects;

import java.sql.Struct;

public class User {

    private Long id;
    private String name;
    private String password;
    private String real_name;
    private String real_lastname;
    private String image_path;
    private String description;



    public User(Long id, String name, String password,String real_name, String real_lastname, String image_path, String description){
        this.id = id;
        this.name = name;
        this.password = password;
        this.real_name = real_name;
        this.real_lastname = real_lastname;
        this.image_path = image_path;
        this.description = description;
    }

    public Long getId(){ return id; }
    public String getName(){ return name; }
    public String getPassword() { return password; }
    public String getRealName() { return real_name; }
    public String getRealLastName() { return real_lastname; }
    public String getImagePath() { return image_path; }
    public String getDescription() { return description; }
}
