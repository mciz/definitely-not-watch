package com.watches.watch;

import com.watches.decoder.MimeType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "watch")
public class Watch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    @NotNull(message= "Price cannot be empty")
    @Range(min = 1)
    private Integer price;

    @Size(max = 1023)
    private String description;

    @Lob
    @Column(nullable = false)
    private String fountain;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MimeType mimeType;
}
