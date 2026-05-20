package dbkeygen;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.NamingHelper;

import java.util.Arrays;
import java.util.List;

public final class DBKeyGenHasher {

    private DBKeyGenHasher() {}

    public static String createIndexHash(String tableName, String[] columnNames) {
        var tableIdentifier = Identifier.toIdentifier(tableName);
        var columnIdentifiers = toIdentifiers(columnNames);
        return NamingHelper.INSTANCE.generateHashedConstraintName("IDX", tableIdentifier, columnIdentifiers);
    }

    public static String createUniqueHash(String tableName, String[] columnNames) {
        var tableIdentifier = Identifier.toIdentifier(tableName);
        var columnIdentifiers = toIdentifiers(columnNames);
        return NamingHelper.INSTANCE.generateHashedConstraintName("UK", tableIdentifier, columnIdentifiers);
    }

    public static String createForeignKeyHash(String tableName, String foreignTableName, String[] columnNames) {
        var tableIdentifier = Identifier.toIdentifier(tableName);
        var foreignTableIdentifier = Identifier.toIdentifier(foreignTableName);
        var columnIdentifiers = toIdentifiers(columnNames);
        return NamingHelper.INSTANCE.generateHashedFkName("FK", tableIdentifier, foreignTableIdentifier, columnIdentifiers);
    }

    private static List<Identifier> toIdentifiers(String[] columns) {
        return Arrays.stream(columns).map(Identifier::toIdentifier).toList();
    }

}
