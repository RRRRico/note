import java.util.*;

class Solution {
    class Transaction {
        Transaction(long timestamp, double amount, double balance) {
            this.timestamp = timestamp;
            this.amount = amount;
            this.balance = balance;
        }
        long timestamp;
        double amount;
        double balance;
    }

    Map<Long, List<Transaction>> history = new HashMap<>();

    public double deposit(long id, long timestamp, double amount) {
        if (amount < 0) throw new IllegalArgumentException("Deposit money cannot be negative.");

        List<Transaction> transactions = history.getOrDefault(id, new ArrayList<>());

        double balance = getBalance(transactions);
        balance += amount;
        transactions.add(new Transaction(timestamp, amount, balance));
        history.put(id, transactions);
        return balance;
    }

    private double getBalance(List<Transaction> transactions) {
        return transactions.isEmpty() ? 0 : transactions.get(transactions.size() - 1).balance;
    }

    public double withdraw(long id, long timestamp, double amount) {
        if (!history.containsKey(id)) throw new IllegalArgumentException("user doesn't exist");
        if (amount < 0) throw new IllegalArgumentException("Cannot withdraw negative amount");

        List<Transaction> transactions = history.get(id);
        double balance = getBalance(transactions);

        if (balance < amount) throw new IllegalArgumentException("Inssuficient balance");
        balance -= amount;
        transactions.add(new Transaction(timestamp, -amount, balance));
        return balance;
    }

    public double check(int id) {
        if (!history.containsKey(id)) throw new IllegalArgumentException("user doesn't exist");
        List<Transaction> transactions = history.get(id);
        return getBalance(transactions);
    }

    public double statement(long id, long start, long end) {
        if (!history.containsKey(id)) throw new IllegalArgumentException("user doesn't exist");
        List<Transaction> transactions = history.get(id);
        if (transactions.isEmpty()) return 0;

        return bs(transactions, end) - bs(transactions, start);
    }

    private double bs(List<Transaction> transactions, long time) {
        int lo = 0;
        int hi = transactions.size() - 1;
        if (transactions.get(lo).timestamp >= time) return 0;
        if (transactions.get(hi).timestamp <= time) return transactions.get(hi).balance;

        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            long key = transactions.get(mid).timestamp;

            if (key == time) return transactions.get(mid).balance;
            if (key < time) lo = mid;
            else hi = mid - 1;
        }
        System.out.println(hi);
        return transactions.get(hi).balance;
    }

    public static void main(String... args) {
        Solution sol = new Solution();
        sol.deposit(3, 5, 10);
        sol.withdraw(3, 12, 5);
        sol.withdraw(3, 15, 5);

        System.out.println(sol.statement(3, 0, 14));
        System.out.println(sol.statement(3, 5, 12));
        System.out.println(sol.statement(3, 6, 16));
    }
}
