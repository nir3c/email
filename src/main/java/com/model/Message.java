package com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Nir.
 */
@Data
public class Message {

    private String from;
    private String[] to;
    private String[] bcc = new String[0];
    private String[] cc = new String[0];
    private String subject = "";
    private String text = "";
    private String[] attachmentsPaths = new String[0];
    private Map<String, String> headers = new HashMap<>();
    private Date sentDate = new Date();


}
