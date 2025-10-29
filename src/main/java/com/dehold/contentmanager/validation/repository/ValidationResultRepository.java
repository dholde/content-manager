package com.dehold.contentmanager.validation.repository;

import com.dehold.contentmanager.validation.model.ValidationError;
import com.dehold.contentmanager.validation.model.ValidationResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ValidationResultRepository {

    private final JdbcTemplate jdbcTemplate;

    public ValidationResultRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(ValidationResult validationResult) {
        jdbcTemplate.update(
                "INSERT INTO validation_result (id, content_id, content_type, is_valid, errors, created_at) VALUES (?, ?, ?, ?, ?, ?)",
                validationResult.getId(),
                validationResult.getContentId(),
                validationResult.getContentType(),
                validationResult.isValid(),
                validationResult.getErrors(),
                validationResult.getCreatedAt()
        );
    }

    private ValidationResult mapRowToValidationResult(ResultSet rs, int rowNum) throws SQLException {
        return ValidationResult.fromPersistence(
                UUID.fromString(rs.getString("id")),
                rs.getString("content_type"),
                UUID.fromString(rs.getString("content_id")),
                rs.getBoolean("is_valid"),
                (List<ValidationError>) rs.getObject("errors"),
                rs.getTimestamp("created_at").toInstant()
        );
    }
}
