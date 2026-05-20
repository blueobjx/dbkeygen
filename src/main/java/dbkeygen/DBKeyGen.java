package dbkeygen;

import picocli.CommandLine;

@CommandLine.Command(name = "dbkeygen", mixinStandardHelpOptions = true, version = "dbkeygen 1.1", description = "A simple tool to generate database constraint keys.")
public class DBKeyGen {

    @CommandLine.Command(mixinStandardHelpOptions = true, description = "Create index key from tablename, and one or more columns")
    public void index(@CommandLine.Parameters(index = "0", paramLabel = "tablename", description = "Table name for index") String tableName,
               @CommandLine.Parameters(index = "1..*", arity = "1..*", paramLabel = "columnname", description = "Column name[s]") String[] columnNames) {
        System.out.println(DBKeyGenHasher.createIndexHash(tableName, columnNames));
    }

    @CommandLine.Command(mixinStandardHelpOptions = true, description = "Create unique key from table and one or more columns")
    public void unique(@CommandLine.Parameters(index = "0", paramLabel = "tablename", description = "Table name for index") String tableName,
                @CommandLine.Parameters(index = "1..*", arity = "1..*", paramLabel = "columnname", description = "Column name[s]") String[] columnNames) {
        System.out.println(DBKeyGenHasher.createUniqueHash(tableName, columnNames));
    }

    @CommandLine.Command(mixinStandardHelpOptions = true, description = "Create foreign key from table, foreign table, and one or more columns")
    public void foreign(@CommandLine.Parameters(index = "0", paramLabel = "tablename", description = "Table name for key") String tableName,
                 @CommandLine.Parameters(index = "1", paramLabel = "foreigntablename", description = "Foreign table name") String foreignTableName,
                 @CommandLine.Parameters(index = "2..*", arity = "1..*", paramLabel = "columnname", description = "Column name[s]") String[] columnNames) {
        System.out.println(DBKeyGenHasher.createForeignKeyHash(tableName, foreignTableName, columnNames));
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new DBKeyGen()).execute(args));
    }

}