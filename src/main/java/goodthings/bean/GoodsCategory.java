package goodthings.bean;

public enum GoodsCategory {
    book(1), audio(2),vedio(3),stusupply(4), toy(5);
    private int value = 0;
    private GoodsCategory(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }
}
