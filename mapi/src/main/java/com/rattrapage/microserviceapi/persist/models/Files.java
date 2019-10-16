package com.rattrapage.microserviceapi.persist.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Files")
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Files {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String summary;

    //On respecte le format attendu
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdDate;


    @ContentId
    private String contentId;

    @ContentLength
    private long contentLength;


    private String mimeType = "text/plain";

    private String path;


    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "users_id")
    @JsonBackReference
    private Users users;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Files))
            return false;

        return
                id != null &&
                        id.equals(((Files) o).getId());
    }
    @Override
    public int hashCode() {
        return 31;
    }

}


