package goodthings.bean;

public class Book {
    private int id;
    private String book_name;
    private String out_link;
    private String pic_link;
    private String author;
    private String press;
    private String desc;
    private String caution;
    private String add_time;

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getOut_link() {
        return out_link;
    }

    public void setOut_link(String out_link) {
        this.out_link = out_link;
    }

    public String getPic_link() {
        return pic_link;
    }

    public void setPic_link(String pic_link) {
        this.pic_link = pic_link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }
}
