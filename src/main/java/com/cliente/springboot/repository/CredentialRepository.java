package com.cliente.springboot.repository;

import java.io.IOException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.dto.UserForAuthDto;
import com.dto.UserForRegisterDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.cliente.springboot.util.*;;

@Repository
public class CredentialRepository {

    private static final String SQL_USER_EXISTS = "SELECT COUNT(1) FROM User WHERE Username = :Username";
    private static final String SQL_REGISTER = "INSERT INTO User (Username, PasswordHash, PasswordSalt) VALUES (:Username, :PasswordHash, :PasswordSalt)";
    private static final String SQL_LOGIN = "SELECT PasswordHash, PasswordSalt FROM User WHERE Username = :Username";

    private static final String HMAC_SHA512 = "HmacSHA512";

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public boolean userExists(final String username) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
            .addValue("Username", username);

        final Integer count = jdbcTemplate.queryForObject(SQL_USER_EXISTS, paramSource, Integer.class);
        return count != null && count > 0;
    }

    public void register(final UserForRegisterDto value) {
        byte[] salt = PasswordUtils.generateSalt(); // você pode usar SecureRandom
        byte[] hash = PasswordUtils.createPasswordHash(value.getPassword(), salt);

        final SqlParameterSource paramSource = new MapSqlParameterSource()
            .addValue("Username", value.getUsername())
            .addValue("PasswordHash", hash)
            .addValue("PasswordSalt", salt);

        jdbcTemplate.update(SQL_REGISTER, paramSource);
    }

    public boolean login(final String username, final String password) throws IOException {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
            .addValue("Username", username);

        UserForAuthDto result = jdbcTemplate.queryForObject(SQL_LOGIN, paramSource, new RowMapper<UserForAuthDto>() {
            @Override
            public UserForAuthDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserForAuthDto dto = new UserForAuthDto();
                dto.setPasswordHash(rs.getBytes("PasswordHash"));
                dto.setPasswordSalt(rs.getBytes("PasswordSalt"));
                return dto;
            }
        });

        return VerifyPasswordHash(password, result.getPasswordHash(), result.getPasswordSalt());
    }

    private boolean VerifyPasswordHash(String password, byte[] passwordHash, byte[] passwordSalt) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(passwordSalt, HMAC_SHA512);
            Mac mac = Mac.getInstance(HMAC_SHA512);
            mac.init(secretKeySpec);
            byte[] result = mac.doFinal(password.getBytes());

            if (result.length != passwordHash.length) return false;

            for (int i = 0; i < result.length; i++) {
                if (result[i] != passwordHash[i]) return false;
            }

            return true;

        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }
}
