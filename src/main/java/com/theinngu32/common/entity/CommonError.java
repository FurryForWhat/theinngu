package com.theinngu32.common.entity;

import com.theinngu32.common.converter.ErrorTypeConverter;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "common_errors",
uniqueConstraints = @UniqueConstraint(columnNames = {"recordId", "entityName", "fieldName", "errorType"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonError {
    public CommonError(String entityName, String fieldName, Long recordId) {
        this.entityName = entityName;
        this.fieldName = fieldName;
        this.recordId = recordId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long recordId;

    @Column
    private String invalidValue;

    @Column(nullable = false)
    private String entityName;

    @Column(nullable = false)
    private String fieldName;

    @Convert(converter = ErrorTypeConverter.class)
    @Column(name = "errorType")
    private ErrorType errorType;

}
