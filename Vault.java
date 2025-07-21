public final class Vault {
    private final java.util.Map<String, String> secrets = new java.util.concurrent.ConcurrentHashMap<>();

    /**
     * Stores a secret value for a given key.
     *
     * @param key   identifier for the secret
     * @param value the secret value
     */
    public void storeSecret(String key, String value) {
        secrets.put(key, value);
    }

    /**
     * Retrieves the secret for the provided key.
     *
     * @param key identifier for the secret
     * @return the stored value or an empty string if not found
     */
    public String getSecret(String key) {
        return secrets.getOrDefault(key, "");
    }
}
