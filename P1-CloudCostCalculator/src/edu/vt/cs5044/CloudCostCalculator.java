package edu.vt.cs5044;

/**
 * A basic system to track costs incurred when purchasing on-demand services from a cloud provider.
 *
 * The costs are tracked for a current billing period as well as over the lifetime of the contract.
 *
 * ACADEMIC NOTE: Only primitive types int and double are to be used. All calculations should be
 * performed at double precision, with only the final result rounded or truncated if applicable.
 *
 * ACADEMIC NOTE: You may NOT use any methods of the Math class, except as explicitly specified in
 * the comments below.
 *
 * ACADEMIC NOTE: You may NOT use any of the following: branches; loops; conditionals; arrays; or
 * collections. We haven't yet covered these topics quite yet.
 *
 * @author moliva
 * @version ⓒ 2020 Prof. Oliva
 *
 */
public class CloudCostCalculator
{
    // TODO: Private instance variables are declared here
    private final int baseQuota;
    private final int baseRate;
    private final int overageRate;
    
    private int currentHoursUsage;
    private double currentCharges;
    private int oneTimeQuotaIncrease;
    private int oneTimeIncreaseCharges;
    private double lastCharges;
    private int totalHoursUsage;

    /**
     * Create a new CloudCalculator object with the specified parameters.
     *
     * Usage is charged in blocks of unit-hours, meaning some number of units used over a defined
     * number of hours. Each block may incur two distinct costs per unit-hour. The number of units
     * in a block up to (and including) the quota are billed at the base rate per unit per hour. The
     * number of units exceeding the quota are billed at the overage rate per unit per hour.
     *
     * @param baseQuota   maximum number of units that will be charged at the base rate.
     * @param baseRate    cost (in cents per unit per hour) charged for any usage up to the quota.
     * @param overageRate cost (in cents per unit per hour) charged for any usage over the quota.
     */

    public CloudCostCalculator(int baseQuota, int baseRate, int overageRate)

    {
        this.baseQuota = baseQuota;
        this.baseRate = baseRate;
        this.overageRate = overageRate;

    }

    /**
     * Record a block of usage of the cloud services.
     *
     * This method is called by an external system, each time a block of usage has been recorded.
     * All such usage must be tracked as part of the current billing period (and the total billing).
     * Partial hours and partial units are already rounded up, so the parameters are always positive
     * (meaning both non-zero and non-negative).
     *
     * ACADEMIC NOTE: You may safely assume that the parameters are always valid, according to the
     * expectations noted. Normally we would add validation code to ensure this is true, but we
     * haven't covered this quite yet.
     *
     * ACADEMIC NOTE: You may use Math.min() and/or Math.max() for your calculations.
     *
     * @param units number of units in this block; minimum value is 1.
     * @param hours number of hours in this block; minimum value is 1.
     */
    public void recordUsage(int units, int hours)
    {
        // split block of usage into quotaBlock, and overageBlock;
        int currentQuota = this.baseQuota + this.oneTimeQuotaIncrease;
        int quotaBlock = Math.min(currentQuota, units);
        int overageBlock = Math.max(units - quotaBlock, 0);
        
        // calculate cost for quota block usage and overage block usage
        int quotaUsageCost = quotaBlock * hours * (this.baseRate);
        int overageUsageCost = overageBlock * hours * (this.overageRate);
        
        this.currentHoursUsage += units * hours;
        this.totalHoursUsage += units * hours;
        this.currentCharges += quotaUsageCost + overageUsageCost;
    }

    /**
     * Return the amount of usage (in number of unit-hours) so far witin the current billing period.
     *
     * The returned value includes all usage, whether it was above or within the quota.
     *
     * @return usage (in number of unit-hours) so far in the current billing period.
     */
    public int getCurrentUsage()
    {

        return this.currentHoursUsage;
    }

    /**
     * Return the charges (in dollars and cents) incurred so far within the current billing period.
     *
     * The returned value includes all charges, meaning usage as well as all one-time quota increase
     * fees. The value should be expressed in dollars and decimal cents. For example, if a charge is
     * 12 dollars and 34 cents, the return value must be exactly 12.34 (to double precision).
     *
     * @return charges incurred within the current billing period, in dollars and cents.
     */
    public double getCurrentCharges()
    {
        int dollars = (int)this.currentCharges / 100 + this.oneTimeIncreaseCharges;
        double cents = (this.currentCharges % 100) / 100;
        return cents + dollars;
    }

