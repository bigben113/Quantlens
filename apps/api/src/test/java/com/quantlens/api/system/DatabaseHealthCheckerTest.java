package com.quantlens.api.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

class DatabaseHealthCheckerTest {

    @Test
    void returnsTrueWhenConnectionIsValid() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(2)).thenReturn(true);

        assertThat(new DatabaseHealthChecker(dataSource).isUp()).isTrue();
    }

    @Test
    void returnsFalseWhenConnectionIsInvalid() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(2)).thenReturn(false);

        assertThat(new DatabaseHealthChecker(dataSource).isUp()).isFalse();
    }

    @Test
    void returnsFalseWhenConnectionCannotBeObtained() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException("connection refused"));

        assertThat(new DatabaseHealthChecker(dataSource).isUp()).isFalse();
    }
}
