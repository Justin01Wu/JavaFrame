// This code help you to understand multi-interface, override variable and methods
package scjp.testclass.inherit;

interface StockWatcher {
    
    final String sunTicker = "SUNW--1";
    final String ciscoTicker = "CSCO";
    
    void valueChanged(double newValue);
    void valueChanged_2(String tickerSymbol);
}

interface StockWatcher2 {
    String sunTicker = "SUNW--2";
    final String oracleTicker = "ORCL";
    
    
    void valueChanged(String tickerSymbol);
    //void valueChanged_2(String tickerSymbol) ;
    //void valueChanged_2(String tickerSymbol) throws Exception; // this is fine
    //int valueChanged_2(String tickerSymbol);  // this will cause a conflict 
}


public class stockShow implements StockWatcher , StockWatcher2 {
    
    public void valueChanged(double newValue){
        System.out.println("a--1");
    }
    
    public void valueChanged(String tickerSymbol){
        System.out.println("a--2");
    }
    
    public void valueChanged_2(String tickerSymbol) {
        System.out.println("a--3");
    }
    
    public static void main(String args[]){
        stockShow show = new stockShow();
        System.out.println(stockShow.ciscoTicker);
        //System.out.println(stockShow.sunTicker);  // this cause an ambiguous compile error
        show.valueChanged_2("aa");
        show.valueChanged("bb");
    }
    
}
