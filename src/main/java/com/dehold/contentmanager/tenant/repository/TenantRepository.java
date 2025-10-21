package com.dehold.contentmanager.tenant.repository;

import com.dehold.contentmanager.tenant.model.Tenant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TenantRepository {

    private final JdbcTemplate jdbcTemplate;

    public TenantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Tenant> TENANT_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Tenant(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("identifier"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant()
            );
        }
    };

    public void createTenant(Tenant tenant) {
        String sql = "INSERT INTO tenant (id, name, identifier, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, tenant.getId(), tenant.getName(), tenant.getIdentifier(), tenant.getCreatedAt(), tenant.getUpdatedAt());
    }

    public Optional<Tenant> getTenantById(UUID id) {
        String sql = "SELECT * FROM tenant WHERE id = ?";
        return jdbcTemplate.query(sql, TENANT_ROW_MAPPER, id).stream().findFirst();
    }

    public Optional<Tenant> getTenantByIdentifier(String identifier) {
        String sql = "SELECT * FROM tenant WHERE identifier = ?";
        return jdbcTemplate.query(sql, TENANT_ROW_MAPPER, identifier).stream().findFirst();
    }

    public void updateTenant(Tenant tenant) {
        String sql = "UPDATE tenant SET name = ?, identifier = ?, created_at = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, tenant.getName(), tenant.getIdentifier(), tenant.getCreatedAt(), tenant.getUpdatedAt(), tenant.getId());
    }

    public void deleteTenant(UUID id) {
        String sql = "DELETE FROM tenant WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