    /**
     * Return the effective rate (in cents per unit-hour) charged within the current billing period.
     *
     * This provides an indication of how much is being charged, on average, per unit-hour of usage.
     * The charges must include usage (over and within quota) plus all one-time quota increase fees.
     *
     * The rate will be calculated at dobule precision, but the return value will be rounded DOWN
     * (truncated) to the nearest multiple of 0.001 cents. For example, if the rate is calculated to
     * be 5.6789... cents, the return value should be exactly 5.678 (to double precision).
     *
     * ACADEMIC NOTE: Use only arithmetic and casting for the truncation.
     *
     * @return truncated effective rate (in cents per unit-hour) within the current billing period.
     */
    public double getCurrentEffectiveRate()
    {
        double effectiveRate = this.getCurrentCharges() * 100 / this.getCurrentUsage();
        
        int effectiveRateWholeNumber = (int)effectiveRate;
        double effectiveRateDecimal = effectiveRate - effectiveRateWholeNumber;
        
        return effectiveRateWholeNumber + ((int)(effectiveRateDecimal * 1000)) / 1000.0; 
    }

    /**
     * Increase the quota for the remainder of the current billing period.
     *
     * This method is called by an external system to indicate that an increase in quota has been
     * purchased for the remainder of the billing period, for a one-time charge. The increased quota
     * only affects new usage for the current period, and does not affect any already-billed usage.
     * The quota increase is always positive (greater than zero), and the one-time charge is always
     * non-negative (zero or greater). At the end of the current period, the quota is reset to the
     * base quota.
     *
     * ACADEMIC NOTE: You may safely assume that the parameters are always valid, according to the
     * expectations noted. Normally we would add validation code to ensure this is true, but we
     * haven't covered this quite yet.
     *
     * @param dollars    number of dollars charged for the increased quota; minimum value is 0.
     * @param extraUnits number of units to add to the quota; minimum value is 1.
     */
    public void increaseQuota(int dollars, int extraUnits)
    {
        this.oneTimeQuotaIncrease += extraUnits;
        this.oneTimeIncreaseCharges += dollars;
    }

    /**
     * Reset the current billing period and begin a new period with no charges.
     *
     * Clears all current billing statistics, but doesn't alter the total billing statistics at all.
     *
     */
    public void resetBillingPeriod()
    {
        this.lastCharges = this.getCurrentCharges();
        this.currentCharges = 0;
        this.currentHoursUsage = 0;
        this.oneTimeIncreaseCharges = 0;
        this.oneTimeQuotaIncrease = 0;
    }

    /**
     * Return the charges (in dollars and cents) incurred within all billing periods.
     *
     * The returned value includes all charges, meaning usage as well as all one-time quota increase
     * fees. The value should be expressed in dollars and decimal cents. For example, if a charge is
     * 12 dollars and 34 cents, the return value must be exactly 12.34 (to double precision).
     *
     * @return charges incurred within the current and past billing periods, in dollars and cents.
     */
    public double getTotalCharges()
    {
        return this.getCurrentCharges() + this.lastCharges;
    }

    /**
     * Return the effective rate (in cents per unit-hour) charged within all billing periods.
     *
     * This provides an indication of how much is being charged, on average, per unit-hour of usage.
     * The charges must include usage (over and within quota) plus all one-time quota increase fees.
     *
     * The rate will be calculated at dobule precision, but the return value will be rounded DOWN
     * (truncated) to the nearest multiple of 0.001 cents. For example, if the rate is calculated to
     * be 5.6789... cents, the return value should be exactly 5.678 (to double precision).
     *
     * ACADEMIC NOTE: Use only arithmetic and casting for the truncation.
     *
     * @return truncated effective rate (in cents per unit-hour) within all billing periods.
     */
    public double getTotalEffectiveRate()
    {
        double effectiveRate = this.getTotalCharges() * 100 / this.totalHoursUsage;
        
        int effectiveRateWholeNumber = (int)effectiveRate;
        double effectiveRateDecimal = effectiveRate - effectiveRateWholeNumber;
        
        return effectiveRateWholeNumber + ((int)(effectiveRateDecimal * 1000)) / 1000.0;  
    }

}
