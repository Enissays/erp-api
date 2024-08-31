package com.est.erp_api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageUser {
    private Message message;
    private String username;
    private String email;
    private String userId;
}
