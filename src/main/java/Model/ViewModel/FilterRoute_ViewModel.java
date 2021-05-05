package Model.ViewModel;

public class FilterRoute_ViewModel {
    private Integer idFilter;
    private byte[] picture;
    private String typeName;
    private String startStation;

    public Integer getIdFilter() {
        return idFilter;
    }

    public void setIdFilter(Integer idFilter) {
        this.idFilter = idFilter;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getDestStation() {
        return destStation;
    }

    public void setDestStation(String destStation) {
        this.destStation = destStation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private String destStation;
    private int duration;

    public FilterRoute_ViewModel(Integer idFilter, byte[] picture, String typeName, String startStation, String destStation, int duration) {
        this.idFilter = idFilter;
        this.picture = picture;
        this.typeName = typeName;
        this.startStation = startStation;
        this.destStation = destStation;
        this.duration = duration;
    }
}
