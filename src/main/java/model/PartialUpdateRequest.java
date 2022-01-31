package model;

public class PartialUpdateRequest {
    public PartialUpdateRequest(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    private String firstname;
    private String lastname;
}
