/**
 * VaultApplication provides a minimal CLI for storing and retrieving secrets.
 */
public final class VaultApplication {
    private VaultApplication() {
        // utility class
    }

    /**
     * Entry point of the application.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("VaultApplication started. Type 'help' for commands.");
        Vault vault = new Vault();
        try {
            new VaultServer(vault).start();
            System.out.println("HTTP server started on port 8080.");
        } catch (java.io.IOException e) {
            System.err.println("Failed to start HTTP server: " + e.getMessage());
        }
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String line = scanner.nextLine().trim();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                } else if (line.startsWith("store ")) {
                    String[] parts = line.split(" ", 3);
                    if (parts.length == 3) {
                        vault.storeSecret(parts[1], parts[2]);
                        System.out.println("Stored.");
                    } else {
                        System.out.println("Usage: store <key> <value>");
                    }
                } else if (line.startsWith("get ")) {
                    String[] parts = line.split(" ", 2);
                    if (parts.length == 2) {
                        String value = vault.getSecret(parts[1]);
                        if (value.isEmpty()) {
                            System.out.println("No secret found.");
                        } else {
                            System.out.println(value);
                        }
                    } else {
                        System.out.println("Usage: get <key>");
                    }
                } else if (line.equalsIgnoreCase("help")) {
                    System.out.println("Commands: store <key> <value>, get <key>, exit");
                } else {
                    System.out.println("Unknown command. Type 'help' for commands.");
                }
            }
        }
        System.out.println("VaultApplication stopped.");
    }
}
