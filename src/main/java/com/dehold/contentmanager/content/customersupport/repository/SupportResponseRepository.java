package com.dehold.contentmanager.content.customersupport.repository;

import com.dehold.contentmanager.content.customersupport.model.SupportResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SupportResponseRepository {
    private final JdbcTemplate jdbcTemplate;

    public SupportResponseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<SupportResponse> ROW_MAPPER = new RowMapper<>() {
        @Override
        public SupportResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SupportResponse(
                UUID.fromString(rs.getString("id")),
                rs.getString("text"),
                UUID.fromString(rs.getString("support_request")),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant()
            );
        }
    };

    public void create(SupportResponse response) {
        String sql = "INSERT INTO support_response (id, text, support_request, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, response.getId(), response.getText(), response.getSupportRequest(), response.getCreatedAt(), response.getUpdatedAt());
    }

    public Optional<SupportResponse> getById(UUID id) {
        String sql = "SELECT * FROM support_response WHERE id = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, id).stream().findFirst();
    }

    public void update(SupportResponse response) {
        String sql = "UPDATE support_response SET text = ?, support_request = ?, created_at = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, response.getText(), response.getSupportRequest(), response.getCreatedAt(), response.getUpdatedAt(), response.getId());
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM support_response WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

