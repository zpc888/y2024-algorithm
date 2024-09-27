package zpc.solution;

import java.time.LocalDate;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

/********************

 Before customers place an order online, they enter an available coupon or voucher code to get a discount. We need to determine if the voucher code entered exists and is not expired yet. Inputs are:
 (1) a voucher code
 (2) the date when the customer applied the voucher,
 (3) a list of all voucher codes and their expiry dates (past, current and future vouchers). Write a function that returns true if the entered voucher code exists and is not yet expired; false otherwise.

 Input:
 "fall3",
 "2021-10-20"
 [
 ["2021-10-30", ["fall2", "fall1", "fall3"]],
 ["2020-07-01", ["summer30", "summer10", "summer5"]],
 ["2022-02-14", ["valentine30", "valentine10", "valentine20"]],
 ],

 Output: true


 Input:
 "fall100",
 "2021-10-20"
 [
 ["2021-10-30", ["fall2", "fall1", "fall3"]],
 ["2020-07-01", ["summer30", "summer10", "summer5"]],
 ["2022-02-14", ["valentine30", "valentine10", "valentine20"]],
 ],

 Output: false

 Max number of vouchers is 1,000,000.
 Max number of expiry dates is 10,000.

 */

class VoucherChecker {
    public static class Voucher {
        public LocalDate date;
        public Set<String> codes;

        public Voucher() {
        }
        public Voucher(String date, List<String> codes) {
            this.date = LocalDate.parse(date);
            this.codes = new HashSet<>(codes);
        }

        public boolean isValid(String code, LocalDate date) {
            //System.out.println("date = " + this.date + " vs " + date);
            //System.out.println("codes == " + codes);
            //System.out.println("code == " + code);
            boolean result = codes.contains(code) && this.date.compareTo(date) >= 0;
            //System.out.println(result);
            return result;
        }
    }

    public static class VoucherCache {
        private final Map<String, String> cache = new HashMap<>(1024);

        public VoucherCache addVoucher(String date, List<String> codes) {
            for (String code : codes) {
                String oldDate = cache.get(code);
                if (oldDate == null || oldDate.compareTo(date) < 0) {
                    cache.put(code, date);
                }
            }
            return this;
        }

        public boolean isValid(String code, String date) {
            return cache.containsKey(code) && cache.get(code).compareTo(date) >= 0;
        }
    }

    public static void main(String[] args) {
        // happy coding :)
        String voucher = "fall3";
        String date = "2021-11-01";
        List<Voucher> vouchers = new ArrayList<>(10);
        vouchers.add(new Voucher("2021-10-30", Arrays.asList("fall2", "fall1", "fall3")));
        vouchers.add(new Voucher("2020-07-01", Arrays.asList("summer30", "summer10", "summer5")));
        vouchers.add(new Voucher("2022-02-14", Arrays.asList("valentine30", "valentine10", "valentine20")));
//        vouchers.sort((v1, v2) -> v1.date.compareTo(v2.date));
        vouchers.sort((v1, v2) -> v2.date.compareTo(v1.date));

        VoucherCache cache = new VoucherCache();
        cache.addVoucher("2021-10-30", Arrays.asList("fall2", "fall1", "fall3"))
             .addVoucher("2020-07-01", Arrays.asList("summer30", "summer10", "summer5"))
             .addVoucher("2022-02-14", Arrays.asList("valentine30", "valentine10", "valentine20"));

        boolean result = isValid(voucher, date, vouchers);
        boolean result2 = cache.isValid(voucher, date);
        System.out.println("Output: " + result + " vs " + result2);
        if (result != result2) {
            System.out.println("Mismatched results!");
        }
    }

    private static boolean isValid(String voucherCode, String date, List<Voucher> vouchers) {
        LocalDate txDate = LocalDate.parse(date);
//        return vouchers.stream().anyMatch(v -> v.isValid(voucherCode, LocalDate.parse(date)));
        for (Voucher v : vouchers) {
            if (v.date.compareTo(txDate) < 0) {
                return false;
            } else if (v.codes.contains(voucherCode)) {
                return true;
            }
        }
        return false;
    }
}
