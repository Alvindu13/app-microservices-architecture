package com.rattrapage.microserviceapi.persist.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileApp {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String summary;

    //On respecte le format attendu
    //@JsonFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdDate;


    @ContentId
    private String contentId;

    @ContentLength
    private long contentLength;


    private String mimeType = "text/plain";

    private String path;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn (name="userApp_id",referencedColumnName="id",nullable=true,unique=true)


    @ManyToOne
    @JoinColumn(name = "userApp_id")
    @Nullable
    private UserApp userApp;

}


