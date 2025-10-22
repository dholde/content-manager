package com.dehold.contentmanager.content.customersupport.repository;

import com.dehold.contentmanager.content.customersupport.model.CustomerRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerRequestRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomerRequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<CustomerRequest> ROW_MAPPER = new RowMapper<>() {
        @Override
        public CustomerRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerRequest(
                UUID.fromString(rs.getString("id")),
                rs.getString("text"),
                UUID.fromString(rs.getString("support_response")),
                UUID.fromString(rs.getString("customer_id")),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant()
            );
        }
    };

    public void create(CustomerRequest request) {
        String sql = "INSERT INTO customer_request (id, text, support_response, customer_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, request.getId(), request.getText(), request.getSupportResponse(), request.getCustomerId(), request.getCreatedAt(), request.getUpdatedAt());
    }

    public void update(CustomerRequest request) {
        String sql = "UPDATE customer_request SET text = ?, support_response = ?, customer_id = ?, created_at = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getText(), request.getSupportResponse(), request.getCustomerId(), request.getCreatedAt(), request.getUpdatedAt(), request.getId());
    }

    public List<CustomerRequest> findAll() {
        String sql = "SELECT * FROM customer_request";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<CustomerRequest> getById(UUID id) {
        String sql = "SELECT * FROM customer_request WHERE id = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, id).stream().findFirst();
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM customer_request WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
