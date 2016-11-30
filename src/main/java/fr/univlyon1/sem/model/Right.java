package fr.univlyon1.sem.model;

import fr.univlyon1.sem.enumeration.RoleEnum;

public class Right {

    RoleEnum role;
    String iriResource;

    public Right(RoleEnum role, String iriResource) {
        this.role = role;
        this.iriResource = iriResource;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getIriResource() {
        return iriResource;
    }

    public void setIriResource(String iriResource) {
        this.iriResource = iriResource;
    }
}
