package balajianoopgupta.projects.com.sjsuinteractivemap;

/**
 * Created by balaji.byrandurga on 10/23/16.
 */

public class BuildingDetails {

    public String key;
    public String name;
    public String address;
    public String photo;
    public Double lat,lng;



    public BuildingDetails(String bkey, String bname,String baddr, String bphoto, Double blat, Double blng){
        this.key = bkey;
        this.address = baddr;
        this.name=bname;
        this.photo = bphoto;
        this.lat=blat;
        this.lng=blng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
