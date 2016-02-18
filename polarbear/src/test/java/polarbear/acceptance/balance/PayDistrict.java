package polarbear.acceptance.balance;

public class PayDistrict {
    private int platformId;
    private String platformName;

    public PayDistrict() {
    }

    public PayDistrict(int platformId, String platformName) {
        super();
        this.platformId = platformId;
        this.platformName = platformName;
    }

    public int getPlatformId() {
        return platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

}