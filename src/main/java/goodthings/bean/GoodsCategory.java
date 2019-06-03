package goodthings.bean;

public enum GoodsCategory {
    book(1), audio(2),video(3),stusupply(4), toy(5);
    private int value = 0;
    private GoodsCategory(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }

    public static String getName(int value) {
        for (GoodsCategory g : GoodsCategory.values()) {
            if (g.value == value) {
                return g.name();
            }
        }
        return null;
    }
}
