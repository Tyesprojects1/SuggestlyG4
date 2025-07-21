/**
 * VaultApplication: demonstrates a simple in-memory vault.
 */
public final class VaultApplication {
    private static final java.util.Map<String, String> VAULT = new java.util.HashMap<>();

    private VaultApplication() {
        // utility class
    }

    public static void main(String[] args) {
        System.out.println("VaultApplication started.");
        storeSecret("admin", "s3cr3t");
        String secret = getSecret("admin");
        System.out.println("Secret for admin: " + secret);
    }

    static void storeSecret(String key, String value) {
        VAULT.put(key, value);
    }

    static String getSecret(String key) {
        return VAULT.getOrDefault(key, "");
    }
}
