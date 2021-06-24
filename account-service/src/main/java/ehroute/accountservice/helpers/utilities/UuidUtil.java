package ehroute.accountservice.helpers.utilities;


import java.util.UUID;

public final class UuidUtil {

    public static String generateUUID()
    {
        return UUID.randomUUID().toString();
    }

    public static boolean isValidUUID(String uuid)
    {
        if (uuid.isEmpty()) return false;
        try { UUID uuidFromString = UUID.fromString(uuid); }
        catch (IllegalArgumentException Ex) { return false; }
        return true;
    }

}
