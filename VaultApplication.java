/**
 * VaultApplication: demonstrates a simple in-memory vault.
 */
public final class VaultApplication {
    private static final java.util.Map<String, String> VAULT = new java.util.concurrent.ConcurrentHashMap<>();

    private VaultApplication() {
        // utility class
    }

    /**
     * Entry point of the application.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("VaultApplication started.");
        storeSecret("admin", "s3cr3t");
        String secret = getSecret("admin");
        System.out.println("Secret for admin: " + secret);
    }

    /**
     * Stores a secret value for a given key.
     *
     * @param key   identifier for the secret
     * @param value the secret value
     */
    static void storeSecret(String key, String value) {
        VAULT.put(key, value);
    }

    /**
     * Retrieves the secret for the provided key.
     *
     * @param key identifier for the secret
     * @return the stored value or an empty string if not found
     */
    static String getSecret(String key) {
        return VAULT.getOrDefault(key, "");
    }
}
