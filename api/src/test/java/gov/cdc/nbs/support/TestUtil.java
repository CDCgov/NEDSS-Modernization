package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.Random;

import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.RecordStatus;

public class TestUtil {
    private static Random random = new Random();

    static {
        var seed = random.nextLong();
        random.setSeed(seed);
        System.out.println("Random data generated with seed: " + seed);
    }

    public static void setSeed(long seed) {
        random.setSeed(seed);
        System.out.println("Random seed updated to: " + seed);
    }

    public static int getRandomInt(int bound) {
        return random.nextInt(bound);
    }

    private static String[] states = new String[] {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida",
            "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine",
            "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
            "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"

    };

    public static String getRandomString() {
        return getRandomString(random.nextInt(5, 20));
    }

    public static String getRandomString(int length) {
        int leftLimit = 48; // 0
        int rightLimit = 122; // z
        return random.ints(leftLimit, rightLimit + 1).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    // format 123-456-789
    public static String getRandomSsn() {
        return getRandomNumericString(3) + "-" + getRandomNumericString(3) + "-" + getRandomNumericString(3);
    }

    public static String getRandomPhoneNumber() {
        // format 123-456-7890
        return getRandomNumericString(3) + "-" + getRandomNumericString(3) + "-" + getRandomNumericString(4);
    }

    public static String getRandomNumericString(int length) {
        int leftLimit = 48; // 0
        int rightLimit = 57; // 9
        return random.ints(leftLimit, rightLimit + 1).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public static Instant getRandomDateInPast() {
        var oneDayMillis = 86_400_000;
        var nowMillis = Instant.now().toEpochMilli() - oneDayMillis;
        return Instant.ofEpochMilli(random.nextLong(nowMillis));
    }

    public static String getRandomState() {
        var index = random.nextInt(states.length);
        return states[index];
    }

    public static Deceased getRandomDeceasedValue() {
        var value = random.nextInt(3);
        switch (value) {
            case 0:
                return Deceased.N;
            case 1:
                return Deceased.Y;
            case 2:
                return Deceased.UNK;
            default:
                throw new IllegalArgumentException("Invalid random value supplied to determine deceased value");
        }
    }

    public static Character getRandomSexValue() {
        var value = random.nextInt(3);
        switch (value) {
            case 0:
                return 'F';
            case 1:
                return 'M';
            case 2:
                return 'U';
            default:
                throw new IllegalArgumentException("Invalid random value supplied to determine random sex");
        }
    }

    public static RecordStatus getRandomRecordStatus() {
        var value = random.nextInt(2);
        switch (value) {
            case 0:
                return RecordStatus.ACTIVE;
            case 1:
                return RecordStatus.LOG_DEL;
            default:
                throw new IllegalArgumentException("Invalid random value supplied to determine record status");
        }
    }
}
