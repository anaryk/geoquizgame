package app.stores;

public class Question {

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", category='" + category + '\'' +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                '}';
    }

    public Question(String text, String category, Double latitude, Double longtitude) {
        this.text = text;
        this.category = category;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    private String text;
    private String category;
    private Double latitude;
    private Double longtitude;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }
}
