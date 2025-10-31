package com.dehold.contentmanager.validation.repository;

import com.dehold.contentmanager.validation.model.ForbiddenWords;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ForbiddenWordsRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ForbiddenWordsRowMapper FORBIDDEN_WORDS_ROW_MAPPER = new ForbiddenWordsRowMapper();

    public ForbiddenWordsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ForbiddenWords forbiddenWords) {
        jdbcTemplate.update(
                "INSERT INTO forbidden_words (id, user_id, description, content_type, field_name, words) VALUES (?, ?, ?, ?, ?, ?)",
                forbiddenWords.getId(),
                forbiddenWords.getUserId(),
                forbiddenWords.getDescription(),
                forbiddenWords.getContentType(),
                forbiddenWords.getFieldName(),
                String.join(",", forbiddenWords.getWords())
        );
    }

    public Optional<ForbiddenWords> findById(UUID id) {
        String sql = "SELECT * FROM forbidden_words WHERE id = ?";
        return jdbcTemplate.query(sql, FORBIDDEN_WORDS_ROW_MAPPER, id).stream().findFirst();
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

    private static class ForbiddenWordsRowMapper implements RowMapper<ForbiddenWords> {
        @Override
        public ForbiddenWords mapRow(ResultSet rs, int rowNum) throws SQLException {
            String wordsAsString = rs.getString("words");
            LinkedHashSet<String> words = new LinkedHashSet<String>(Arrays.asList(wordsAsString.split(",")));
            return new ForbiddenWords(
                    UUID.fromString(rs.getString("id")),
                    UUID.fromString(rs.getString("user_id")),
                    rs.getString("description"),
                    rs.getString("content_type"),
                    rs.getString("field_name"),
                    words
            );
        }
    }
}
