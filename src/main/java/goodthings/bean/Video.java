package goodthings.bean;

public class Video {
    private int id;
    private String video_name;
    private String out_link;
    private String pic_link;
    private String producer;
    private String description;
    private int isdel;
    private String add_time;
    private int owner_num;
    private int approval_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getOwner_num() {
        return owner_num;
    }

    public void setOwner_num(int owner_num) {
        this.owner_num = owner_num;
    }

    public int getApproval_num() {
        return approval_num;
    }

    public void setApproval_num(int approval_num) {
        this.approval_num = approval_num;
    }
}
