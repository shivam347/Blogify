package com.example.SpringStarter.Util.Constants;

public enum Privileges {

    RESET_USER_PASSWORD(1L, "RESET_USER_PASSWORD"),
    ACCES_ADMIN_PANEL(2L, "ACCESS_ADMIN_PANEL");

    private Long authorityId;
    private String authorityName;

    private Privileges(Long authorityId, String authorityName){
        this.authorityId = authorityId;
        this.authorityName = authorityName;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }   
    
}
