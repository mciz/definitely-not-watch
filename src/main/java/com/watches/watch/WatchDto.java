package com.watches.watch;

import com.watches.watch.validation.ValidFountainMimeTypeConstraint;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@ValidFountainMimeTypeConstraint(field = "fountain")
public class WatchDto {

    private Long id;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 1, max = 255, message = "Title is either too short or too long")
    private String title;

    @NotNull(message= "Price cannot be empty")
    @Range(min = 1)
    private Integer price;

    @Size(max = 1023, message = "Description is too long")
    private String description;

    // field has validation above class declaration
    private String fountain;
}
