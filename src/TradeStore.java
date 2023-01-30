import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TradeStore {
    private List<Trade> trades;

    public TradeStore() {
        this.trades = new ArrayList<>();
    }


    public void addTrade(Trade trade) {
        if (trade.getMaturityDate().isBefore(LocalDate.now()))
            throw new RuntimeException("Maturity date " + trade.getMaturityDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " is less than today date");

        for (int i = 0; i < trades.size(); i++)
            if (Objects.equals(trades.get(i).getTradeId(), trade.getTradeId()))
                if (trade.getVersion() < trades.get(i).getVersion()) {
                    throw new RuntimeException("Lower version detected");
                } else {
                    trades.set(i, trade);
                    return;
                }

        trades.add(trade);
    }


    public List<Trade> getTrades() {
        return trades;
    }


    public void printTrades() {
        this.getTrades().forEach(t -> System.out.println("Trade Id: " + t.getTradeId() + ", Version: " + t.getVersion() + ", Counter-Party Id: " + t.getCounterPartyId() + ", Book-Id: " + t.getBookId() + ", Maturity Date: " + t.getMaturityDate() + ", Created Date: " + t.getCreatedDate() + ", Expired: " + t.isExpired()));
    }


    public void updateExpiredFlag() {
        System.out.println("\nExpired flag checker running...");

        trades.forEach(trade -> {
            if (trade.getMaturityDate().isBefore(LocalDate.now()) && trade.isExpired() != 'Y') {
                trade.setExpired('Y');
                System.out.println("The Expired flag for tradeId " + trade.getTradeId() + " is now set to 'Y'.\n");
                this.printTrades();
            }
        });
    }

}