package com.eoffice.app.repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.eoffice.app.domain.User;

import java.time.ZonedDateTime;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cassandra repository for the User entity.
 */
@Repository
public class UserRepository {

    private final Session session;

    private Mapper<User> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement findOneByActivationKeyStmt;

    private PreparedStatement findOneByResetKeyStmt;

    private PreparedStatement insertByActivationKeyStmt;

    private PreparedStatement insertByResetKeyStmt;

    private PreparedStatement deleteByActivationKeyStmt;

    private PreparedStatement deleteByResetKeyStmt;

    private PreparedStatement findOneByLoginStmt;

    private PreparedStatement insertByLoginStmt;

    private PreparedStatement deleteByLoginStmt;

    private PreparedStatement findOneByEmailStmt;

    private PreparedStatement insertByEmailStmt;

    private PreparedStatement deleteByEmailStmt;

    public UserRepository(Session session) {
        this.session = session;
        mapper = new MappingManager(session).mapper(User.class);

        findAllStmt = session.prepare("SELECT * FROM user");

        findOneByActivationKeyStmt = session.prepare(
            "SELECT id " +
            "FROM user_by_activation_key " +
            "WHERE activation_key = :activation_key");

        findOneByResetKeyStmt = session.prepare(
            "SELECT id " +
            "FROM user_by_reset_key " +
            "WHERE reset_key = :reset_key");

        insertByActivationKeyStmt = session.prepare(
            "INSERT INTO user_by_activation_key (activation_key, id) " +
            "VALUES (:activation_key, :id)");

        insertByResetKeyStmt = session.prepare(
            "INSERT INTO user_by_reset_key (reset_key, id) " +
            "VALUES (:reset_key, :id)");

        deleteByActivationKeyStmt = session.prepare(
            "DELETE FROM user_by_activation_key " +
            "WHERE activation_key = :activation_key");

        deleteByResetKeyStmt = session.prepare(
            "DELETE FROM user_by_reset_key " +
            "WHERE reset_key = :reset_key");

        findOneByLoginStmt = session.prepare(
            "SELECT id " +
            "FROM user_by_login " +
            "WHERE login = :login");

        insertByLoginStmt = session.prepare(
            "INSERT INTO user_by_login (login, id) " +
                "VALUES (:login, :id)");

        deleteByLoginStmt = session.prepare(
            "DELETE FROM user_by_login " +
                "WHERE login = :login");

        findOneByEmailStmt = session.prepare(
            "SELECT id " +
            "FROM user_by_email " +
            "WHERE email     = :email");

        insertByEmailStmt = session.prepare(
            "INSERT INTO user_by_email (email, id) " +
                "VALUES (:email, :id)");

        deleteByEmailStmt = session.prepare(
            "DELETE FROM user_by_email " +
                "WHERE email = :email");
    }

    public User findOne(String id) {
        return mapper.get(id);
    }

    public Optional<User> findOneByActivationKey(String activationKey) {
        BoundStatement stmt = findOneByActivationKeyStmt.bind();
        stmt.setString("activation_key", activationKey);
        return findOneFromIndex(stmt);
    }

    public Optional<User> findOneByResetKey(String resetKey) {
        BoundStatement stmt = findOneByResetKeyStmt.bind();
        stmt.setString("reset_key", resetKey);
        return findOneFromIndex(stmt);
    }

    public Optional<User> findOneByEmail(String email) {
        BoundStatement stmt = findOneByEmailStmt.bind();
        stmt.setString("email", email);
        return findOneFromIndex(stmt);
    }

    public Optional<User> findOneByLogin(String login) {
        BoundStatement stmt = findOneByLoginStmt.bind();
        stmt.setString("login", login);
        return findOneFromIndex(stmt);
    }

    public List<User> findAll() {
        return mapper.map(session.execute(findAllStmt.bind())).all();
    }

    public User save(User user) {
        User oldUser = mapper.get(user.getId());
        if (oldUser != null) {
            if (!StringUtils.isEmpty(oldUser.getActivationKey()) && !oldUser.getActivationKey().equals(user.getActivationKey())) {
                session.execute(deleteByActivationKeyStmt.bind().setString("activation_key", oldUser.getActivationKey()));
            }
            if (!StringUtils.isEmpty(oldUser.getResetKey()) && !oldUser.getResetKey().equals(user.getResetKey())) {
                session.execute(deleteByResetKeyStmt.bind().setString("reset_key", oldUser.getResetKey()));
            }
            if (!StringUtils.isEmpty(oldUser.getLogin()) && !oldUser.getLogin().equals(user.getLogin())) {
                session.execute(deleteByLoginStmt.bind().setString("login", oldUser.getLogin()));
            }
            if (!StringUtils.isEmpty(oldUser.getEmail()) && !oldUser.getEmail().equals(user.getEmail())) {
                session.execute(deleteByEmailStmt.bind().setString("email", oldUser.getEmail()));
            }
        }
        BatchStatement batch = new BatchStatement();
        batch.add(mapper.saveQuery(user));
        if (!StringUtils.isEmpty(user.getActivationKey())) {
            batch.add(insertByActivationKeyStmt.bind()
                .setString("activation_key", user.getActivationKey())
                .setString("id", user.getId()));
        }
        if (!StringUtils.isEmpty(user.getResetKey())) {
          batch.add(insertByResetKeyStmt.bind()
              .setString("reset_key", user.getResetKey())
              .setString("id", user.getId()));
        }
        batch.add(insertByLoginStmt.bind()
            .setString("login", user.getLogin())
            .setString("id", user.getId()));
        batch.add(insertByEmailStmt.bind()
            .setString("email", user.getEmail())
            .setString("id", user.getId()));
        session.execute(batch);
        return user;
    }

    public void delete(User user) {
        BatchStatement batch = new BatchStatement();
        batch.add(mapper.deleteQuery(user));
        if (!StringUtils.isEmpty(user.getActivationKey())) {
            batch.add(deleteByActivationKeyStmt.bind().setString("activation_key", user.getActivationKey()));
        }
        if (!StringUtils.isEmpty(user.getResetKey())) {
            batch.add(deleteByResetKeyStmt.bind().setString("reset_key", user.getResetKey()));
        }
        batch.add(deleteByLoginStmt.bind().setString("login", user.getLogin()));
        batch.add(deleteByEmailStmt.bind().setString("email", user.getEmail()));
        session.execute(batch);
    }

    private Optional<User> findOneFromIndex(BoundStatement stmt) {
        ResultSet rs = session.execute(stmt);
        if (rs.isExhausted()) {
            return Optional.empty();
        }
        return Optional.ofNullable(rs.one().getString("id"))
            .map(id -> Optional.ofNullable(mapper.get(id)))
            .get();
    }
}
