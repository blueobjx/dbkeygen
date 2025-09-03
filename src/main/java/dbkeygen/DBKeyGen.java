package dbkeygen;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.NamingHelper;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.List;

@CommandLine.Command(name = "dbkeygen", mixinStandardHelpOptions = true, version = "dbkeygen 1.0", description = "A simple tool to generate database constraint keys.")
public class DBKeyGen {

    @CommandLine.Command(mixinStandardHelpOptions = true, description = "Create index key from table, and one or more columns")
    void index(@CommandLine.Parameters(index = "0", paramLabel = "table", description = "Table name for index") String table,
                @CommandLine.Parameters(index = "1..*", arity = "1..*", paramLabel = "column", description = "Column name[s]") String[] columns) {
        var tableIdentifier = Identifier.toIdentifier(table);
        var columnIdentifiers = toIdentifiers(columns);
        System.out.println(NamingHelper.INSTANCE.generateHashedConstraintName("IDX", tableIdentifier, columnIdentifiers));
    }

    @CommandLine.Command(mixinStandardHelpOptions = true, description = "Create unique key from table and one or more columns")
    void unique(@CommandLine.Parameters(index = "0", paramLabel = "table", description = "Table name for index") String table,
                @CommandLine.Parameters(index = "1..*", arity = "1..*", paramLabel = "column", description = "Column name[s]") String[] columns) {
        var tableIdentifier = Identifier.toIdentifier(table);
        var columnIdentifiers = toIdentifiers(columns);
        System.out.println(NamingHelper.INSTANCE.generateHashedConstraintName("UK", tableIdentifier, columnIdentifiers));
    }

    @CommandLine.Command(mixinStandardHelpOptions = true, description = "Create foreign key from table, foreign table, and one or more columns")
    void foreign(@CommandLine.Parameters(index = "0", paramLabel = "table", description = "Table name for key") String table,
                @CommandLine.Parameters(index = "1", paramLabel = "foreigntable", description = "Foreign table name") String refTable,
                @CommandLine.Parameters(index = "2..*", arity = "1..*", paramLabel = "column", description = "Column name[s]") String[] columns) {
        var tableIdentifier = Identifier.toIdentifier(table);
        var refTableIdentifier = Identifier.toIdentifier(refTable);
        var columnIdentifiers = toIdentifiers(columns);
        System.out.println(NamingHelper.INSTANCE.generateHashedFkName("FK", tableIdentifier, refTableIdentifier, columnIdentifiers));
    }

    private List<Identifier> toIdentifiers(String[] columns) {
        return Arrays.stream(columns).map(Identifier::toIdentifier).toList();
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new DBKeyGen()).execute(args));
    }

}