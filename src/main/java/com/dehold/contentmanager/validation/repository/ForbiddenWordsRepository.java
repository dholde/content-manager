package com.dehold.contentmanager.validation.repository;

import com.dehold.contentmanager.validation.model.ForbiddenWords;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public class ForbiddenWordsRepository {

    private final JdbcTemplate jdbcTemplate;

    public ForbiddenWordsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ForbiddenWords forbiddenWords) {
        jdbcTemplate.update(
                "INSERT INTO forbidden_words (id, user_id, content_type, field_name, words) VALUES (?, ?, ?, ?, ?)",
                forbiddenWords.getId(),
                forbiddenWords.getUserId(),
                forbiddenWords.getContentType(),
                forbiddenWords.getFieldName(),
                String.join(",", forbiddenWords.getWords())
        );
    }

    public ForbiddenWords findById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM forbidden_words WHERE id = ?",
                new Object[]{id},
                new ForbiddenWordsRowMapper()
        );
    }

    public List<ForbiddenWords> findAll() {
        return jdbcTemplate.query("SELECT * FROM forbidden_words", new ForbiddenWordsRowMapper());
    }

    public void delete(ForbiddenWords forbiddenWords) {
        jdbcTemplate.update(
                "DELETE FROM forbidden_words WHERE id = ?",
                forbiddenWords.getId()
        );
    }

    public void addForbiddenWord(String word) {
        String sql = "INSERT INTO forbidden_words (word) VALUES (?)";
        jdbcTemplate.update(sql, word);
    }

    public void removeForbiddenWord(String word) {
        String sql = "DELETE FROM forbidden_words WHERE word = ?";
        jdbcTemplate.update(sql, word);
    }

    public Set<String> getAllForbiddenWords() {
        String sql = "SELECT word FROM forbidden_words";
        return Set.copyOf(jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("word")));
    }

    private static class ForbiddenWordsRowMapper implements RowMapper<ForbiddenWords> {
        @Override
        public ForbiddenWords mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ForbiddenWords(
                    UUID.fromString(rs.getString("id")),
                    UUID.fromString(rs.getString("user_id")),
                    rs.getString("content_type"),
                    rs.getString("field_name"),
                    Set.of(rs.getString("words").split(","))
            );
        }
    }
}
