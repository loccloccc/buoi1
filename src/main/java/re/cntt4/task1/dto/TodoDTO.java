package re.cntt4.task1.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    @NotBlank(message = "Khong duoc de trong")
    private String content;

    @NotNull(message = "khong duoc de trong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;


    private boolean status;

    @NotBlank(message = "Khong duoc de trong")
    private String priority;
}
