package edu.vt.cs5044;

//CHECKSTYLE:OFF
@SuppressWarnings("javadoc")
public class CloudCostCalculatorTester
{
    public static void main(String[] args)
    {
        CloudCostCalculator calc = new CloudCostCalculator(100, 1, 5);

        System.out.println("--- Initial state ---");
        System.out.println("current usage - expected: 0, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 0.0, actual: " + calc.getCurrentCharges());
        System.out.println("total charges - expected: 0.0, actual: " + calc.getTotalCharges());

        calc.recordUsage(50, 24);
// 50*24 = 1200 = current usage 
        // 1200*.01 = 12 
        System.out.println();
        System.out.println("--- After u(50, 24) ---");
        System.out.println("current usage - expected: 1200, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 12.0, actual: " + calc.getCurrentCharges());
        System.out.println("current effective rate - expected: 1.0, actual: " + calc.getCurrentEffectiveRate());
        System.out.println("total charges - expected: 12.0, actual: " + calc.getTotalCharges());

//50*96 = 4800 current usage 
        //4800 + 1200 = 6000 * .01 = 60 
        calc.recordUsage(50, 96);

        System.out.println();
        System.out.println("--- After u(50, 96) ---");
        System.out.println("current usage - expected: 6000, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 60.0, actual: " + calc.getCurrentCharges());
        System.out.println("current effective rate - expected: 1.0, actual: " + calc.getCurrentEffectiveRate());
        System.out.println("total charges - expected: 60.0, actual: " + calc.getTotalCharges());

//100 will be charged at baserate, 20 will be charged the overage rate 
        //100*24 = 2400 *.01 = 24 
        //60 + 24 (base charges) + (20 units * 24 hours) * .05 = 24 (overage) 
        //60 + 24 + 24 = 108  
        calc.recordUsage(120, 24);

        System.out.println();
        System.out.println("--- After u(120, 24) ---");
        System.out.println("current usage - expected: 8880, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 108.0, actual: " + calc.getCurrentCharges());
        System.out.println("current effective rate - expected: 1.216, actual: " + calc.getCurrentEffectiveRate());
        System.out.println("total charges - expected: 108.0, actual: " + calc.getTotalCharges());

        calc.recordUsage(20, 24);

        System.out.println();
        System.out.println("--- After u(20, 24) ---");
        System.out.println("current usage - expected: 9360, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 112.8, actual: " + calc.getCurrentCharges());
        System.out.println("current effective rate - expected: 1.205, actual: " + calc.getCurrentEffectiveRate());
        System.out.println("total charges - expected: 112.8, actual: " + calc.getTotalCharges());

//increase quota by 100 
        //200 * 72 = 14400 * .01 = 144
        // $112.8 + $20 + 144 = $276.8 
        calc.increaseQuota(20, 100);
        calc.recordUsage(200, 72);

        System.out.println();
        System.out.println("--- After q(20, 100) then u(200, 72) ---");
        System.out.println("current usage - expected: 23760, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 276.8, actual: " + calc.getCurrentCharges());
        System.out.println("current effective rate - expected: 1.164, actual: " + calc.getCurrentEffectiveRate());
        System.out.println("total charges - expected: 276.8, actual: " + calc.getTotalCharges());
        System.out.println("total effective rate - expected: 1.164, actual: " + calc.getTotalEffectiveRate());

        calc.resetBillingPeriod();

        System.out.println();
        System.out.println("--- After reset ---");
        System.out.println("current usage - expected: 0, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 0.0, actual: " + calc.getCurrentCharges());
        System.out.println("total charges - expected: 276.8, actual: " + calc.getTotalCharges());
        System.out.println("total effective rate - expected: 1.164, actual: " + calc.getTotalEffectiveRate());

        calc.recordUsage(150, 72);

        System.out.println();
        System.out.println("--- After u(150, 72) ---");
        System.out.println("current usage - expected: 10800, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 252.0, actual: " + calc.getCurrentCharges());
        System.out.println("current effective rate - expected: 2.333, actual: " + calc.getCurrentEffectiveRate());
        System.out.println("total charges - expected: 528.8, actual: " + calc.getTotalCharges());
        System.out.println("total effective rate - expected: 1.53, actual: " + calc.getTotalEffectiveRate());

        System.out.println();
        System.out.println("--- END OF PROVIDED TESTS ---");

        //Additional test cases below:
        
        calc = new CloudCostCalculator(200, 2, 1);

        System.out.println();
        System.out.println("--- Initial state ---");
        System.out.println("current usage - expected: 0, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 0.0, actual: " + calc.getCurrentCharges());
        System.out.println("total charges - expected: 0.0, actual: " + calc.getTotalCharges());

        calc.recordUsage(50, 24);
        
        System.out.println();
        System.out.println("--- After u(50, 24) ---");
        System.out.println("current usage - expected: 1200, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 24.0, actual: " + calc.getCurrentCharges());
        System.out.println("current effective rate - expected: 2.0, actual: " + calc.getCurrentEffectiveRate());
        System.out.println("total charges - expected: 24.0, actual: " + calc.getTotalCharges());

        calc.increaseQuota(80, 1000);
        calc.recordUsage(20, 9);

        System.out.println();
        System.out.println("--- After q(80, 1000) then u(20, 9) ---");
        System.out.println("current usage - expected: 1380, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 107.6, actual: " + calc.getCurrentCharges());
        System.out.println("current effective rate - expected: 7.797, actual: " + calc.getCurrentEffectiveRate());
        System.out.println("total charges - expected: 107.6, actual: " + calc.getTotalCharges());
        System.out.println("total effective rate - expected: 7.797, actual: " + calc.getTotalEffectiveRate());

        calc.resetBillingPeriod();

        System.out.println();
        System.out.println("--- After reset ---");
        System.out.println("current usage - expected: 0, actual: " + calc.getCurrentUsage());
        System.out.println("current charges - expected: 0.0, actual: " + calc.getCurrentCharges());
        System.out.println("total charges - expected: 107.6, actual: " + calc.getTotalCharges());
        System.out.println("total effective rate - expected: 7.797, actual: " + calc.getTotalEffectiveRate());
        
        System.out.println();
        System.out.println("--- END OF ADDITIONAL TESTS ---");
        
//        System.out.println("--- START OF ADDITIONAL TESTS ---");
//        CloudCostCalculator calc2 = new CloudCostCalculator (40, 1, 40);
//        
//        System.out.println("--- Initial state ---");
//        System.out.println("current usage - expected: 0, actual: " + calc2.getCurrentUsage());
//        System.out.println("current charges - expected: 0.0, actual: " + calc2.getCurrentCharges());
//        System.out.println("total charges - expected: 0.0, actual: " + calc2.getTotalCharges());
//        
//        calc2.recordUsage(20, 20);
//        // 20*20 = 400 
//        //400*.01 =4
//        
//        System.out.println();
//        System.out.println("--- After u(20, 20) ---");
//        System.out.println("current usage - expected: 400, actual: " + calc2.getCurrentUsage());
//        System.out.println("current charges - expected: 4, actual: " + calc2.getCurrentCharges());
//        System.out.println("current effective rate - expected: 1.0, actual: " + calc2.getCurrentEffectiveRate());
//        System.out.println("total charges - expected: 4.0, actual: " + calc2.getTotalCharges());
//        
//        calc2.recordUsage(40, 40);
//        //40*40 =1600
//        //1600*.01 = 16
//
//        System.out.println();
//        System.out.println("--- After u(30, 30) ---");
//        System.out.println("current usage - expected: 2000, actual: " + calc2.getCurrentUsage());
//        System.out.println("current charges - expected: 20, actual: " + calc2.getCurrentCharges());
//        System.out.println("current effective rate - expected: 1.216, actual: " + calc2.getCurrentEffectiveRate());
//        System.out.println("total charges - expected: 20.0, actual: " + calc2.getTotalCharges());
//        
//        calc2.resetBillingPeriod();
//
//        System.out.println();
//        System.out.println("--- After reset ---");
//        System.out.println("current usage - expected: 0, actual: " + calc2.getCurrentUsage());
//        System.out.println("current charges - expected: 0.0, actual: " + calc2.getCurrentCharges());
//        System.out.println("total charges - expected: 16, actual: " + calc2.getTotalCharges());
//        System.out.println("total effective rate - expected: 1.164, actual: " + calc2.getTotalEffectiveRate());

    }
}
