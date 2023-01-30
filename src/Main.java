import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        List<Trade> newTrades = List.of(
                new Trade("T1", 1, "CP-1", "B1", LocalDate.of(2023, 05, 20), LocalDate.now(), 'N'),
                new Trade("T2", 2, "CP-2", "B1", LocalDate.of(2023, 05, 20), LocalDate.now(), 'N'),
                new Trade("T3", 3, "CP-3", "B2", LocalDate.of(2024, 05, 20), LocalDate.now(), 'N'),
                new Trade("T3", 4, "CP-4", "B2", LocalDate.of(2024, 01, 29), LocalDate.now(), 'N')
        );

        TradeStore tradeStore = new TradeStore();
        newTrades.forEach(tradeStore::addTrade);

        tradeStore.printTrades();

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        System.out.println("\nExpired flag checker started...");
        scheduledThreadPool.scheduleAtFixedRate(tradeStore::updateExpiredFlag, 0, 1, TimeUnit.SECONDS);

    }
}
