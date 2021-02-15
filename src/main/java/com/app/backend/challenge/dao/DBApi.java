package com.app.backend.challenge.dao;

import com.app.backend.challenge.resources.*;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Database Layer
 */
public class DBApi {

    /**
     *
     * @return a DB connection
     */
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DBConfig.DB_URL, DBConfig.config.toProperties());
    }


    /**
     * Insert a User into users table
     *
     * @param user to be inserted
     * @return user inserted
     * @throws SQLException when something was wrong
     */
    public UserResource addUser(final UserResource user) throws SQLException {

        final String sql = "INSERT INTO users(username,password) VALUES(?,?)";
        final String sqlRowId = "SELECT last_insert_rowid()";
        UserResource userResource = new UserResource();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmtRowId = conn.prepareStatement(sqlRowId)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmtRowId.executeQuery();
            userResource.setId(resultSet.getInt(1));
            userResource.setUsername(user.getUsername());
            userResource.setPassword(user.getPassword());
        } catch (SQLException e) {
            final String errMsg = "addUser error: " + e.getMessage();
            System.out.println(errMsg);
            throw e;
        }
        return userResource;
    }


    /**
     * Read a user by name from DB
     *
     * @param username to be read
     * @return user read
     * @throws Exception when something was wrong
     */
    public UserResource getUserByName(final String username) throws Exception {
        final String sql = "SELECT id, username, password FROM users WHERE username = ?" ;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            final ResultSet rs  = pstmt.executeQuery();
            final UserResource user = new UserResource();
            if(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
            throw new Exception("User does not exists");
        } catch (Exception e) {
            final String errMsg = "getUserByName error: " + e.getMessage();
            System.out.println(errMsg);
            throw e;
        }
    }


    /**
     * Insert a message into messages table
     *
     * @param message to be added
     * @return message id inserted
     * @throws SQLException when something was wrong
     */
    public MessageIdResource addMessage(MessageResource message) throws SQLException {
        final String sql = "INSERT INTO messages(" +
                "sender, recipient, type, text, timestamp) " +
                "VALUES(?,?,?,?,?)";

        final String sqlRowId = "SELECT last_insert_rowid()";

        MessageIdResource messageIdResource = new MessageIdResource();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmtRowId = conn.prepareStatement(sqlRowId)) {
            final long timestamp = Instant.now().toEpochMilli();
            pstmt.setInt(1, message.getSender());
            pstmt.setInt(2, message.getRecipient());
            pstmt.setString(3, message.getContent().getType());
            pstmt.setString(4, message.getContent().getText());
            pstmt.setLong(5, timestamp);
            pstmt.executeUpdate();

            // Read the inserted rowid
            ResultSet resultSet = pstmtRowId.executeQuery();
            messageIdResource.setId(resultSet.getInt(1));
            messageIdResource.setTimestamp(timestamp);

        } catch (SQLException e) {
            final String errMsg = "addMessage error: " + e.getMessage();
            System.out.println(errMsg);
            throw e;
        }

        return messageIdResource;

    }

    /**
     * Read messages from DB
     *
     * @param messageRequestResource Query criteria
     * @return A message list wrapped into MessageListResource instance
     * @throws SQLException when something was wrong
     */
    public MessageListResource readMessages(final MessagesRequestResource messageRequestResource) throws SQLException {

        final String sql = "SELECT id, timestamp, sender, recipient, type, text " +
                "FROM messages " +
                "WHERE recipient = ? " +
                "AND id >= ? " +
                "ORDER BY id " +
                "LIMIT  ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            final List<MessageResource> messages = new ArrayList<>();

            pstmt.setInt(1, messageRequestResource.getRecipient());
            pstmt.setInt(2, messageRequestResource.getStart());
            pstmt.setInt(3, messageRequestResource.getLimit());
            final ResultSet rs  = pstmt.executeQuery();
            while(rs.next()) {
                final MessageResource messageResource = new MessageResource();
                final ContentResource contentResource = new ContentResource();
                messageResource.setId(rs.getInt("id"));
                messageResource.setTimestamp(rs.getLong("timestamp"));
                messageResource.setSender(rs.getInt("sender"));
                messageResource.setRecipient(rs.getInt("recipient"));
                contentResource.setType(rs.getString("type"));
                contentResource.setText(rs.getString("text"));
                messageResource.setContent(contentResource);
                messages.add(messageResource);
            }
            MessageListResource messageListResource = new MessageListResource();
            messageListResource.setMessages(messages);
            return messageListResource;
        } catch (SQLException e) {
            final String errMsg = "addMessage error: " + e.getMessage();
            System.out.println(errMsg);
            throw e;
        }
    }
}
