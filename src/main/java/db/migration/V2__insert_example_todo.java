package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class V2__insert_example_todo extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws SQLException {
        new JdbcTemplate((Connection) new SingleConnectionDataSource(context.getConnection(),true))
                .execute("insert into tasks (description, done) VALUES ('Learn JAva migration', true)");
    }
}
