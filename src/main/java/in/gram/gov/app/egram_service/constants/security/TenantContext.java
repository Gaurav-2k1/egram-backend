package in.gram.gov.app.egram_service.constants.security;

public class TenantContext {
    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> TENANT_SLUG = new ThreadLocal<>();

    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    public static void setTenantSlug(String slug) {
        TENANT_SLUG.set(slug);
    }

    public static String getTenantSlug() {
        return TENANT_SLUG.get();
    }

    public static void clear() {
        TENANT_ID.remove();
        TENANT_SLUG.remove();
    }
}

